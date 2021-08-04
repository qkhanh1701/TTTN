package com.quockhanh.commercemanager.controller;

import com.quockhanh.commercemanager.converter.OrderConverter;
import com.quockhanh.commercemanager.message.request.ApiResponse;
import com.quockhanh.commercemanager.model.DTO.OrderDTO;
import com.quockhanh.commercemanager.model.DTO.OrderDetailsDTO;
import com.quockhanh.commercemanager.model.Order;
import com.quockhanh.commercemanager.model.User;
import com.quockhanh.commercemanager.repository.OrderRepository;
import com.quockhanh.commercemanager.repository.UserRepository;
import com.quockhanh.commercemanager.services.OrderDetailsService;
import com.quockhanh.commercemanager.services.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/order")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private OrderDetailsService orderDetailsService;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderConverter orderConverter;

    @PostMapping("/checkout")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> addOrder(@RequestBody OrderDTO orderRequest) throws Exception {
        Optional<User> temp = userRepository.findById(orderRequest.getUser_id());
        AtomicReference<User> user = new AtomicReference<>(new User());
        temp.ifPresent(s -> {
            user.set(s);
        });
        boolean result = orderService.sendEmail("Order","Successlly", user.get().getEmail());
        if(result){
            try {
                OrderDTO obj = orderService.addOrder(orderRequest);
                return ResponseEntity.ok(obj);
            } catch (Exception e) {
                e.printStackTrace();
                return ResponseEntity.badRequest().body(new ApiResponse(e.getMessage(), ""));
            }
        }else
        {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Sending fail...");
        }
    }

    @GetMapping("/listOrder/{id}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> listOrder(@PathVariable Long id)
    {
        try {
            List<OrderDTO> listDTO = orderService.getListOrderByUserId(id);
            return ResponseEntity.ok(listDTO);
        }catch(Exception e){
            e.printStackTrace();
            return ResponseEntity.badRequest().body(new ApiResponse(e.getMessage(), ""));
        }
    }

    @PutMapping("/listOrder/cancel/{id}")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<?> cancel(@PathVariable long id, @RequestBody HashMap<String, Integer> status)
    {
        try {
            String keys[] = { "status" };
            Order order = orderService.getOrderById(id);
            Integer status1 = status.get("status");
            order.setStatus(status1);
            Order updateOrder = orderRepository.save(order);
            Long orderId = updateOrder.getId();
            OrderDTO obj = orderConverter.toDTO(orderService.getOrderById(orderId));
            return ResponseEntity.ok(obj);
            //return ResponseEntity.ok("Update quantity successly");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body(new ApiResponse(e.getMessage(), ""));
        }
    }

    @GetMapping("/listOrderDetails/{OrderId}")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<?> getListOrderDetails(@PathVariable Long OrderId)
    {
        try {
            List<OrderDetailsDTO> listOrderDetails = orderDetailsService.getOrderDetailsList(OrderId);
            return ResponseEntity.ok(listOrderDetails);
        }catch(Exception e){
            e.printStackTrace();
            return ResponseEntity.badRequest().body(new ApiResponse(e.getMessage(), ""));
        }
    }
}
