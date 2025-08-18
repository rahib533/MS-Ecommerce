package az.rahibjafar.mscustomer.service;

import az.rahibjafar.mscustomer.dto.CreateCustomerRequest;
import az.rahibjafar.mscustomer.dto.CustomerDto;
import az.rahibjafar.mscustomer.dto.converter.CustomerDtoConverter;
import az.rahibjafar.mscustomer.exception.CustomerNotFoundException;
import az.rahibjafar.mscustomer.model.Customer;
import az.rahibjafar.mscustomer.repository.CustomerRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class CustomerServis {
    private final CustomerRepository customerRepository;
    private final CustomerDtoConverter customerDtoConverter;

    public CustomerServis(CustomerRepository customerRepository, CustomerDtoConverter customerDtoConverter) {
        this.customerRepository = customerRepository;
        this.customerDtoConverter = customerDtoConverter;
    }

    public CustomerDto create(CreateCustomerRequest createCustomerRequest) {
        Customer customer = new Customer(createCustomerRequest.getFirstName(),
                createCustomerRequest.getLastName(),
                createCustomerRequest.getCif()
                );
        return customerDtoConverter.convertToCustomerDto(customerRepository.save(customer));
    }

    public CustomerDto getById(UUID id) {
        return customerDtoConverter.convertToCustomerDto(customerRepository.findById(id).orElseThrow(
                () -> new CustomerNotFoundException("Customer with id " + id + " not found")
        ));
    }

    protected Customer findById(UUID id) {
        return customerRepository.findById(id).orElseThrow(
                () -> new CustomerNotFoundException("Customer with id " + id + " not found")
        );
    }

    public List<CustomerDto> findAll() {
        return customerRepository.findAll()
                .stream()
                .map(customerDtoConverter::convertToCustomerDto)
                .collect(Collectors.toList());
    }
}
