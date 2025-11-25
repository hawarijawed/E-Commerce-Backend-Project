package com.ecommerce.ecommerce_backend.service;

import com.ecommerce.ecommerce_backend.dto.PlaceOrder.PaymentSimulationDTO;
import com.ecommerce.ecommerce_backend.models.OrderStatus;
import com.ecommerce.ecommerce_backend.models.Orders;
import com.ecommerce.ecommerce_backend.models.PaymentStatus;
import com.ecommerce.ecommerce_backend.repository.OrdersRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
public class PaymentService {
    private final EmailService emailService;
    private final OrdersRepository ordersRepository;

    public PaymentService(EmailService emailService1,
                          OrdersRepository ordersRepository1){
        this.emailService = emailService1;
        this.ordersRepository = ordersRepository1;
    }

    @Transactional
    public String processPayment(PaymentSimulationDTO dto){
        //Check order
        Orders orders = ordersRepository.findById(dto.getOrderId()).orElseThrow(
                ()->new RuntimeException("No order found")
        );

        if(!orders.getOrderStatusEnum().equals(OrderStatus.PENDING)){
            return "Order is already processed, current status: "+orders.getOrderStatusEnum();
        }

        //Confirm order status based on payment status
        if(dto.getStatus().equalsIgnoreCase("SUCCESS")){
            orders.setPaymentStatus(PaymentStatus.PAID);
            orders.setOrderStatusEnum(OrderStatus.PAID);

            ordersRepository.save(orders);
//            log.info("Order status is processed");
            //Send order confirmation mail to customer
            emailService.sendOrderConfirmation(orders, orders.getOrderItems());

            return "Payment Success! Order confirmation mail has been sent to your account";
        } else if (dto.getStatus().equalsIgnoreCase("FAILED")) {
            orders.setPaymentStatus(PaymentStatus.FAILED);
            orders.setOrderStatusEnum(OrderStatus.CANCELLED);

            ordersRepository.save(orders);

            return "Payment Failed! Order cancelled";
        }

        return "Invalid payment status";
    }
}
