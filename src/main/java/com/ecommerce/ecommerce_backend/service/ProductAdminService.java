package com.ecommerce.ecommerce_backend.service;

import com.ecommerce.ecommerce_backend.dto.Product.CreateProductPojo;
import com.ecommerce.ecommerce_backend.dto.Product.UpdateProductPojo;
import com.ecommerce.ecommerce_backend.models.Products;
import com.ecommerce.ecommerce_backend.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ProductAdminService {
    @Autowired
    private ProductRepository productRepository;

    //List out all the products
    public List<Products> listProducts(){
        return productRepository.findAll();
    }

    //Get specific product
    public Products getProductById(Long id){
        return productRepository.findById(id).orElse(null);
    }

    //Delete product by Id
    public boolean deleteProductById(Long id){
        if(productRepository.existsById(id)) {
            productRepository.deleteById(id);
            return true;
        }
        return false;
    }

    //List product with same category
    public List<Products> listProductByCategory(String category){
        return productRepository.findByCategory(category);
    }

    //Add product
    public boolean addProduct(CreateProductPojo productPojo){
        Products products = new Products();
        products.setName(productPojo.getName());
        products.setDescription(productPojo.getDescription());
        products.setPrice(productPojo.getPrice());
        products.setBrand(productPojo.getBrand());
        products.setCategory(productPojo.getCategory());
        products.setStockQuantity(productPojo.getStockQuantity());
        productRepository.save(products);
        return true;
    }
    public boolean updateProduct(UpdateProductPojo productPojo){
        //
        Optional<Products> existing = productRepository.findById(productPojo.getId());
        if (existing.isEmpty()) return false;

        Products products = existing.get();
        if(productPojo.getName() != null){
            products.setName(productPojo.getName());
        }
        if(productPojo.getDescription() != null){
            products.setDescription(products.getDescription());
        }
        if(productPojo.getPrice() != 0) {
            products.setPrice(productPojo.getPrice());
        }
        if (productPojo.getCategory() != null && !productPojo.getCategory().isEmpty()) {
            if (products.getCategory() == null) {
                products.setCategory(new ArrayList<>());
            }

            // Avoid duplicates if needed
            for (String cat : productPojo.getCategory()) {
                if (!products.getCategory().contains(cat)) {
                    products.getCategory().add(cat);
                }
            }
        }
        productRepository.save(products);
        return true;
    }



}
