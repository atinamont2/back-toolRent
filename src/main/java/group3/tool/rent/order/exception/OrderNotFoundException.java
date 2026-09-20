package group3.tool.rent.order.exception;

import group3.tool.rent.common.ResourceNotFoundException;

public class OrderNotFoundException extends ResourceNotFoundException {
    public OrderNotFoundException(String message) {
        super(message);
    }
    public OrderNotFoundException(Long id) {
        super("No se encontró el pedido - id: " + id);
    }
}
