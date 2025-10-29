package com.ecommerce.ecommerce_backend.service;

import com.ecommerce.ecommerce_backend.models.Products;
import com.ecommerce.ecommerce_backend.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductUserService {

    @Autowired
    private ProductRepository productRepository;

    //View All prodcut
    public List<Products> viewAllProduct(){
        return productRepository.findAll();
    }

    //View a single product
    public Products viewProductById(Long id){
        return productRepository.findById(id).orElse(null);
    }
}
