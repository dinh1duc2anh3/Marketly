package com.darian.ecommerce.service.impl;

import com.darian.ecommerce.dto.CategoryDTO;
import com.darian.ecommerce.entity.Category;
import com.darian.ecommerce.repository.CategoryRepository;
import com.darian.ecommerce.service.CategoryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class CategoryServiceImpl implements CategoryService {
    // Logger for logging actions and errors
    private static final Logger logger = LoggerFactory.getLogger(ProductServiceImpl.class);

    // Dependencies injected via constructor
    private final CategoryRepository categoryRepository;


    // Constructor for dependency injection
    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    // Get all product categories
    @Override
    public List<CategoryDTO> findAllCategories() {
        logger.info("Fetching all categories");
        List<Category>  allCategory = categoryRepository.findAll();

        return allCategory.stream().map(this::mapToCategoryDTO).toList();
    }

    // Save a new category
    @Override
    public CategoryDTO saveCategory(CategoryDTO category) {
        //- check again saveCategory , check if the input is category or categoryDTO
        logger.info("Saving category: {}", category.getName());
        Category savedCategory = categoryRepository.save(mapToCategoryEntity(category));
        logger.info("Category saved: {}", savedCategory.getId());
        return category;
    }

    // Private method to map Category entity to CategoryDTO
    private CategoryDTO mapToCategoryDTO(Category category) {
        return CategoryDTO.builder()
                .id(category.getId())
                .name(category.getName())
                .description(category.getDescription())
                .build();
    }

    // Private method to map CategoryDTO to Category entity
    private Category mapToCategoryEntity(CategoryDTO dto) {
        return Category.builder()
                .id(dto.getId())
                .name(dto.getName())
                .description(dto.getDescription())
                .build();
    }
}
