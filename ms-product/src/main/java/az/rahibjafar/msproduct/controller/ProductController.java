package az.rahibjafar.msproduct.controller;

import az.rahibjafar.msproduct.dto.CreateProductRequest;
import az.rahibjafar.msproduct.dto.ProductDto;
import az.rahibjafar.msproduct.service.ProductService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/v1/product")
public class ProductController {
    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/getAll")
    public List<ProductDto> getAll() {
        return productService.findAll();
    }

    @GetMapping("get/{id}")
    public ProductDto get(@PathVariable UUID id) {
        return productService.findById(id);
    }

    @PostMapping("/create")
    public ProductDto create(@RequestBody CreateProductRequest createProductRequest) {
        return productService.create(createProductRequest);
    }
}
