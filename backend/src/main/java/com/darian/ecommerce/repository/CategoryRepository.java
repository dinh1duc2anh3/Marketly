package com.darian.ecommerce.repository;

import com.darian.ecommerce.dto.CategoryDTO;
import com.darian.ecommerce.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Integer> {
    // Save a category (inherited from JpaRepository)
    Category save(Category category);

    // Find all categories (inherited from JpaRepository)
    List<Category> findAll();


}
