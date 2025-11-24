package com.ecommerce.ecommerce_backend.controller;

import com.ecommerce.ecommerce_backend.dto.PlaceOrder.PaymentSimulationDTO;
import com.ecommerce.ecommerce_backend.service.PaymentService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/payment")
public class PaymentController {
    private final PaymentService paymentService;

    public PaymentController(PaymentService paymentService){
        this.paymentService = paymentService;
    }

    @PostMapping("/simulate")
    public ResponseEntity<String> simulatePayment(@RequestBody @Valid PaymentSimulationDTO paymentSimulationDTO){
        String res = paymentService.processPayment(paymentSimulationDTO);

        return ResponseEntity.ok(res);
    }
}
