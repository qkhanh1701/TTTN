package com.quockhanh.commercemanager.controller;

import com.quockhanh.commercemanager.converter.AddCartConverter;
import com.quockhanh.commercemanager.message.request.AddCartRequest;
import com.quockhanh.commercemanager.message.request.ApiResponse;
import com.quockhanh.commercemanager.model.AddCart;
import com.quockhanh.commercemanager.model.DTO.AddCartDTO;
import com.quockhanh.commercemanager.repository.CartRepository;
import com.quockhanh.commercemanager.services.CartService;
import com.quockhanh.commercemanager.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/addcart")
public class AddCartController {
    @Autowired
    private CartService cartService;

    @Autowired
    private ProductService productService;

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private AddCartConverter addCartConverter;

    @RequestMapping("/addProduct")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> addCartwithProduct(@RequestBody AddCartRequest addCartRequest) {
        try {
            List<AddCartDTO> obj = cartService.addCartbyUserIdAndProductId(addCartRequest.getProductId(), addCartRequest.getUserId(), addCartRequest.getQty(), addCartRequest.getPrice());
            return ResponseEntity.ok(obj);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body(new ApiResponse(e.getMessage(), ""));
        }
    }

    @GetMapping("/getCartsByUserId/{userId}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> getCartsByUserId(@PathVariable Long userId) {
        try {
            List<AddCartDTO> obj = cartService.getCartByUserId(userId);
            return ResponseEntity.ok(obj);
        }catch(Exception e) {
            return ResponseEntity.badRequest().body(new ApiResponse(e.getMessage(), ""));
        }
    }

    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> deleteCartById(@PathVariable Long id){
        try {
            return ResponseEntity.ok(cartService.deleteCartById(id));
        }catch(Exception e) {
            return ResponseEntity.badRequest().body(new ApiResponse(e.getMessage(), ""));
        }
    }

    @PutMapping("update/{id}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> updateCart(@PathVariable long id, @RequestBody HashMap<String, Long> Quantity)
    {
        try {
            String keys[] = { "quantity" };
            AddCart cart = cartService.getCartById(id);
            Long quantity = Quantity.get("quantity");
            cart.setQuantity(quantity);
            AddCart updatedCart = cartRepository.save(cart);
            Long userId = updatedCart.getUserId();
            List<AddCartDTO> obj = cartService.getCartByUserId(userId);
            return ResponseEntity.ok(obj);
            //return ResponseEntity.ok("Update quantity successly");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body(new ApiResponse(e.getMessage(), ""));
        }
    }

    @GetMapping("/count/{userId}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> countCartUserId(@PathVariable Long userId) throws Exception {
        Long count = cartService.countCartByUserId(userId);
        String countStr = count.toString();
        return ResponseEntity.ok(count);
    }

    @GetMapping("/getCartByIdCart/{cartId}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> getCartByIdCart(@PathVariable Long cartId) throws Exception {
        AddCart addCart = cartService.getCartById(cartId);
        AddCartDTO addCartDTO = addCartConverter.toDTO(addCart);
        return ResponseEntity.ok(addCartDTO);
    }
}
