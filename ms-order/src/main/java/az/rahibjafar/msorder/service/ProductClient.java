package az.rahibjafar.msorder.service;

import az.rahibjafar.msorder.dto.ProductDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import java.util.List;
import java.util.UUID;

@FeignClient(name = "ms-product", path = "/v1/product")
public interface ProductClient {
    @GetMapping("/getAll")
    List<ProductDto> getAll();

    @GetMapping("/get/{id}")
    ProductDto get(@PathVariable("id") UUID id);
}
