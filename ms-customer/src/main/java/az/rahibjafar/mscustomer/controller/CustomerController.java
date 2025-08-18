package az.rahibjafar.mscustomer.controller;

import az.rahibjafar.mscustomer.dto.CreateCustomerRequest;
import az.rahibjafar.mscustomer.dto.CustomerDto;
import az.rahibjafar.mscustomer.service.CustomerServis;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/v1/customer")
public class CustomerController {
    private final CustomerServis customerServis;
    public CustomerController(CustomerServis customerServis) {
        this.customerServis = customerServis;
    }
    @GetMapping("/getAll")
    public List<CustomerDto> getAll() {
        return customerServis.findAll();
    }

    @GetMapping("get/{id}")
    public CustomerDto get(@PathVariable UUID id) {
        return customerServis.getById(id);
    }

    @PostMapping("/create")
    public CustomerDto create(@RequestBody CreateCustomerRequest createCustomerRequest) {
        return customerServis.create(createCustomerRequest);
    }
}
