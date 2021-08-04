package com.quockhanh.commercemanager.controller;

import com.quockhanh.commercemanager.model.DTO.CategoryDTO;
import com.quockhanh.commercemanager.services.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/user")
public class CategoryController {
    @Autowired
    CategoryService categoryService;

    @GetMapping("/category")
    public ResponseEntity<?> getCategory(){
        List<CategoryDTO> categoryList = categoryService.getCategory();
        return new ResponseEntity<> (categoryList, HttpStatus.OK);
    }

    @GetMapping("/category/{categoryName}")
    public ResponseEntity<?> getCategoryName(@PathVariable String categoryName){
        CategoryDTO category = categoryService.getCategoryByName(categoryName);
        return new ResponseEntity<> (category, HttpStatus.OK);
    }

    @GetMapping("/category/id/{id}")
    public ResponseEntity<?> getCategoryName(@PathVariable Long id){
        CategoryDTO category = categoryService.getCategoryById(id);
        return new ResponseEntity<> (category, HttpStatus.OK);
    }
}
