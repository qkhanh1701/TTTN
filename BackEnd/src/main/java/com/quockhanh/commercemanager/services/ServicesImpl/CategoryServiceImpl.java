package com.quockhanh.commercemanager.services.ServicesImpl;

import com.quockhanh.commercemanager.converter.CategoryConverter;
import com.quockhanh.commercemanager.model.Category;
import com.quockhanh.commercemanager.model.DTO.CategoryDTO;
import com.quockhanh.commercemanager.repository.CategoryRepository;
import com.quockhanh.commercemanager.services.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private CategoryConverter categoryConverter;

    @Override
    public List<CategoryDTO> getCategory() {
        List<Category> list = categoryRepository.findAllByOrderByIdDesc();
        List<CategoryDTO> listDTO = new ArrayList<CategoryDTO>();
        list.stream().forEach(s -> {
            CategoryDTO dto = categoryConverter.toDTO(s);
            listDTO.add(dto);
        });
        return listDTO;
    }

    @Override
    public CategoryDTO getCategoryByName(String name) {
        Category category = categoryRepository.findCategoryByCategoryname(name);
            CategoryDTO dto = categoryConverter.toDTO(category);
        return dto;
    }

    @Override
    public CategoryDTO getCategoryById(Long id) {
        Category category = categoryRepository.findCategoryById(id);
        CategoryDTO dto = categoryConverter.toDTO(category);
        return dto;
    }

    @Override
    public Category findById(Long id) {
        return categoryRepository.findById(id).orElse(null);
    }

    @Override
    public Category save(Category category) {
        return categoryRepository.save(category);
    }

    @Override
    public void delete(Long id) {
        categoryRepository.deleteById(id);
    }
}
