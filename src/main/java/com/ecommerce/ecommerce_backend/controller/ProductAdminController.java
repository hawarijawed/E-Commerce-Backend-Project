package com.ecommerce.ecommerce_backend.controller;

import com.ecommerce.ecommerce_backend.dto.Product.CreateProductPojo;
import com.ecommerce.ecommerce_backend.models.Products;
import com.ecommerce.ecommerce_backend.service.ProductAdminService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/product/admin")
public class ProductAdminController {
    private final ProductAdminService productAdminService;

    public ProductAdminController(ProductAdminService productAdminService){
        this.productAdminService = productAdminService;
    }

    //Add product
    @PostMapping("/add")
    private ResponseEntity<String> addProduct(@RequestBody @Valid CreateProductPojo productPojo){
        boolean flag = productAdminService.addProduct(productPojo);
        if(flag){
            return new ResponseEntity<>("Product added successfully", HttpStatus.CREATED);
        }

        return new ResponseEntity<>("Error occurred", HttpStatus.BAD_REQUEST);
    }

    //View all products
    @GetMapping("/list")
    private List<Products> viewAllProduct(){
        return productAdminService.listProducts();
    }

    //View product by Id
    @GetMapping("/get/{id}")
    private Products getById(@PathVariable Long id){
        return productAdminService.getProductById(id);
    }

}
