package group3.tool.rent.order.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderRequestDTO {
    private Long userId;
    private LocalDate startDate;
    private LocalDate endDate;
    private List<OrderItemRequestDTO> items;
}
