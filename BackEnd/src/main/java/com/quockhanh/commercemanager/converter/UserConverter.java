package com.quockhanh.commercemanager.converter;

import com.quockhanh.commercemanager.model.DTO.OrderDTO;
import com.quockhanh.commercemanager.model.DTO.UserDTO;
import com.quockhanh.commercemanager.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class UserConverter {
    @Autowired
    OrderConverter orderConverter;

    public UserDTO toDTO(User user){
        UserDTO dto = new UserDTO();
        dto.setId(user.getId());
        dto.setUsername(user.getUsername());
        dto.setEmail(user.getEmail());
        dto.setPassword(user.getPassword());
        dto.setPhone(user.getPhone());
        dto.setAddress(user.getAddress());
        dto.setFirstname(user.getFirstname());
        dto.setLastname(user.getLastname());
        dto.setDeletestatus(user.getDeletestatus());
        dto.setResetToken(user.getResetToken());
        List<OrderDTO> listDto = new ArrayList<>();
        user.getOrders().stream().forEach(s -> {
            OrderDTO orderDTO = orderConverter.toDTO(s);
            listDto.add(orderDTO);
        });
        dto.setOrders(listDto);
        dto.setRoles(user.getRoles());
        return dto;
    }
}
