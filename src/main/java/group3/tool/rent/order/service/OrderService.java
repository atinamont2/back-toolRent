package group3.tool.rent.order.service;

import group3.tool.rent.order.dto.OrderDTO;
import group3.tool.rent.order.dto.OrderItemDTO;
import group3.tool.rent.order.dto.OrderItemRequestDTO;
import group3.tool.rent.order.dto.OrderRequestDTO;
import group3.tool.rent.order.exception.OrderNotFoundException;
import group3.tool.rent.order.exception.ProductNotAvailableException;
import group3.tool.rent.order.model.Order;
import group3.tool.rent.order.model.OrderItem;
import group3.tool.rent.order.model.OrderStatus;
import group3.tool.rent.order.repository.OrderRepository;
import group3.tool.rent.product.exception.ProductNotFoundException;
import group3.tool.rent.product.model.Product;
import group3.tool.rent.product.repository.ProductRepository;
import group3.tool.rent.user.exception.UserNotFoundException;
import group3.tool.rent.user.model.User;
import group3.tool.rent.user.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

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

    public List<Order> findAll() {
        return orderRepository.findAll();
    }

    public OrderDTO findById(Long id) {
        Order order = orderRepository.findById(id).orElse(null);
        if (order == null) {
            throw new OrderNotFoundException("Pedido no encontrado con id: " + id);
        }

        return toDTO(order);
    }

    public Order createOrder(OrderRequestDTO request) {
        User user = userRepository.findById(request.getUserId()).orElse(null);
        if (user == null) {
            throw new UserNotFoundException("Usuario no encontrado con id: " + request.getUserId());
        }

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
                throw new ProductNotFoundException("Producto no encontrado con id: " + itemRequest.getProductId());
            }

            if (!Boolean.TRUE.equals(product.getIsAvailable())) {
                throw new ProductNotAvailableException(
                        "El producto '" + product.getName() + "' no está disponible para alquilar"
                );
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

        return orderRepository.save(order);
    }

    public Order cancelOrder(Long id) {
        Order order = orderRepository.findById(id).orElse(null);
        if (order == null) {
            throw new OrderNotFoundException("Pedido no encontrado con id: " + id);
        }

        order.setStatus(OrderStatus.CANCELLED);

        for (OrderItem item : order.getItems()) {
            Product product = item.getProduct();
            product.setIsAvailable(true);
            productRepository.save(product);
        }

        return orderRepository.save(order);
    }

    public void deleteOrderById(Long id) {
        orderRepository.deleteById(id);
    }

    private OrderDTO toDTO(Order order) {
        List<OrderItemDTO> itemDTOs = new ArrayList<>();
        for (OrderItem item : order.getItems()) {
            itemDTOs.add(new OrderItemDTO(
                    item.getId(),
                    item.getProduct(),
                    item.getQuantity(),
                    item.getUnitPrice(),
                    item.getSubtotal()
            ));
        }

        return new OrderDTO(
                order.getId(),
                order.getUser(),
                order.getOrderDate(),
                order.getStartDate(),
                order.getEndDate(),
                order.getTotalAmount(),
                order.getStatus(),
                itemDTOs
        );
    }
}
