package com.quockhanh.commercemanager.services.ServicesImpl;

import com.quockhanh.commercemanager.converter.OrderConverter;
import com.quockhanh.commercemanager.converter.OrderDetailsConverter;
import com.quockhanh.commercemanager.converter.ProductConverter;
import com.quockhanh.commercemanager.message.request.OrderDetailsRequest;
import com.quockhanh.commercemanager.model.DTO.OrderDetailsDTO;
import com.quockhanh.commercemanager.model.Order;
import com.quockhanh.commercemanager.model.OrderDetails;
import com.quockhanh.commercemanager.model.Product;
import com.quockhanh.commercemanager.repository.OrderDetailsRepository;
import com.quockhanh.commercemanager.repository.OrderRepository;
import com.quockhanh.commercemanager.services.OrderDetailsService;
import com.quockhanh.commercemanager.services.OrderService;
import com.quockhanh.commercemanager.services.ProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class OrderDetailsServiceImpl implements OrderDetailsService {

    @Autowired
    private OrderService orderService;

    @Autowired
    private ProductService productService;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderDetailsRepository orderDetailsRepository;

    @Autowired
    private OrderDetailsConverter orderDetailsConverter;

    @Autowired
    private OrderConverter orderConverter;

    @Autowired
    private ProductConverter productConverter;

    private static final Logger logger = LoggerFactory.getLogger(OrderServiceImpl.class);

    @Override
    public OrderDetailsDTO addOrderDetailByOrderId(OrderDetailsRequest orderDetailsRequest) throws Exception {
        try {
            OrderDetails obj = new OrderDetails();
            Order order = orderService.getOrderById(orderDetailsRequest.getOrderId());
            obj.setOrder(order);
            Product product = productService.getProductById(orderDetailsRequest.getProductId());
            obj.setProduct(product);
            obj.setQuantity(orderDetailsRequest.getQuantity());
            obj.setAmount(orderDetailsRequest.getAmount());
            obj.setDiscount(orderDetailsRequest.getDiscount());
            orderDetailsRepository.save(obj);
            return  orderDetailsConverter.toDTO(obj);
        }catch(Exception e) {
            e.printStackTrace();
            logger.error(""+e.getMessage());
            throw new Exception(e.getMessage());
        }
    }

    @Override
    public List<OrderDetailsDTO> getOrderDetailsList(Long orderId) throws Exception {
        Order order = orderRepository.findOrderById(orderId);
        String a = order.getAddress();
        System.out.println("SSSS" + a);
        List<OrderDetails> listOrderDetails = orderDetailsRepository.findOrderDetailsByOrder(order);
        List<OrderDetailsDTO> listOrderDetailsDTO = new ArrayList<>();
        listOrderDetails.stream().forEach(
                (s) -> {
                    listOrderDetailsDTO.add(orderDetailsConverter.toDTO(s));
                }
        );
        return listOrderDetailsDTO;
    }
}
