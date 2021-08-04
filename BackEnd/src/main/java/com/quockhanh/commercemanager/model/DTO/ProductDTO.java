package com.quockhanh.commercemanager.model.DTO;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Data
@ToString
public class ProductDTO {
    private Long id;
    private String name;
    private Float price;
    private Integer promotion;
    private String description;
    private String image;
    private Integer deletestatus;
    private Long categoryId;
    private Long quantity;
}
