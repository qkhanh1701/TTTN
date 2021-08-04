package com.quockhanh.commercemanager.converter;

import com.quockhanh.commercemanager.model.DTO.OrderDTO;
import com.quockhanh.commercemanager.model.Order;
import org.springframework.stereotype.Component;

@Component
public class OrderConverter {

    public OrderDTO toDTO(Order order){
        OrderDTO dto = new OrderDTO();
        dto.setId(order.getId());
        dto.setOrder_date(order.getOrder_date());
        dto.setAmount(order.getAmount());
        dto.setAddress(order.getAddress());
        dto.setReceiver(order.getReceiver());
        dto.setPhone_number(order.getPhone_number());
        dto.setStatus(order.getStatus());
        dto.setUser_id(order.getUser().getId());
        return dto;
    }
}
