package group3.tool.rent.category.exception;

import group3.tool.rent.common.ResourceNotFoundException;

public class CategoryNotFoundException extends ResourceNotFoundException {
    public CategoryNotFoundException(String message) {
        super(message);
    }
    
}