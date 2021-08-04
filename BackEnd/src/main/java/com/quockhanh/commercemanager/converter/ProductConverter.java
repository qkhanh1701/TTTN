package com.quockhanh.commercemanager.converter;

import com.quockhanh.commercemanager.model.DTO.ProductDTO;
import com.quockhanh.commercemanager.model.Product;
import com.quockhanh.commercemanager.services.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ProductConverter {
    @Autowired
    CategoryService categoryService;

    public ProductDTO toDTO(Product product){
        ProductDTO dto = new ProductDTO();
        dto.setId(product.getId());
        dto.setName(product.getName());
        dto.setPrice(product.getPrice());
        dto.setImage(product.getImage());
        dto.setDeletestatus(product.getDeletestatus());
        dto.setDescription(product.getDescription());
        dto.setPromotion(product.getPromotion());
        dto.setCategoryId(product.getCategory().getId());
        dto.setQuantity(product.getQuantity());
        return dto;
    }

    private Product toEntity(ProductDTO dto) {
        Product product = new Product();
        product.setId(dto.getId());
        product.setName(dto.getName());
        product.setPrice(dto.getPrice());
        product.setPromotion(dto.getPromotion());
        product.setDescription(dto.getDescription());
        product.setImage(dto.getImage());
        product.setDeletestatus(dto.getDeletestatus());
        product.setQuantity(dto.getQuantity());
//        Category
        return product;
    }
}
