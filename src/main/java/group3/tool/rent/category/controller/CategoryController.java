package group3.tool.rent.category.controller;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import group3.tool.rent.category.dto.CategoryDTO;
import group3.tool.rent.category.model.Category;
import group3.tool.rent.category.service.CategoryService;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {

    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping
    public ResponseEntity<List<Category>> findAll() {
        return ResponseEntity.ok(categoryService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategoryDTO> findById(@PathVariable Long id) {
        return ResponseEntity.ok(categoryService.findById(id));
    }
    @PostMapping
    public ResponseEntity<Category> create(@RequestBody Category category) {
    return ResponseEntity.ok(categoryService.create(category));
    }
}