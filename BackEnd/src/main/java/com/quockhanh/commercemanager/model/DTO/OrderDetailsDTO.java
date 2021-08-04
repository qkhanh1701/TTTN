package com.quockhanh.commercemanager.model.DTO;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Data
public class OrderDetailsDTO {
    private OrderDTO order;

    private ProductDTO product;

    private Long quantity;

    private Double amount;

    private Integer discount;
}
