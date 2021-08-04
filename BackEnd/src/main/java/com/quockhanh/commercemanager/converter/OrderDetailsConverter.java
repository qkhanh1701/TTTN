package com.quockhanh.commercemanager.converter;

import com.quockhanh.commercemanager.model.DTO.OrderDetailsDTO;
import com.quockhanh.commercemanager.model.OrderDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class OrderDetailsConverter {
    @Autowired
    private OrderConverter orderConverter;

    @Autowired
    private ProductConverter productConverter;

    public OrderDetailsDTO toDTO(OrderDetails orderDetails) {
        OrderDetailsDTO dto = new OrderDetailsDTO();
        dto.setOrder(orderConverter.toDTO(orderDetails.getOrder()));
        dto.setProduct(productConverter.toDTO(orderDetails.getProduct()));
        dto.setQuantity(orderDetails.getQuantity());
        dto.setAmount(orderDetails.getAmount());
        dto.setDiscount(orderDetails.getDiscount());
        return dto;
    }
}
