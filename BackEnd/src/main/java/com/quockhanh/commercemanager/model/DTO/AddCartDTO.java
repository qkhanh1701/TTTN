package com.quockhanh.commercemanager.model.DTO;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Data
public class AddCartDTO {
    private Long id;

    ProductDTO product;

    private Long quantity;

    private double price;

    private Long user_id;
}
