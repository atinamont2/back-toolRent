package group3.tool.rent.order.service;

import group3.tool.rent.common.ResourceNotFoundException;
import group3.tool.rent.order.dto.OrderDTO;
import group3.tool.rent.order.dto.OrderItemDTO;
import group3.tool.rent.order.dto.OrderItemRequestDTO;
import group3.tool.rent.order.dto.OrderRequestDTO;
import group3.tool.rent.order.exception.ProductNotAvailableException;
import group3.tool.rent.order.model.Order;
import group3.tool.rent.order.model.OrderItem;
import group3.tool.rent.order.model.OrderStatus;
import group3.tool.rent.order.repository.OrderRepository;
import group3.tool.rent.product.model.Product;
import group3.tool.rent.product.repository.ProductRepository;
import group3.tool.rent.user.model.User;
import group3.tool.rent.user.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class OrderService {

    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;

    public OrderService(OrderRepository orderRepository,
            UserRepository userRepository,
            ProductRepository productRepository) {
        this.orderRepository = orderRepository;
        this.userRepository = userRepository;
        this.productRepository = productRepository;
    }

    public List<OrderDTO> findAll(Authentication authentication) {
        User currentUser = getAuthenticatedUser(authentication);

        List<Order> orders = isAdmin(authentication)
                ? orderRepository.findAll()
                : orderRepository.findByUserId(currentUser.getId());

        return orders.stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public OrderDTO findById(Long id, Authentication authentication) {
        Order order = orderRepository.findById(id).orElse(null);
        if (order == null) {
            throw new ResourceNotFoundException("Pedido no encontrado con id: " + id);
        }

        assertOwnerOrAdmin(order, authentication);

        return toDTO(order);
    }

    public OrderDTO createOrder(OrderRequestDTO request, Authentication authentication) {
        User user = getAuthenticatedUser(authentication);

        Order order = new Order();
        order.setUser(user);
        order.setOrderDate(LocalDateTime.now());
        order.setStartDate(request.getStartDate());
        order.setEndDate(request.getEndDate());
        order.setStatus(OrderStatus.CONFIRMED);

        List<OrderItem> items = new ArrayList<>();
        double total = 0.0;

        for (OrderItemRequestDTO itemRequest : request.getItems()) {
            Product product = productRepository.findById(itemRequest.getProductId()).orElse(null);
            if (product == null) {
                throw new ResourceNotFoundException("Producto no encontrado con id: " + itemRequest.getProductId());
            }

            if (!Boolean.TRUE.equals(product.getIsAvailable())) {
                throw new ProductNotAvailableException(
                        "El producto '" + product.getName() + "' no está disponible para alquilar");
            }

            OrderItem item = new OrderItem();
            item.setOrder(order);
            item.setProduct(product);
            item.setQuantity(itemRequest.getQuantity());
            item.setUnitPrice(product.getPrice());
            item.setSubtotal(product.getPrice() * itemRequest.getQuantity());

            total += item.getSubtotal();
            items.add(item);

            product.setIsAvailable(false);
            productRepository.save(product);
        }

        order.setItems(items);
        order.setTotalAmount(total);

        Order savedOrder = orderRepository.save(order);
        return toDTO(savedOrder);
    }

    public OrderDTO cancelOrder(Long id, Authentication authentication) {
        Order order = orderRepository.findById(id).orElse(null);
        if (order == null) {
            throw new ResourceNotFoundException("Pedido no encontrado con id: " + id);
        }

        assertOwnerOrAdmin(order, authentication);

        order.setStatus(OrderStatus.CANCELLED);

        for (OrderItem item : order.getItems()) {
            Product product = item.getProduct();
            product.setIsAvailable(true);
            productRepository.save(product);
        }

        Order savedOrder = orderRepository.save(order);
        return toDTO(savedOrder);
    }

    public void deleteOrderById(Long id, Authentication authentication) {
        Order order = orderRepository.findById(id).orElse(null);
        if (order == null) {
            throw new ResourceNotFoundException("Pedido no encontrado con id: " + id);
        }

        assertOwnerOrAdmin(order, authentication);

        orderRepository.deleteById(id);
    }

    private User getAuthenticatedUser(Authentication authentication) {
        String email = authentication.getName();
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado: " + email));
    }

    private boolean isAdmin(Authentication authentication) {
        return authentication.getAuthorities().stream()
                .anyMatch(authority -> authority.getAuthority().toUpperCase().contains("ADMIN"));
    }

    private void assertOwnerOrAdmin(Order order, Authentication authentication) {
        User currentUser = getAuthenticatedUser(authentication);

        boolean isOwner = order.getUser().getId().equals(currentUser.getId());

        if (!isOwner && !isAdmin(authentication)) {
            throw new ResourceNotFoundException("Pedido no encontrado con id: " + order.getId());
        }
    }

    private OrderDTO toDTO(Order order) {
        List<OrderItemDTO> itemDTOs = order.getItems().stream()
                .map(item -> new OrderItemDTO(
                        item.getId(),
                        item.getProduct().getId(),
                        item.getQuantity(),
                        item.getUnitPrice(),
                        item.getSubtotal()))
                .collect(Collectors.toList());

        return new OrderDTO(
                order.getId(),
                order.getUser().getId(),
                order.getOrderDate(),
                order.getStartDate(),
                order.getEndDate(),
                order.getTotalAmount(),
                order.getStatus(),
                itemDTOs);
    }
}
