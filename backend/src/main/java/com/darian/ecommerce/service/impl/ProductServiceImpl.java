package com.darian.ecommerce.service.impl;

import com.darian.ecommerce.businesslogic.productdetail.ProductDetailFetcherFactory;
import com.darian.ecommerce.businesslogic.productlist.ProductListFetcherFactory;
import com.darian.ecommerce.businesslogic.productsearch.ProductSearchFetcherFactory;
import com.darian.ecommerce.dto.CategoryDTO;
import com.darian.ecommerce.dto.ManagerProductDTO;
import com.darian.ecommerce.dto.ProductDTO;
import com.darian.ecommerce.dto.ProductReviewDTO;
import com.darian.ecommerce.entity.Product;
import com.darian.ecommerce.entity.Category;
import com.darian.ecommerce.entity.ProductReview;
import com.darian.ecommerce.repository.CategoryRepository;
import com.darian.ecommerce.repository.ProductRepository;
import com.darian.ecommerce.repository.ProductReviewRepository;
import com.darian.ecommerce.service.AuditLogService;
import com.darian.ecommerce.service.ProductService;
import org.slf4j.*;

import java.util.List;

public class ProductServiceImpl implements ProductService {
    // Logger for logging actions and errors
    private static final Logger logger = LoggerFactory.getLogger(ProductServiceImpl.class);

    // Dependencies injected via constructor
    private final ProductRepository productRepository;
    private final ProductListFetcherFactory productListFetcherFactory;
    private final ProductDetailFetcherFactory productDetailFetcherFactory;
    private final ProductSearchFetcherFactory productSearchFetcherFactory;
    private final AuditLogService auditLogService;
    private final CategoryRepository categoryRepository;
    private final ProductReviewRepository productReviewRepository;

    // Constructor for dependency injection
    public ProductServiceImpl(ProductRepository productRepository,
                              ProductListFetcherFactory productListFetcherFactory,
                              ProductDetailFetcherFactory productDetailFetcherFactory,
                              ProductSearchFetcherFactory productSearchFetcherFactory,
                              AuditLogService auditLogService,
                              CategoryRepository categoryRepository,
                              ProductReviewRepository productReviewRepository) {
        this.productRepository = productRepository;
        this.productListFetcherFactory = productListFetcherFactory;
        this.productDetailFetcherFactory = productDetailFetcherFactory;
        this.productSearchFetcherFactory = productSearchFetcherFactory;
        this.auditLogService = auditLogService;
        this.categoryRepository = categoryRepository;
        this.productReviewRepository = productReviewRepository;
    }

    // Fetch product list using Factory Pattern based on role
    @Override
    public <T extends ProductDTO> List<T> getProductList(String role) {
        logger.info("Fetching product list for role: {}", role);
        return productListFetcherFactory.getFetcher(role).fetchProductList();
    }

    // Fetch product details using Factory Pattern based on role
    @Override
    public <T extends ProductDTO> T getProductDetails(String userId, String productId, String role) {
        logger.info("Fetching product details for productId: {}, role: {}, userId: {}", productId, role, userId);
        T productDTO = productDetailFetcherFactory.getFetcher(role).fetchProductDetails(productId);
        auditLogService.logViewProduct(userId, "VIEW_PRODUCT_DETAILS", "Viewed product: " + productId);
        return productDTO;
    }

    // Search products using Factory Pattern based on role
    @Override
    public <T extends ProductDTO> List<T> searchProducts(String userId, String keyword, String role) {
        logger.info("Searching products with keyword: {}, role: {}, userId: {}", keyword, role, userId);
        List<T> results = productSearchFetcherFactory.getFetcher(role).searchProducts(keyword);
        auditLogService.logSearchAction(userId, "SEARCH_PRODUCTS", "Searched with keyword: " + keyword);
        return results;
    }

    // Advanced search with filters, directly querying repository
    @Override
    public List<ProductDTO> findByFilters(String keyword, float minPrice, float maxPrice, String category) {
        logger.info("Finding products with filters - keyword: {}, minPrice: {}, maxPrice: {}, category: {}",
                keyword, minPrice, maxPrice, category);
        return productRepository.findByFilters(keyword, minPrice, maxPrice, category);
    }

    // Suggest related products based on product ID
    @Override
    public List<ProductDTO> suggestRelatedProducts(String productId) {
        logger.info("Suggesting related products for productId: {}", productId);
        List<Product> relatedProducts = productRepository.findRelatedProducts(productId);
        return relatedProducts.stream().map(this::mapToBaseDTO).toList(); //?
    }

    // Add a new product with validation (Manager only)
    @Override
    public ManagerProductDTO addProduct(ProductDTO productDTO) {
        logger.info("Adding new product: {}", productDTO.getName());
        if (!validateProductDetails(productDTO)){
            logger.warn("Invalid product details: {}", productDTO);
            throw new IllegalArgumentException("Invalid product details");
        }
        Product product = new Product();
        product.setProductId(); //stt ? hay laf "P" + System.currentTimeMillis()
        product.setName(productDTO.getName());
        product.setPrice(productDTO.getPrice());
        product.setDescription(productDTO.getDescription());
        Product savedProduct = productRepository.save(product);
        logger.info("Product added successfully: {}", savedProduct.getProductId());
        auditLogService.logAddAction("MANAGER", "ADD_PRODUCT", "Added product: " + savedProduct.getProductId());

        //return managerDTO ? right or not ?
        ManagerProductDTO managerDTO = new ManagerProductDTO();
        managerDTO.setProductId(savedProduct.getProductId());
        managerDTO.setName(savedProduct.getName());
        managerDTO.setPrice(savedProduct.getPrice());
        managerDTO.setDescription(savedProduct.getDescription());
        managerDTO.setStockQuantity(0); // Default stock
        return managerDTO;

        return null;
    }

    // Update an existing product (Manager only)
    @Override
    public ManagerProductDTO updateProduct(String productId, ProductDTO productDTO) {
        logger.info("Updating product: {}", productId);
        Optional<Product> optionalProduct = productRepository.findById(productId);
        if (optionalProduct.isEmpty()){
            logger.warn("Invalid product details for update: {}", productDTO);
            throw new IllegalArgumentException("Invalid product details");
        }
        if (!validateProductDetails(productDTO)) {
            logger.warn("Invalid product details for update: {}", productDTO);
            throw new IllegalArgumentException("Invalid product details");
        }

        //input for updating a product is only these ??? nothing else? i think not enough info here
        Product product = optionalProduct.get();
        product.setName(productDTO.getName());
        product.setPrice(productDTO.getPrice());
        product.setDescription(productDTO.getDescription());
        Product updatedProduct = productRepository.save(product);
        logger.info("Product updated successfully: {}", updatedProduct.getProductId());
        auditLogService.logUpdateAction("MANAGER", "UPDATE_PRODUCT", "Updated product: " + productId);


        //return managerDTO ??? is this enough info here ?
        ManagerProductDTO managerDTO = new ManagerProductDTO();
        managerDTO.setProductId(updatedProduct.getProductId());
        managerDTO.setName(updatedProduct.getName());
        managerDTO.setPrice(updatedProduct.getPrice());
        managerDTO.setDescription(updatedProduct.getDescription());
        managerDTO.setStockQuantity(updatedProduct.getStockQuantity());
        return managerDTO;
    }

    // Delete a product by ID (Manager only)
    @Override
    public void deleteProduct(String productId) {
        logger.info("Attempting to delete product: {}", productId);
        validateDeletion(productId);
        productRepository.deleteById(productId);
        //need more check function here
        logger.info("Product deleted successfully: {}", productId);
        auditLogService.logDeleteAction("MANAGER", "DELETE_PRODUCT", "Deleted product: " + productId);

    }

    // Validate product details before adding/updating
    @Override
    public Boolean validateProductDetails(ProductDTO productDTO) {
        //check if enough condition here ?
        return productDTO != null &&
                productDTO.getName() != null && !productDTO.getName().isBlank() &&
                productDTO.getPrice() > 0;
    }


    @Override
    public int checkProductQuantity(String productId) {
        logger.info("Checking quantity for product: {}", productId);
        return productRepository.findById(productId)
                .map(Product::getStockQuantity)
                .orElse(0);
    }

    // Validate conditions before deleting a product
    @Override
    public void validateDeletion(String productId) {
        logger.info("Validating deletion for product: {}", productId);
        if (!checkDeletionConstraints(productId)) {
            logger.warn("Deletion constraints violated for product: {}", productId);
            throw new IllegalStateException("Cannot delete due to constraints");
        }
        if (checkOrdersAffected(productId)){
            logger.warn("Active orders affected by product deletion: {}", productId);
            throw new IllegalStateException("Cannot delete due to affected orders");
        }

    }

    // Check if the user has exceeded the deletion limit
    @Override
    public Boolean checkDeleteLimit(String userId) {
        logger.info("Checking delete limit for user: {}", userId);
        int deleteCount = auditLogService.countDeletesByUserId(userId);
        return deleteCount < 30;  //check again if the delete limit is 30 or else
    }

    // Check constraints that might prevent deletion
    @Override
    public Boolean checkDeletionConstraints(String productId) {
        logger.info("Checking deletion constraints for product: {}", productId);
        int stock = checkProductQuantity(productId);
        return stock > 0; //Can only delete if stock is above 0
        //check if there is any deletion constraints else ?
    }

    // Check if deleting a product affects any orders
    @Override
    public Boolean checkOrdersAffected(String productId) {
        logger.info("Checking if orders are affected for product: {}", productId);
        //logic to check if any order affected ?
        return false; //assume that No orders affected for now

    }

    // Get all product categories
    @Override
    public List<CategoryDTO> findAllCategories() {
        logger.info("Fetching all categories");
        return categoryRepository.findAll();
    }

    // Save a new category
    @Override
    public CategoryDTO saveCategory(CategoryDTO category) {
        //- check again saveCategory , check if the input is category or categoryDTO
        logger.info("Saving category: {}", category.getName());
        Category savedCategory = categoryRepository.save(category);
        logger.info("Category saved: {}", savedCategory.getId());
        return savedCategory;
    }

    // Add a review for a product
    @Override
    public ProductReviewDTO addReview(String productId, ProductReviewDTO reviewDTO) {
        logger.info("Adding review for product: {}", productId);
        if (productRepository.findById(productId) == null) {
            logger.warn("Product not found for review: {}", productId);
            throw new IllegalArgumentException("Product not found");
        }
        ProductReview review = new ProductReview();
        review.setProductId(productId);
        review.setRating(reviewDTO.getRating());
        review.setComment(reviewDTO.getComment());
        ProductReview savedReview = productReviewRepository.save(review);
        logger.info("Review added for product: {}", productId);

        //is it necessary to return savedDTO or just simply return saveReview?
        ProductReviewDTO savedDTO = new ProductReviewDTO();
        savedDTO.setRating(savedReview.getRating());
        savedDTO.setComment(savedReview.getComment());
        return savedDTO;
    }

    // Get all reviews for a product
    @Override
    public List<ProductReviewDTO> getReviews(String productId) {
        logger.info("Fetching reviews for product: {}", productId);
        List<ProductReview> reviews = productReviewRepository.findByProductId(productId);
        //what does this do ?
        return reviews.stream().map(review -> {
            ProductReviewDTO dto = new ProductReviewDTO();
            dto.setRating(review.getRating());
            dto.setComment(review.getComment());
            return dto;
        }).toList();
    }

    // Private method to map Product entity to ProductDTO
    private ProductDTO mapToBaseDTO(Product product) {
        ProductDTO dto = new ProductDTO();
        dto.setProductId(product.getProductId());
        dto.setName(product.getName());
        dto.setPrice(product.getPrice());
        dto.setDescription(product.getDescription());
        return dto;
    }
}
