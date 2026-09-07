package group3.tool.rent.product.service;

import group3.tool.rent.product.dto.ProductDTO;
import group3.tool.rent.product.exception.ProductNotFoundException;
import group3.tool.rent.product.model.Product;
import group3.tool.rent.product.repository.ProductRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class ProductService {

    private final ProductRepository productoRepository;

    public ProductService(ProductRepository productoRepository) {
        this.productoRepository = productoRepository;
    }

    public List<Product> findAll() {
        return productoRepository.findAll();
    }

    public ProductDTO findById(Long id) {
        Product prod = productoRepository.findById(id).orElse(null);
        if (prod == null) {
            throw new ProductNotFoundException("Producto no encontrado con id: " + id );
        }

        return new ProductDTO(
                prod.getId(),
                prod.getName(),
                prod.getDescription(),
                prod.getPrice(),
                prod.getIsAvailable(),
                prod.getAddress(),
                prod.getOwner()
        );
    }

    public void deleteProductById(Long id) {
        productoRepository.deleteById(id);
    }

    public Product saveProduct(Product product) {
        return productoRepository.save(product);
    }


}
