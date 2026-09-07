package group3.tool.rent.product.model;

import java.util.ArrayList;
import java.util.List;
import group3.tool.rent.category.model.Category;

import group3.tool.rent.user.model.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "products")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String description;
    private Double price;
    private Boolean isAvailable;
    private String address;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="owner_id", nullable=false)
    private User owner;
    @ManyToMany(fetch = FetchType.LAZY)
      @JoinTable(    
        name = "product_categories",
        joinColumns = @JoinColumn(name = "product_id"), 
        inverseJoinColumns = @JoinColumn(name = "category_id")
                )    
    private List<Category> categories = new ArrayList<>();
}
