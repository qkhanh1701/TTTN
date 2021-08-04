package com.quockhanh.commercemanager.services;

import com.quockhanh.commercemanager.model.Category;
import com.quockhanh.commercemanager.model.DTO.CategoryDTO;

import java.util.List;

public interface CategoryService {
    public List<CategoryDTO> getCategory();

    public CategoryDTO getCategoryByName(String name);

    public CategoryDTO getCategoryById(Long id);

    public Category findById(Long id);

    public Category save(Category category);

    public void delete(Long id);
}
