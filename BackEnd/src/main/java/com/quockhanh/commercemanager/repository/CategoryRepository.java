package com.quockhanh.commercemanager.repository;

import com.quockhanh.commercemanager.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

    List<Category> findAllByOrderByIdDesc();

    Category findCategoryByCategoryname(String categoryName);

    Category findCategoryById(Long id);

    Optional<Category> findById(Long id);
}
