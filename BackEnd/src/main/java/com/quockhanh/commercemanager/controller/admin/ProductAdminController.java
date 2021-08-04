package com.quockhanh.commercemanager.controller.admin;

import com.quockhanh.commercemanager.converter.ImageDetailsConverter;
import com.quockhanh.commercemanager.converter.ProductConverter;
import com.quockhanh.commercemanager.model.Category;
import com.quockhanh.commercemanager.model.DTO.ImageDetailsDTO;
import com.quockhanh.commercemanager.model.DTO.ProductDTO;
import com.quockhanh.commercemanager.model.Product;
import com.quockhanh.commercemanager.services.CategoryService;
import com.quockhanh.commercemanager.services.ImageDetailsService;
import com.quockhanh.commercemanager.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/admin")
public class ProductAdminController {

    @Autowired
    private ProductService productService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private ImageDetailsService imageDetailsService;

    @Autowired
    private ProductConverter productConverter;

    @PostMapping("/product")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> createProduct(@RequestBody ProductDTO dto) {
        System.out.println(dto.toString());
        Product product = new Product();
        product.setName(dto.getName());
        product.setPrice(dto.getPrice());
        product.setPromotion(dto.getPromotion());
        product.setDescription(dto.getDescription());
        product.setImage(dto.getImage());
        product.setDeletestatus(dto.getDeletestatus());
        product.setQuantity(dto.getQuantity());
        Category category = categoryService.findById(dto.getCategoryId());
        product.setCategory(category);
        product.toString();
        productService.save(product);
        return ResponseEntity.ok().body("Product has been created successfully");
    }

    @DeleteMapping("/product/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> deleteProduct(@PathVariable Long id) {
        Product product = productService.getProductById(id);
        if (Objects.isNull(product)) {
            throw new RuntimeException("Can't find Product");
        }
        if (product.getOrderDetails().stream().count() == 0) {
            imageDetailsService.delete(product.getId());
            productService.delete(product);
            return ResponseEntity.ok().body("Product has been deleted successfully");
        } else {
            product.setDeletestatus(1);
            productService.save(product);
            return ResponseEntity.ok().body("Delete Status has been set to 1");
        }
    }

    @PutMapping("/product/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> updateProduct(@RequestBody ProductDTO dto,
                                           @PathVariable Long id) {
        Product product = productService.getProductById(id);
        if (Objects.isNull(product)) {
            throw new RuntimeException("Can't find Product");
        }
        product.setName(dto.getName());
        product.setPrice(dto.getPrice());
        product.setPromotion(dto.getPromotion());
        product.setDescription(dto.getDescription());
        product.setImage(dto.getImage());
        product.setDeletestatus(dto.getDeletestatus());
        product.setQuantity(dto.getQuantity());
        Category category = categoryService.findById(dto.getCategoryId());
        product.setCategory(category);
        product.toString();
//        putImage(product.getId(), list);
        productService.save(product);
        return ResponseEntity.ok().body("Product has been updated successfully");
    }

    @Transactional
    @PutMapping("product/detail/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> putImage(@PathVariable Long id, @RequestBody List<ImageDetailsDTO> list) {
        System.out.println(id);
        System.out.println(list);

        if (Objects.isNull(productService.getProductById(id))) {
            throw new RuntimeException("Product not found");
        }

        imageDetailsService.delete(id);

        list.stream().forEach(s -> {
            imageDetailsService.save(new ImageDetailsConverter().toEntity(s));
        });

        return ResponseEntity.ok().body("Product Images has been updated successfully");
    }
}
