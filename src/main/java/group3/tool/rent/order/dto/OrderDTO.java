package group3.tool.rent.order.dto;

import group3.tool.rent.order.model.OrderStatus;
import group3.tool.rent.user.model.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderDTO {
    private Long id;
    private User user;
    private LocalDateTime orderDate;
    private LocalDate startDate;
    private LocalDate endDate;
    private Double totalAmount;
    private OrderStatus status;
    private List<OrderItemDTO> items;
}
