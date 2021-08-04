package com.quockhanh.commercemanager.services.ServicesImpl;

import com.quockhanh.commercemanager.converter.ProductConverter;
import com.quockhanh.commercemanager.model.DTO.ProductDTO;
import com.quockhanh.commercemanager.model.ImageDetail;
import com.quockhanh.commercemanager.model.Product;
import com.quockhanh.commercemanager.repository.ImageDetailRepository;
import com.quockhanh.commercemanager.repository.ProductRepository;
import com.quockhanh.commercemanager.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ImageDetailRepository imageDetailRepository;

    @Autowired
    private ProductConverter productConverter;

    @Override
    public List<ProductDTO> getAllProducts() {
        List<Product> list =  productRepository.findAllByOrderByIdDesc();
        List<ProductDTO> listDTO = new ArrayList<>();
        list.stream().forEach(
                (s) -> {
                    ProductDTO dto = productConverter.toDTO(s);
                    listDTO.add(dto);
                }
        );
        return listDTO;
    }

    @Override
    public List<ProductDTO> getProductPromotion() {
        List<Product> list = productRepository.findTop8ByPromotionGreaterThanOrderByIdDesc(0);

        List<ProductDTO> listDTO = new ArrayList<ProductDTO>();

        list.stream().forEach(s -> {
            ProductDTO dto = productConverter.toDTO(s);
            listDTO.add(dto);
        });

        return listDTO;
    }

    @Override
    public Product getProductById(Long id) {
        Product product = productRepository.findProductById(id);
        return product;
    }

    @Override
    public ProductDTO getProductDTOById(Long id) {
        Product product = productRepository.findProductById(id);
        ProductDTO dto = productConverter.toDTO(product);
        return dto;
    }

    @Override
    public ProductDTO getProductByName(String name) {
        Product product = productRepository.findProductByName(name);
        ProductDTO dto = productConverter.toDTO(product);
        return dto;
    }

    @Override
    public List<ImageDetail> getImageId(Long imageId) {
        List<ImageDetail> list = imageDetailRepository.findImageDetailByImageid(imageId);
        return list;
    }

    @Override
    public Product save(Product product) {
        return productRepository.save(product);
    }

    @Override
    public void delete(Product product) {
        productRepository.delete(product);
    }

    @Override
    public void updateProduct(Long id, Long quantity) {
        Product product = getProductById(id);
        product.setQuantity(quantity);
        productRepository.save(product);
    }
}
