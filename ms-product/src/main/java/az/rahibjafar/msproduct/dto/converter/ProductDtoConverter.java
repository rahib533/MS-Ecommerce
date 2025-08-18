package az.rahibjafar.msproduct.dto.converter;

import az.rahibjafar.msproduct.dto.ProductDto;
import az.rahibjafar.msproduct.model.Product;
import org.springframework.stereotype.Component;

@Component
public class ProductDtoConverter {
    public ProductDto convertToProductDto(Product from) {
        return new ProductDto(from.getId(),
                from.getName(),
                from.getPrice(),
                from.getStockCount(),
                from.getCategory(),
                from.getDescription(),
                from.getInStock()
        );
    }
}
