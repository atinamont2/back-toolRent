package group3.tool.rent.product.service;

import group3.tool.rent.product.dto.ProductDTO;
import group3.tool.rent.product.exception.ProductNotFoundException;
import group3.tool.rent.product.model.Product;
import group3.tool.rent.product.repository.ProductRepository;
import group3.tool.rent.user.exception.UserNotFoundException;
import group3.tool.rent.user.model.User;
import group3.tool.rent.user.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class ProductService {

    private final ProductRepository productRepository;
    private final UserRepository userRepository;

    public ProductService(ProductRepository productRepository, UserRepository userRepository) {
        this.productRepository = productRepository;
        this.userRepository = userRepository;
    }

    public List<ProductDTO> findAll() {
        List<Product> products = productRepository.findAll();
        List<ProductDTO> productDTOs = new ArrayList<>();

        for (Product product : products) {
            productDTOs.add(toDTO(product));
        }

        return productDTOs;
    }

    public ProductDTO findById(Long id) {
        Product product = productRepository.findById(id).orElse(null);
        if (product == null) {
            throw new ProductNotFoundException(id);
        }

        return toDTO(product);
    }

    public List<ProductDTO> search(String text) {
        List<Product> products = productRepository.findByNameContainingIgnoreCaseOrDescriptionContainingIgnoreCase(text, text);
        List<ProductDTO> productDTOs = new ArrayList<>();

        for (Product product : products) {
            productDTOs.add(toDTO(product));
        }

        return productDTOs;
    }

    public ProductDTO saveProduct(ProductDTO productDTO) {
        User owner = userRepository.findById(productDTO.getOwnerId()).orElse(null);
        if (owner == null) {
            throw new UserNotFoundException("Usuario no encontrado con id: " + productDTO.getOwnerId());
        }

        Product product = new Product();
        product.setName(productDTO.getName());
        product.setDescription(productDTO.getDescription());
        product.setPrice(productDTO.getPrice());
        product.setIsAvailable(productDTO.getIsAvailable());
        product.setAddress(productDTO.getAddress());
        product.setOwner(owner);

        Product savedProduct = productRepository.save(product);
        return toDTO(savedProduct);
    }

    public ProductDTO updateProduct(Long id, ProductDTO productDTO) {
        Product existingProduct = productRepository.findById(id).orElse(null);
        if (existingProduct == null) {
            throw new ProductNotFoundException(id);
        }

        existingProduct.setName(productDTO.getName());
        existingProduct.setDescription(productDTO.getDescription());
        existingProduct.setPrice(productDTO.getPrice());
        existingProduct.setIsAvailable(productDTO.getIsAvailable());
        existingProduct.setAddress(productDTO.getAddress());

        Product updatedProduct = productRepository.save(existingProduct);
        return toDTO(updatedProduct);
    }

    public void deleteProductById(Long id) {
        Product product = productRepository.findById(id).orElse(null);
        if (product == null) {
            throw new ProductNotFoundException(id);
        }

        productRepository.deleteById(id);
    }

    private ProductDTO toDTO(Product product) {
        return new ProductDTO(
                product.getId(),
                product.getName(),
                product.getDescription(),
                product.getPrice(),
                product.getIsAvailable(),
                product.getAddress(),
                product.getOwner() != null ? product.getOwner().getId() : null
        );
    }
}