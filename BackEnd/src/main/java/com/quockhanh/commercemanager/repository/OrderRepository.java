package com.quockhanh.commercemanager.repository;

import com.quockhanh.commercemanager.model.Order;
import com.quockhanh.commercemanager.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findOrdersByUserOrderByIdDesc(User user);
    Order findOrderById(Long id);
    List<Order> findAllByOrderByIdDesc();
    List<Order> findTop10ByOrderByIdDesc();
}
