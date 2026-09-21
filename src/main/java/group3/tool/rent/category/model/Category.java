package group3.tool.rent.category.model;

import java.util.ArrayList;
import java.util.List;

import group3.tool.rent.product.model.Product;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "categories")
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String description;
    

    @ManyToMany(mappedBy = "categories", fetch = FetchType.LAZY)
    private List<Product> products = new ArrayList<>();
}