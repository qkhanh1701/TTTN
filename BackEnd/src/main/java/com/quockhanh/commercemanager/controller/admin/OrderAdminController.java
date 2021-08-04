package com.quockhanh.commercemanager.controller.admin;

import com.quockhanh.commercemanager.converter.OrderConverter;
import com.quockhanh.commercemanager.repository.OrderRepository;
import com.quockhanh.commercemanager.repository.UserRepository;
import com.quockhanh.commercemanager.services.OrderDetailsService;
import com.quockhanh.commercemanager.services.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/order")
public class OrderAdminController {

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

    @GetMapping("/listOrders")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> getAllOrders() {
        return ResponseEntity.ok().body(orderService.getAllOrders());
    }

    @GetMapping("/get-top-10")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> getTop10Orders() {
        return ResponseEntity.ok().body(orderService.getTop10Orders());
    }
}
