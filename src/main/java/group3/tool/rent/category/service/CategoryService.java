package group3.tool.rent.category.service;

import group3.tool.rent.category.dto.CategoryRequest;
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

    public List<CategoryDTO> findAll() {
        return categoryRepository.findAll().stream()
                .map(cat -> new CategoryDTO(
                        cat.getId(),
                        cat.getName(),
                        cat.getDescription()
                ))
                .toList();
    }

    public CategoryDTO findById(Long id) {        
        Category cat = categoryRepository.findById(id)
            .orElseThrow(() -> new CategoryNotFoundException("Categoría no encontrada con id: " + id));

        return new CategoryDTO(
                cat.getId(),
                cat.getName(),
                cat.getDescription()
        );        
    }

    public CategoryDTO create(CategoryRequest request) {
        Category category = new Category();
        category.setName(request.getName());
        category.setDescription(request.getDescription());

    Category savedCategory = categoryRepository.save(category);
    return new CategoryDTO(
            savedCategory.getId(),
            savedCategory.getName(),
            savedCategory.getDescription()
    );
    }
    public CategoryDTO update(Long id, CategoryRequest request) {
    Category existingCategory = categoryRepository.findById(id)
            .orElseThrow(() -> new CategoryNotFoundException("Categoría no encontrada con id: " + id));

    existingCategory.setName(request.getName());
    existingCategory.setDescription(request.getDescription());

    Category updatedCategory = categoryRepository.save(existingCategory);

    return new CategoryDTO(
            updatedCategory.getId(),
            updatedCategory.getName(),
            updatedCategory.getDescription()
    );
    }

    public void delete(Long id) {
        Category category = categoryRepository.findById(id).orElse(null);

        if (category == null) {
            throw new CategoryNotFoundException(
                    "Categoría no encontrada con id: " + id
            );
        }

        categoryRepository.delete(category);
    }
}