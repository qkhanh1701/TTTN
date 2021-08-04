package com.quockhanh.commercemanager.controller.admin;

import com.quockhanh.commercemanager.model.Category;
import com.quockhanh.commercemanager.model.DTO.CategoryDTO;
import com.quockhanh.commercemanager.services.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/admin")
public class CategoryAdminController {
    @Autowired
    CategoryService categoryService;

    @PostMapping("/add-category")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> createCategory(@RequestBody CategoryDTO categoryDTO) {
        System.out.println(categoryDTO.toString());
        Category category = new Category();
        category.setCategoryname(categoryDTO.getCategoryName());
        category.setDeletestatus(0);
        category.setProducts(null);
        categoryService.save(category);
        return ResponseEntity.ok().body(category);
    }

    @DeleteMapping("/category/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> deleteCategory(@PathVariable Long id) {
        Category category = categoryService.findById(id);
        if (Objects.isNull(category)) {
            throw new RuntimeException("Can't find Category");
        }
        if (category.getProducts().stream().count() == 0) {
            categoryService.delete(id);
            return ResponseEntity.ok().body("Category has been deleted successfully");
        } else {
            category.setDeletestatus(1);
            categoryService.save(category);
            return ResponseEntity.badRequest().body("Delete Status has been set to 1");
        }
    }

    @PutMapping("/category/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> updateCategory(@PathVariable Long id, @RequestBody CategoryDTO categoryDTO) {
        System.out.println(categoryDTO.getCategoryName()+id);
        Category category = categoryService.findById(id);
        if (Objects.isNull(category)) {
            throw new RuntimeException("Can't find Category");
        }
        category.setCategoryname(categoryDTO.getCategoryName());
        categoryService.save(category);
        return ResponseEntity.ok().body("Category has been updated successfully");
    }
}
