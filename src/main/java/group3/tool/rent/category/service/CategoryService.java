package group3.tool.rent.category.service;

import group3.tool.rent.category.exception.CategoryNotFoundException;
import group3.tool.rent.category.repository.CategoryRepository;
import group3.tool.rent.category.dto.CategoryDTO;
import group3.tool.rent.category.model.Category;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public List<Category> findAll() {
        return categoryRepository.findAll();
    }

    public CategoryDTO findById(Long id) {
        Category cat = categoryRepository.findById(id).orElse(null);
        if (cat == null) {
            throw new CategoryNotFoundException("Categoría no encontrada con id: " + id );
        }

        return new CategoryDTO(
                cat.getId(),
                cat.getName(),
                cat.getDescription()
        );        
    }

    public Category create(Category category) {
    return categoryRepository.save(category);
}


}