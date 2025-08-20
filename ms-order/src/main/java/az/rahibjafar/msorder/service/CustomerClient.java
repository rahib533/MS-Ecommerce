package az.rahibjafar.msorder.service;

import az.rahibjafar.msorder.dto.CustomerDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import java.util.List;
import java.util.UUID;

@FeignClient(name = "ms-customer", path = "/v1/customer")
public interface CustomerClient {
    @GetMapping("/getAll")
    List<CustomerDto> getAll();

    @GetMapping("/get/{id}")
    CustomerDto get(@PathVariable("id") UUID id);
}
