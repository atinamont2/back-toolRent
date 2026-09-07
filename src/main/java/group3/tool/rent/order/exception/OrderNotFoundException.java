package group3.tool.rent.order.exception;

public class OrderNotFoundException extends RuntimeException {
    public OrderNotFoundException(String message) {
        super(message);
    }
    public OrderNotFoundException(Long id) {
        super("No se encontró el pedido - id: " + id);
    }
}
