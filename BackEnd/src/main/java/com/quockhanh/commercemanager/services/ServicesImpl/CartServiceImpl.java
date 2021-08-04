package com.quockhanh.commercemanager.services.ServicesImpl;

import com.quockhanh.commercemanager.converter.AddCartConverter;
import com.quockhanh.commercemanager.model.AddCart;
import com.quockhanh.commercemanager.model.DTO.AddCartDTO;
import com.quockhanh.commercemanager.model.Product;
import com.quockhanh.commercemanager.repository.CartRepository;
import com.quockhanh.commercemanager.services.CartService;
import com.quockhanh.commercemanager.services.ProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CartServiceImpl implements CartService {

    @Autowired
    CartRepository cartRepository;

    @Autowired
    ProductService productService;

    @Autowired
    AddCartConverter addCartConverter;

    private static final Logger logger = LoggerFactory.getLogger(CartServiceImpl.class);

    @Override
    public List<AddCartDTO> addCartbyUserIdAndProductId(Long productId, Long userId, Long qty, double price) throws Exception {
        try {
            Product product = productService.getProductById(productId);
            if(cartRepository.findAddCartByProductAndUserId(product, userId).isPresent()){
                return this.getCartByUserId(userId);
            }
            AddCart obj = new AddCart();
            obj.setQuantity(qty);
            obj.setUserId(userId);
            Product pro = productService.getProductById(productId);
            obj.setProduct(pro);
            //TODO price has to check with qty
            obj.setPrice(price);
            cartRepository.save(obj);
            Long update = obj.getProduct().getQuantity() - obj.getQuantity();
            productService.updateProduct(obj.getProduct().getId(),update);
            return this.getCartByUserId(userId);
        }catch(Exception e) {
            e.printStackTrace();
            logger.error(""+e.getMessage());
            throw new Exception(e.getMessage());
        }
    }

    @Override
    public List<AddCartDTO> getCartByUserId(long userId) {
        List<AddCart> listAddCart = cartRepository.findAddCartByUserId(userId);
        List<AddCartDTO> listDTO = new ArrayList<AddCartDTO>();
        listAddCart.stream().forEach(s -> {
            AddCartDTO dto = addCartConverter.toDTO(s);
            listDTO.add(dto);
        });
        return listDTO;
    }

    @Override
    public void removeCartByUserId(long userId) {
        cartRepository.deleteAddCartsByUserId(userId);
    }

    @Override
    public List<AddCartDTO> deleteCartById(Long id) throws Exception {
        AddCart addCart = cartRepository.findAddCartById(id);
        cartRepository.deleteAddCartById(id);
        System.out.println(addCart.getUserId());
        List<AddCartDTO> list = getCartByUserId(addCart.getUserId());
        return list;
    }

    @Override
    public AddCart getCartById(Long id) throws Exception {
        AddCart addCart = cartRepository.findAddCartById(id);
        return addCart;
    }

    @Override
    public Long countCartByUserId(Long userId) throws Exception{
        return cartRepository.countByUserId(userId);
    }
}
