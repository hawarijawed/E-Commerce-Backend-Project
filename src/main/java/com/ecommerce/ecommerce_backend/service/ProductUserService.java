package com.ecommerce.ecommerce_backend.service;

import com.ecommerce.ecommerce_backend.dto.Reviews.CreateReviewsDTO;
import com.ecommerce.ecommerce_backend.models.Products;
import com.ecommerce.ecommerce_backend.models.Reviews;
import com.ecommerce.ecommerce_backend.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductUserService {

    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private ReviewService reviewService;
    //View All prodcut
    public List<Products> viewAllProduct(){
        return productRepository.findAll();
    }

    //View a single product
    public Products viewProductById(Long id){
        return productRepository.findById(id).orElse(null);
    }

    //Buy Product
    // 🟢 Buy product (basic version - will link to Order table later)
    public boolean buyProduct(Long productId, int quantity) {
        Products product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found with id: " + productId));

        if (product.getStockQuantity() < quantity) {
            throw new RuntimeException("Insufficient stock for product: " + product.getName());
        }

        product.setStockQuantity(product.getStockQuantity() - quantity);
        productRepository.save(product);
        return true;
    }
    //Add Review over a product
    public Reviews addReview(CreateReviewsDTO createReviewsDTO){
        return reviewService.addReview(createReviewsDTO);
    }

    //view reviews
    public List<Reviews> getReviews(Long productId){
        return reviewService.viewReviewsByProductId(productId);
    }
    //search product by category
    public List<Products> searchByCategory(String category){
        return productRepository.findByCategory(category);
    }
}
