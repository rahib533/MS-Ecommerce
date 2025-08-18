package az.rahibjafar.msproduct.dto.converter;

import az.rahibjafar.msproduct.dto.CategoryDto;
import az.rahibjafar.msproduct.model.Category;
import org.springframework.stereotype.Component;

@Component
public class CategoryDtoConverter {
    public CategoryDto convertToCategoryDto(Category from) {
        return new CategoryDto(from.getId(), from.getName(), from.getDescription());
    }
}
