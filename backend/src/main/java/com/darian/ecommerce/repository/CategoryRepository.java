package com.darian.ecommerce.repository;

import com.darian.ecommerce.dto.CategoryDTO;
import com.darian.ecommerce.entity.Category;

import java.util.List;

public interface CategoryRepository {
    List<CategoryDTO> findAll();

    Category save(CategoryDTO category);
}
