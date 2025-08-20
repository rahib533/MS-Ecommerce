package az.rahibjafar.msorder.service;

import az.rahibjafar.msorder.dto.CreateOrderRequest;
import az.rahibjafar.msorder.dto.CustomerDto;
import az.rahibjafar.msorder.dto.OrderDto;
import az.rahibjafar.msorder.dto.ProductDto;
import az.rahibjafar.msorder.dto.converter.OrderDtoConverter;
import az.rahibjafar.msorder.exception.CustomerNotFoundException;
import az.rahibjafar.msorder.exception.InvalidOrderStatusType;
import az.rahibjafar.msorder.exception.OrderNotFoundException;
import az.rahibjafar.msorder.exception.ProductNotFoundException;
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

    public OrderService(
            OrderRepository orderRepository,
            OrderDtoConverter orderDtoConverter,
            ProductClient productClient,
            CustomerClient customerClient
    )
    {
        this.orderRepository = orderRepository;
        this.orderDtoConverter = orderDtoConverter;
        this.productClient = productClient;
        this.customerClient = customerClient;
    }

    public OrderDto create(final CreateOrderRequest createOrderRequest) {
        ProductDto product = productClient.get(createOrderRequest.getProductId());
        if (product == null) {
            throw new ProductNotFoundException("Product not found with id: " + createOrderRequest.getProductId());
        }
        CustomerDto customer = customerClient.get(createOrderRequest.getCustomerID());
        if (customer == null) {
            throw new CustomerNotFoundException("Customer not found with id: " + createOrderRequest.getCustomerID());
        }

        Order order = new Order(createOrderRequest.getProductId(), createOrderRequest.getCustomerID(), createOrderRequest.getCount());

        return orderDtoConverter.convertToOrderDto(orderRepository.save(order));
    }

    public List<OrderDto> findAll() {
        System.out.println("Product ->");
        productClient.getAll().forEach(productDto -> {
            System.out.println(productDto.getId() + " - " + productDto.getName());
        });
        System.out.println("Customer -> ");
        customerClient.getAll().forEach(customerDto -> {
            System.out.println(customerDto.getId() + " - " + customerDto.getFirstName());
        });
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
            case CANCELLED ->{
                order.setStatus(OrderStatus.CANCELLED);
                order.setCancelledDate(LocalDateTime.now());
            }
            default -> throw new InvalidOrderStatusType("Invalid order status");
        }
        return orderDtoConverter.convertToOrderDto(orderRepository.save(order));
    }

    public OrderDto updateTotalAmount(BigDecimal totalAmount, UUID id) {
        Order order = findById(id);
        order.setTotalAmount(totalAmount);
        return orderDtoConverter.convertToOrderDto(orderRepository.save(order));
    }
}
