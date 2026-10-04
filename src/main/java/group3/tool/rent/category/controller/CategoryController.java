package group3.tool.rent.category.controller;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import group3.tool.rent.category.dto.CategoryDTO;
import group3.tool.rent.category.dto.CategoryRequest;
import org.springframework.http.HttpStatus;
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
    public ResponseEntity<List<CategoryDTO>> findAll() {
        return ResponseEntity.ok(categoryService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategoryDTO> findById(@PathVariable Long id) {
        return ResponseEntity.ok(categoryService.findById(id));
    }
    @PostMapping
    public ResponseEntity<CategoryDTO> create(@RequestBody CategoryRequest category) {
        return ResponseEntity.ok(categoryService.create(category));
    }
    @PutMapping("/{id}")
    public ResponseEntity<CategoryDTO> update(
        @PathVariable Long id,
        @RequestBody CategoryRequest request) {

    return ResponseEntity.ok(categoryService.update(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        categoryService.delete(id);
        return ResponseEntity.noContent().build();
    }
}