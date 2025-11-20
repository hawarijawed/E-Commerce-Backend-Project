package com.ecommerce.ecommerce_backend.controller;

import com.ecommerce.ecommerce_backend.dto.Cart.AddToCart;
import com.ecommerce.ecommerce_backend.dto.Cart.UpdateCartDTO;
import com.ecommerce.ecommerce_backend.models.Cart;
import com.ecommerce.ecommerce_backend.service.CartService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.awt.geom.RectangularShape;
import java.util.List;

@Controller
@RequestMapping("/api/cart")
public class CartController {

    private final CartService cartService;

    public CartController(CartService cartService){
        this.cartService = cartService;
    }

    //add to cart
    @PostMapping("/add")
    public ResponseEntity<String> addToCart(@RequestBody AddToCart addToCart){
        String result = cartService.addToCart(addToCart);

        return ResponseEntity.ok(result);
    }

    @DeleteMapping("/delete/{userId}")
    public ResponseEntity<String> removeFromCart(@PathVariable Long userId, @RequestBody Long productId){
        String result = cartService.removeProductFromCart(userId, productId);

        return ResponseEntity.ok(result);
    }

    @GetMapping("/get/{userId}")
    public ResponseEntity<?> viewCarts(@PathVariable Long userId){
        List<Cart> carts = cartService.viewCarts(userId);

        if(carts.isEmpty()){
            return new ResponseEntity<>("Cart is empty", HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(carts, HttpStatus.FOUND);
    }

    @PutMapping("/update/{userId}")
    public ResponseEntity<String> updateCart(@PathVariable Long userId, @RequestBody @Valid UpdateCartDTO updateCartDTO){
        String result = cartService.updateCart(userId, updateCartDTO);

        return ResponseEntity.ok(result);
    }

}
