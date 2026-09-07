package group3.tool.rent.product.exception;

public class ProductNotFoundException extends RuntimeException {
    public ProductNotFoundException(String message) {
        super(message);
    }
    public ProductNotFoundException(Long id) {
        super("No se encontró el producto - id: " + id);
    }
}
