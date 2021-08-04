package com.quockhanh.commercemanager.repository;

import com.quockhanh.commercemanager.model.AddCart;
import com.quockhanh.commercemanager.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Repository
public interface CartRepository extends JpaRepository<AddCart, Long> {
    List<AddCart> findAddCartByUserId(Long userId);

    Optional<AddCart> findAddCartByProductAndUserId(Product product, Long userId);

    AddCart findAddCartById(Long id);

    @Transactional
    void deleteAddCartsByUserId(Long id);

    @Transactional
    void deleteAddCartById(Long id);

    long countByUserId(Long userId);
}
