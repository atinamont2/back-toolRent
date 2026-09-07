package group3.tool.rent.order.dto;

import group3.tool.rent.product.model.Product;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderItemDTO {
    private Long id;
    private Product product;
    private Integer quantity;
    private Double unitPrice;
    private Double subtotal;
}
