package com.quockhanh.commercemanager.message.request;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Data
public class OrderDetailsRequest {
    private Long orderId;
    private Long productId;
    private Long quantity;
    private Double amount;
    private Integer discount;
}
