package com.quockhanh.commercemanager.repository;

import com.quockhanh.commercemanager.model.Product;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends CrudRepository<Product, Long> {

    List<Product> findAllByOrderByIdDesc();

    List<Product> findTop8ByPromotionGreaterThanOrderByIdDesc(Integer promotion);
    Product findProductById(Long id);
    Product findProductByName(String name);
}
