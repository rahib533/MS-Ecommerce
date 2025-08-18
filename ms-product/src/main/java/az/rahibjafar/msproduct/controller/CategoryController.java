package az.rahibjafar.msproduct.controller;

import az.rahibjafar.msproduct.dto.CategoryDto;
import az.rahibjafar.msproduct.dto.CreateCategoryRequest;
import az.rahibjafar.msproduct.service.CategoryService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/v1/category")
public class CategoryController {
    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping("/getAll")
    public List<CategoryDto> getAll() {
        return categoryService.findAll();
    }

    @GetMapping("get/{id}")
    public CategoryDto get(@PathVariable UUID id) {
        return categoryService.getById(id);
    }

    @PostMapping("/create")
    public CategoryDto create(@RequestBody CreateCategoryRequest createCategoryRequest) {
        return categoryService.create(createCategoryRequest);
    }
}
