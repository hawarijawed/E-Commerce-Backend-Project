package com.ecommerce.ecommerce_backend.service;

import com.ecommerce.ecommerce_backend.models.Orders;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class EmailService {
    private final JavaMailSender mailSender;

    public EmailService(JavaMailSender mailSender1){
        this.mailSender = mailSender1;
    }

    public void sendOrderConfirmation(Orders order){
        SimpleMailMessage msg = new SimpleMailMessage();
        msg.setFrom("hawarijawed143@gmail.com");
        msg.setTo(order.getUser().getEmail());
        //log.info("User mail: {}", order.getUser().getEmail());
        msg.setSubject("Order Confirmation - Order # "+ order.getId());
        msg.setText(
                        "Hello "+order.getUser().getFirstName()+" "+order.getUser().getLastName()+", \n\n"+
                        "Your order has been confirmed!\n" +
                        "Total Amount: " + order.getTotalAmount() + "\n" +
                        "Payment Method: " + order.getPaymentMethod() + "\n" +
                        "Shipping Address: " + order.getShippingAddress() + "\n\n" +
                        "Thank you for shopping with us!"
        );
        mailSender.send(msg);

        //log.info("Email is sent to respective user");
    }
}
