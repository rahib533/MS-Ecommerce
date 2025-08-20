package az.rahibjafar.msorder.controller;

import az.rahibjafar.msorder.dto.CreateOrderRequest;
import az.rahibjafar.msorder.dto.OrderDto;
import az.rahibjafar.msorder.service.OrderService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/order")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping("/getAll")
    public List<OrderDto> getAll() {
        return orderService.findAll();
    }

    @PostMapping("/create")
    public OrderDto create(@RequestBody CreateOrderRequest createOrderRequest) {
        return orderService.create(createOrderRequest);
    }
}
