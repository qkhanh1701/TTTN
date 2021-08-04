package com.quockhanh.commercemanager.model.DTO;

import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Data
@ToString
public class CategoryDTO {
    private Long id;
    private String categoryName;
    private Integer deletestatus;
    private List<ProductDTO> products;
}
