package group3.tool.rent.product.dto;

import group3.tool.rent.user.model.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductDTO {
    private Long id;
    private String name;
    private String description;
    private Double price;
    private Boolean isAvailable;
    private String address;
    private User owner;
}
