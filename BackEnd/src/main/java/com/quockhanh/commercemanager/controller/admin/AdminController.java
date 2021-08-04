package com.quockhanh.commercemanager.controller.admin;

import com.quockhanh.commercemanager.model.DTO.OrderDTO;
import com.quockhanh.commercemanager.services.OrderService;
import com.quockhanh.commercemanager.services.ProductService;
import com.quockhanh.commercemanager.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/admin")
public class AdminController {

    @Autowired
    ProductService productService;

    @Autowired
    UserService userService;

    @Autowired
    OrderService orderService;

    @GetMapping("/get-statistics")
//    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> getAllStatistics() {
        List<Number> statistics = new ArrayList<>();
        statistics.add(productService.getAllProducts().stream().count());
        statistics.add(userService.getAllUser().stream().count());
        statistics.add(orderService.getAllOrders().stream().count());
        double sum = orderService.getAllOrders().stream().mapToDouble(OrderDTO::getAmount).sum();
        statistics.add(sum);
        System.out.println(statistics);
        return ResponseEntity.ok().body(statistics);
    }
}
