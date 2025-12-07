package com.ecommerce.ecommerce_backend.controller;

import com.ecommerce.ecommerce_backend.dto.Reviews.CreateReviewsDTO;
import com.ecommerce.ecommerce_backend.models.Products;
import com.ecommerce.ecommerce_backend.models.Reviews;
import com.ecommerce.ecommerce_backend.service.ProductUserService;
import com.ecommerce.ecommerce_backend.service.ReviewService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@Controller
@RequestMapping("/api/product/user")
public class ProductUserController {
    final private ProductUserService productUserService;
    final private ReviewService reviewService;
    @Autowired
    private AuthenticationManager authenticationManager;
    public ProductUserController(ProductUserService productUserService,
                                 ReviewService reviewService){
        this.productUserService = productUserService;
        this.reviewService = reviewService;
    }

    @GetMapping("/list")
    public ResponseEntity<?> viewProducts(){
        List<Products> products = productUserService.viewAllProduct();
        if(products.isEmpty()){
            return new ResponseEntity<>("No products found", HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(products, HttpStatus.FOUND);
    }

    @PostMapping("/buy/{id}")
    public ResponseEntity<?> buyProduct(@PathVariable Long id, @RequestBody int quantity){
        boolean buy = productUserService.buyProduct(id, quantity);
        if(buy){
            return new ResponseEntity<>("Product purchased successfully", HttpStatus.ACCEPTED);
        }

        return new ResponseEntity<>("Product purchase failed due to either product not found or insufficient product quantity",HttpStatus.BAD_REQUEST);
    }

    @PostMapping("/review")//add reviews
    public ResponseEntity<?> addReview(@Valid @RequestBody CreateReviewsDTO createReviewsDTO){
        log.info(String.valueOf(createReviewsDTO.getProductId()));
        log.info(String.valueOf(createReviewsDTO.getUsername()));
        log.info(String.valueOf(createReviewsDTO.getComment()));
        log.info(String.valueOf(createReviewsDTO.getRating()));
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        log.info("Username from add review: {}",username);
        Reviews reviews = reviewService.addReview(createReviewsDTO);
        if(reviews == null){
            return new ResponseEntity<>("Review could not be saved",HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(reviews,HttpStatus.ACCEPTED);
    }

    @GetMapping("/review/{productId}")//View reviews
    public ResponseEntity<?> getReviews(@PathVariable Long productId){
        List<Reviews> reviews = reviewService.viewReviewsByProductId(productId);

        if(reviews.isEmpty()){
            return new ResponseEntity<>("No reviews found", HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(reviews,HttpStatus.FOUND);
    }

    @PostMapping("/delete/{productId}")
    public ResponseEntity<Boolean> deleteReviews(@PathVariable Long productId){
        boolean flag = reviewService.deleteReviewsByProductId(productId);
        return new ResponseEntity<>(flag, HttpStatus.ACCEPTED);
    }
    @GetMapping("/search/{category}")
    public ResponseEntity<?> searchByCategory(@PathVariable String category){
        List<Products> product = productUserService.searchByCategory(category);
        if(product.isEmpty()){
            return new ResponseEntity<>("No product found with such category", HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(product, HttpStatus.FOUND);
    }
}
/*
GET /api/product/user/list -> done

POST /api/product/user/buy/{id} -> done

POST /api/product/user/review -> done

GET /api/product/user/reviews/{productId} -> done

GET /api/product/user/search?category=Electronics
 */