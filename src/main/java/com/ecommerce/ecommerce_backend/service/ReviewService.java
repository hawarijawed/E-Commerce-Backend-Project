package com.ecommerce.ecommerce_backend.service;

import com.ecommerce.ecommerce_backend.dto.Reviews.CreateReviewsDTO;
import com.ecommerce.ecommerce_backend.models.Products;
import com.ecommerce.ecommerce_backend.models.Reviews;
import com.ecommerce.ecommerce_backend.repository.ProductRepository;
import com.ecommerce.ecommerce_backend.repository.ReviewRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class ReviewService {
    private final ReviewRepository reviewRepository;
    private final ProductRepository productRepository;
    public ReviewService(ReviewRepository reviewRepository,
                         ProductRepository productRepository){
        this.reviewRepository = reviewRepository;
        this.productRepository = productRepository;
    }

    //add review
    public Reviews addReview(CreateReviewsDTO createReviewsDTO){
        Products products = productRepository.findById(createReviewsDTO.getProductId()).
                orElseThrow(()-> new RuntimeException("Product not found"));

        Reviews reviews = new Reviews();
        reviews.setProducts(products);
        reviews.setUsername(createReviewsDTO.getUsername());
        reviews.setComment(createReviewsDTO.getComment());
        reviews.setRating(createReviewsDTO.getRating());
        log.info("Review are set to be saved+++++++++++++++++++++++++++++++++++++");
        reviewRepository.save(reviews);
        log.info("Review are saved___________________________________________________");

        return reviews;
    }

    //View Reviews
    public List<Reviews> viewReviewsByProductId(Long productId){
        //return reviewRepository.findByProduct_Id(productId);
        return reviewRepository.findAll();
    }
}
