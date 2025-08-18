package az.rahibjafar.msproduct.service;

import az.rahibjafar.msproduct.dto.CategoryDto;
import az.rahibjafar.msproduct.dto.CreateCategoryRequest;
import az.rahibjafar.msproduct.dto.converter.CategoryDtoConverter;
import az.rahibjafar.msproduct.exception.CategoryNotFoundException;
import az.rahibjafar.msproduct.model.Category;
import az.rahibjafar.msproduct.repository.CategoryRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class CategoryService {
    private final CategoryRepository categoryRepository;
    private final CategoryDtoConverter categoryDtoConverter;

    public CategoryService(CategoryRepository categoryRepository, CategoryDtoConverter categoryDtoConverter) {
        this.categoryRepository = categoryRepository;
        this.categoryDtoConverter = categoryDtoConverter;
    }

    public CategoryDto create(CreateCategoryRequest createCategoryRequest) {
        Category category = new Category(createCategoryRequest.getName(),
                createCategoryRequest.getDescription()
        );
        return categoryDtoConverter.convertToCategoryDto(categoryRepository.save(category));
    }

    public List<CategoryDto> findAll() {
        return categoryRepository.findAll()
                .stream()
                .map(categoryDtoConverter::convertToCategoryDto)
                .collect(Collectors.toList());
    }

    public CategoryDto getById(UUID id) {
        return categoryDtoConverter.convertToCategoryDto(categoryRepository.findById(id).orElseThrow(
                () -> new CategoryNotFoundException("Category not found with id: " + id))
        );
    }

    protected Category findById(UUID id) {
        return categoryRepository.findById(id).orElseThrow(
                () -> new CategoryNotFoundException("Category not found with id: " + id)
        );
    }
}
