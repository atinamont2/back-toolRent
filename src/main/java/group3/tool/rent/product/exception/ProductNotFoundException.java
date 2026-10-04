package group3.tool.rent.product.exception;

import group3.tool.rent.common.ResourceNotFoundException;

public class ProductNotFoundException extends ResourceNotFoundException {
    public ProductNotFoundException(String message) {
        super(message);
    }
    public ProductNotFoundException(Long id) {
        super("No se encontró el producto - id: " + id);
    }
}