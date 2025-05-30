package com.darian.ecommerce.service.impl;

import com.darian.ecommerce.businesslogic.productdetail.ProductDetailFetcher;
import com.darian.ecommerce.businesslogic.productdetail.ProductDetailFetcherFactory;
import com.darian.ecommerce.businesslogic.productlist.ProductListFetcher;
import com.darian.ecommerce.businesslogic.productlist.ProductListFetcherFactory;
import com.darian.ecommerce.businesslogic.productsearch.ProductSearchFetcher;
import com.darian.ecommerce.businesslogic.productsearch.ProductSearchFetcherFactory;
import com.darian.ecommerce.dto.ManagerProductDTO;
import com.darian.ecommerce.dto.ProductDTO;
import com.darian.ecommerce.entity.Product;
import com.darian.ecommerce.entity.ProductImage;
import com.darian.ecommerce.enums.UserRole;
import com.darian.ecommerce.event.AddProductEvent;
import com.darian.ecommerce.event.CheckDeleteLimitEvent;
import com.darian.ecommerce.event.SearchProductEvent;
import com.darian.ecommerce.event.ViewProductEvent;
import com.darian.ecommerce.repository.ProductRepository;
import com.darian.ecommerce.service.AuditLogService;
import com.darian.ecommerce.service.ProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;



import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {


    // Logger for logging actions and errors
    private static final Logger logger = LoggerFactory.getLogger(ProductServiceImpl.class);

    // Dependencies injected via constructor
    private final ApplicationEventPublisher eventPublisher;

    private final ProductRepository productRepository;
    private final ProductListFetcherFactory productListFetcherFactory;
    private final ProductDetailFetcherFactory productDetailFetcherFactory;
    private final ProductSearchFetcherFactory productSearchFetcherFactory;
    private final AuditLogService auditLogService;

    // Constructor for dependency injection
    public ProductServiceImpl(ProductRepository productRepository,
                              ProductListFetcherFactory productListFetcherFactory,
                              ProductDetailFetcherFactory productDetailFetcherFactory,
                              ProductSearchFetcherFactory productSearchFetcherFactory,
                              AuditLogService auditLogService,
                              ApplicationEventPublisher eventPublisher
    ) {
        this.productRepository = productRepository;
        this.productListFetcherFactory = productListFetcherFactory;
        this.productDetailFetcherFactory = productDetailFetcherFactory;
        this.productSearchFetcherFactory = productSearchFetcherFactory;
        this.auditLogService = auditLogService;
        this.eventPublisher = eventPublisher;
    }

    // Fetch product list using Factory Pattern based on role
    @Override
    // Safe cast: fetcher is guaranteed to return correct subtype based on role
    @SuppressWarnings("unchecked")
    public <T extends ProductDTO> List<T> getProductList(UserRole role) {
        logger.info("Fetching product list for role: {}", role);

        //get fetcher
        ProductListFetcher<T> fetcher = (ProductListFetcher<T>) productListFetcherFactory.getFetcher(role);

        //get product list
        return fetcher.fetchProductList();
    }

    // Fetch product details using Factory Pattern based on rol
    @Override
    // Safe cast: fetcher is guaranteed to return correct subtype based on role
    @SuppressWarnings("unchecked")
    public <T extends ProductDTO> T getProductDetails(Integer userId, Long productId, UserRole role) {
        logger.info("Fetching product details for productId: {}, role: {}, userId: {}", productId, role, userId);

        //get fetcher
        ProductDetailFetcher<T> fetcher = (ProductDetailFetcher<T>) productDetailFetcherFactory.getFetcher(role);

        //fetch product details
        T productDTO = fetcher.fetchProductDetails(productId);

        eventPublisher.publishEvent(new ViewProductEvent(this, productId, userId, role));
        return productDTO;
    }

    @Override
    public Optional<Product> getProductById(Long productId) {
        return productRepository.findById(productId);
    }

    // Search products using Factory Pattern based on role
    @Override
    // Safe cast: fetcher is guaranteed to return correct subtype based on role
    @SuppressWarnings("unchecked")
    public <T extends ProductDTO> List<T> searchProducts(Integer userId, String keyword, UserRole role) {
        logger.info("Searching products with keyword: {}, role: {}, userId: {}", keyword, role, userId);

        //get fetcher
        ProductSearchFetcher<T> fetcher = (ProductSearchFetcher<T>) productSearchFetcherFactory.getFetcher(role);

        //search product
        List<T> results = fetcher.searchProducts(keyword);
        eventPublisher.publishEvent(new SearchProductEvent(this, userId, keyword, role));
        return results;
    }

    // Advanced search with filters, directly querying repository
    @Override
    public List<ProductDTO> findByFilters(String keyword, Float minPrice, Float maxPrice, String category) {
        logger.info("Finding products with filters - keyword: {}, minPrice: {}, maxPrice: {}, category: {}",
                keyword, minPrice, maxPrice, category);
        return productRepository.findByFilters(keyword, minPrice, maxPrice, category);
    }



    // Add a new product with validation (Manager only)
    @Override
    public ManagerProductDTO addProduct(Integer userId, ProductDTO productDTO) {
        logger.info("Adding new product: {}", productDTO.getName());
        if (!validateProductDetails(productDTO)){
            logger.warn("Invalid product details: {}", productDTO);
            throw new IllegalArgumentException("Invalid product details");
        }
        Product product = new Product();
//        product.setProductId(); //stt ? hay laf "P" + System.currentTimeMillis()
        product.setName(productDTO.getName());
        product.setPrice(productDTO.getPrice());
        product.setDescription(productDTO.getDescription());
        Product savedProduct = productRepository.save(product);
        logger.info("Product added successfully: {}", savedProduct.getProductId());
        eventPublisher.publishEvent(new AddProductEvent(this, userId, productDTO.getProductId(),UserRole.MANAGER));


        //TODO: mapto function
        //return managerDTO ? right or not ?
        ManagerProductDTO managerDTO = new ManagerProductDTO();
        managerDTO.setProductId(savedProduct.getProductId());
        managerDTO.setName(savedProduct.getName());
        managerDTO.setPrice(savedProduct.getPrice());
        managerDTO.setDescription(savedProduct.getDescription());
        managerDTO.setStockQuantity(0); // Default stock
        return managerDTO;
    }

    // Update an existing product (Manager only)
    @Override
    public ManagerProductDTO updateProduct(Integer userId, Long productId, ProductDTO productDTO) {
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
        eventPublisher.publishEvent(new ViewProductEvent(this, productId, userId, UserRole.MANAGER));




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
    public void deleteProduct(Long productId, Integer userId) {
        logger.info("Attempting to delete product: {}", productId);
        if (!validateDeletion(productId,userId)) return;
        //update catalog??? need more check
        //update database
        productRepository.deleteById(productId);
        logger.info("Product {} deleted successfully by {}", productId,userId);
        eventPublisher.publishEvent(new ViewProductEvent(this, productId, userId, UserRole.MANAGER));

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
    public Integer checkProductQuantity(Long productId) {
        logger.info("Checking quantity for product: {}", productId);

        Optional<Product> product = getProductById(productId) ;
        return (product.isPresent()) ? product.get().getStockQuantity() : 0;
    }

    // Validate general conditions before deleting a product
    @Override
    public Boolean validateDeletion(Long productId, Integer userId) {
        logger.info("Validating deletion for product {} by user {}", productId,userId);

        //check if quantity of available product is > 0
        if (!checkDeletionConstraints(productId)) {
            logger.warn("Deletion constraints violated for product: {}", productId);
            throw new IllegalStateException("Cannot delete due to constraints");
        }

        if (checkOrdersAffected(productId)){
            logger.warn("Active orders affected by product deletion: {}", productId);
            throw new IllegalStateException("Cannot delete due to affected orders");
        }

        if (checkDeleteLimit(userId)){
            logger.warn("Active orders affected by product deletion: {}", productId);
            throw new IllegalStateException("Cannot delete due to affected orders");
        }
        return true;

    }

    // Check if the user has exceeded the deletion limit
    @Override
    public Boolean checkDeleteLimit(Integer userId) {
        //for log
        eventPublisher.publishEvent(new CheckDeleteLimitEvent(this, userId));
        return auditLogService.checkDeleteLimit(userId);
    }

    // Check constraints that might prevent deletion
    @Override
    public Boolean checkDeletionConstraints(Long productId) {
        logger.info("Checking deletion constraints for product: {}", productId);
        Integer stock = checkProductQuantity(productId);
        return stock > 0; //Can only delete if stock is above 0
    }

    // Check if deleting a product affects any orders
    @Override
    public Boolean checkOrdersAffected(Long productId) {
        logger.info("Checking if orders are affected for product: {}", productId);
        //logic to check if any order affected ?

        return false; //assume that No orders affected for now

    }

}
