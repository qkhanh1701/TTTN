package com.quockhanh.commercemanager.services;

import com.quockhanh.commercemanager.message.request.OrderDetailsRequest;
import com.quockhanh.commercemanager.model.DTO.OrderDetailsDTO;

import java.util.List;

public interface OrderDetailsService {
    OrderDetailsDTO addOrderDetailByOrderId(OrderDetailsRequest orderDetailsRequest) throws Exception;
    List<OrderDetailsDTO> getOrderDetailsList(Long orderId) throws Exception;
}
