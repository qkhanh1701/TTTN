package com.quockhanh.commercemanager.converter;

import com.quockhanh.commercemanager.model.AddCart;
import com.quockhanh.commercemanager.model.DTO.AddCartDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AddCartConverter {
    @Autowired
    private ProductConverter productConverter;

    public AddCartDTO toDTO(AddCart addCart){
        AddCartDTO dto = new AddCartDTO();
        dto.setId(addCart.getId());
        dto.setProduct(productConverter.toDTO(addCart.getProduct()));
        dto.setQuantity(addCart.getQuantity());
        dto.setPrice(addCart.getPrice());
        dto.setUser_id(addCart.getUserId());
        return dto;
    }
}
