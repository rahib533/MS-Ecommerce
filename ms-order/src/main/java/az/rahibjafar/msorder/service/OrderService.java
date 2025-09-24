package az.rahibjafar.msorder.service;

import az.rahibjafar.msorder.dto.*;
import az.rahibjafar.msorder.dto.converter.OrderDtoConverter;
import az.rahibjafar.msorder.event.model.OrderCreatedEvent;
import az.rahibjafar.msorder.event.model.StockRollbackEvent;
import az.rahibjafar.msorder.event.producer.OrderEventProducer;
import az.rahibjafar.msorder.exception.*;
import az.rahibjafar.msorder.model.Order;
import az.rahibjafar.msorder.model.OrderStatus;
import az.rahibjafar.msorder.repository.OrderRepository;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class OrderService {
    private final OrderRepository orderRepository;
    private final OrderDtoConverter orderDtoConverter;
    private final ProductClient productClient;
    private final CustomerClient customerClient;
    private final OrderEventProducer orderEventProducer;

    public OrderService(
            OrderRepository orderRepository,
            OrderDtoConverter orderDtoConverter,
            ProductClient productClient,
            CustomerClient customerClient,
            OrderEventProducer orderEventProducer
    )
    {
        this.orderRepository = orderRepository;
        this.orderDtoConverter = orderDtoConverter;
        this.productClient = productClient;
        this.customerClient = customerClient;
        this.orderEventProducer = orderEventProducer;
    }

    public OrderDto create(final CreateOrderRequest createOrderRequest) {
        ProductDto product = productClient.get(createOrderRequest.getProductId());
        if (product == null) {
            throw new ProductNotFoundException("Product not found with id: " + createOrderRequest.getProductId());
        }
        CustomerDto customer = customerClient.getCustomerById(createOrderRequest.getCustomerID());
        if (customer == null) {
            throw new CustomerNotFoundException("Customer not found with id: " + createOrderRequest.getCustomerID());
        }
        AccountDto account = customerClient.getAccountById(createOrderRequest.getAccountId());
        if (account == null) {
            throw new AccountNotFoundException("Account not found with id: " + createOrderRequest.getAccountId());
        }

        Order order = new Order(
                createOrderRequest.getProductId(),
                createOrderRequest.getCustomerID(),
                account.getAccountNumber(),
                createOrderRequest.getCount()
        );

        Order createdOrder = orderRepository.save(order);

        OrderCreatedEvent orderCreatedEvent = new OrderCreatedEvent(createdOrder.getId(), createOrderRequest.getProductId(),
                createOrderRequest.getCustomerID(), account.getAccountNumber(), createOrderRequest.getCount());
        orderEventProducer.publishOrderCreated(orderCreatedEvent);
        return orderDtoConverter.convertToOrderDto(createdOrder);
    }

    public List<OrderDto> findAll() {
        return orderRepository.findAll()
                .stream()
                .map(orderDtoConverter::convertToOrderDto)
                .collect(Collectors.toList());
    }

    public OrderDto getById(UUID id) {
        return orderDtoConverter.convertToOrderDto(orderRepository.findById(id).orElseThrow(
                () -> new OrderNotFoundException("Order not found with id: " + id)
        ));
    }

    public Order findById(UUID id) {
        return orderRepository.findById(id).orElseThrow(
                () -> new OrderNotFoundException("Order not found with id: " + id)
        );
    }

    public OrderDto updateStatus(OrderStatus orderStatus, UUID id) {
        Order order = findById(id);
        switch (orderStatus) {
            case CONFIRMED ->{
                order.setStatus(OrderStatus.CONFIRMED);
                order.setConfirmedDate(LocalDateTime.now());
            }
            case RESERVED -> {
                order.setStatus(OrderStatus.RESERVED);
                order.setReservedDate(LocalDateTime.now());
            }
            case CANCELLED ->{
                order.setStatus(OrderStatus.CANCELLED);
                order.setCancelledDate(LocalDateTime.now());
            }
            default -> throw new InvalidOrderStatusType("Invalid order status");
        }
        return orderDtoConverter.convertToOrderDto(orderRepository.save(order));
    }

    public OrderDto cancelOrderWithStockRollback(UUID id, String message) {
        Order order = findById(id);

        order.setStatus(OrderStatus.CANCELLED);
        order.setCancelledDate(LocalDateTime.now());

        orderEventProducer.publishStocksRollback(
                new StockRollbackEvent(
                        order.getId(),
                        order.getProductId(),
                        order.getCount(),
                        message
                )
        );

        return orderDtoConverter.convertToOrderDto(orderRepository.save(order));
    }

    public OrderDto updateTotalAmount(BigDecimal totalAmount, UUID id) {
        Order order = findById(id);
        order.setTotalAmount(totalAmount);
        return orderDtoConverter.convertToOrderDto(orderRepository.save(order));
    }
}
