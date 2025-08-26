package az.rahibjafar.msproduct.service;

import az.rahibjafar.msproduct.dto.CreateProductRequest;
import az.rahibjafar.msproduct.dto.ProductDto;
import az.rahibjafar.msproduct.dto.converter.ProductDtoConverter;
import az.rahibjafar.msproduct.exception.ProductNotFoundException;
import az.rahibjafar.msproduct.model.Category;
import az.rahibjafar.msproduct.model.Product;
import az.rahibjafar.msproduct.repository.CategoryRepository;
import az.rahibjafar.msproduct.repository.ProductRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ProductService {
    private final ProductRepository productRepository;
    private final ProductDtoConverter productDtoConverter;
    private final CategoryService categoryService;

    public ProductService(ProductRepository productRepository, ProductDtoConverter productDtoConverter, CategoryService categoryService, CategoryRepository categoryRepository) {
        this.productRepository = productRepository;
        this.productDtoConverter = productDtoConverter;
        this.categoryService = categoryService;
    }

    public List<ProductDto> findAll() {
        return productRepository.findAll()
                .stream()
                .map(productDtoConverter::convertToProductDto)
                .collect(Collectors.toList());
    }

    public ProductDto getById(UUID id) {
        return productDtoConverter.convertToProductDto(productRepository.findById(id).orElseThrow(
                () -> new ProductNotFoundException("Product not found with id: " + id))
        );
    }

    protected Product findById(UUID id) {
        return productRepository.findById(id).orElseThrow(
                () -> new ProductNotFoundException("Product not found with id: " + id));
    }

    public ProductDto create(CreateProductRequest createProductRequest) {
        Category category = categoryService.findById(createProductRequest.getCategoryId());

        Product product = new Product(createProductRequest.getName(),
                createProductRequest.getPrice(),
                createProductRequest.getStockCount(),
                category,
                createProductRequest.getDescription(),
                createProductRequest.getInStock()
        );

        return productDtoConverter.convertToProductDto(productRepository.save(product));
    }

    public ProductDto updateStock(UUID id, Integer stock) {
        Product product = findById(id);
        product.setStockCount(stock);
        return productDtoConverter.convertToProductDto(productRepository.save(product));
    }

    public void stockRollback(UUID id, Integer count) {
        Product product = findById(id);
        product.setStockCount(product.getStockCount() + count);
        productRepository.save(product);
    }
}
