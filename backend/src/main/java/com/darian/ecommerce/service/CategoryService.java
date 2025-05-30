package com.darian.ecommerce.service;


import com.darian.ecommerce.dto.CategoryDTO;
import org.springframework.stereotype.Service;

import java.util.List;

public interface CategoryService {
    // Get all product categories
    List<CategoryDTO> findAllCategories();

    // Save a new category
    CategoryDTO saveCategory(CategoryDTO category);
}
