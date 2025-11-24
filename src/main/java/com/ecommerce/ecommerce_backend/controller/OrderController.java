package com.ecommerce.ecommerce_backend.controller;

import com.ecommerce.ecommerce_backend.dto.PlaceOrder.BuyNowDTO;
import com.ecommerce.ecommerce_backend.dto.PlaceOrder.CheckoutOrderDTO;
import com.ecommerce.ecommerce_backend.dto.PlaceOrder.OrderResponseDTO;
import com.ecommerce.ecommerce_backend.models.Orders;
import com.ecommerce.ecommerce_backend.service.OrderServices;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/order")
public class OrderController {

    private final OrderServices orderServices;

    public OrderController(OrderServices orderServices){
        this.orderServices = orderServices;
    }

    @GetMapping("/list-all/{userId}")
    public ResponseEntity<?> getAllOrders(@PathVariable Long userId){
        List<OrderResponseDTO> orders = orderServices.allOrders(userId);
        if(orders.isEmpty()){
            return new ResponseEntity<>("Order List is Empty", HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(orders, HttpStatus.FOUND);
    }
    @PostMapping("/buy-now/{userId}")
    public ResponseEntity<String> buyNow(@PathVariable Long userId,
                                         @RequestBody BuyNowDTO dto){
        String response = orderServices.buyNowOrder(userId, dto);

        return ResponseEntity.ok(response);
    }
    @DeleteMapping("/delete")
    public String deleteAll(){
        return orderServices.deleteAllOrders();
    }

    @PostMapping("/checkout/{userId}")
    public ResponseEntity<Orders> checkout(@PathVariable Long userId, @RequestBody @Valid CheckoutOrderDTO dto){
        Orders order = orderServices.checkoutOrder(userId, dto);

        return ResponseEntity.ok(order);
    }

    @PatchMapping("/cancel/{orderId}/user/{userId}")
    public ResponseEntity<String> cancelOrder(@PathVariable Long orderId, @PathVariable Long userId){
        String res = orderServices.cancelOrder(orderId, userId);

        return ResponseEntity.ok(res);
    }
}
