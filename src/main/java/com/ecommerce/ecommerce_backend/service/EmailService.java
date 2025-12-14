package com.ecommerce.ecommerce_backend.service;

import com.ecommerce.ecommerce_backend.models.OrderItems;
import com.ecommerce.ecommerce_backend.models.Orders;
import jakarta.mail.internet.MimeMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Slf4j
@Service
public class EmailService {
    private final JavaMailSender mailSender;

    public EmailService(JavaMailSender mailSender1){
        this.mailSender = mailSender1;
    }

    //Loading emailTemplate
    private String loadTemplate(String fileName){
        try{
            return new String(
                    Objects.requireNonNull(getClass().getClassLoader()
                            .getResourceAsStream("templates/"+fileName))
                            .readAllBytes()
            );
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    public void sendOrderConfirmation(Orders order, List<OrderItems> items){
        try{
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);

            helper.setFrom("hawarijawed143@gmail.com");
            helper.setTo(order.getUser().getEmail());
            helper.setSubject("Order Confirmation - Order #"+ order.getId());

            //Load template
            String htmlContent = loadTemplate("EmailTemplate.html");

            // Build product rows
            StringBuilder productRows = new StringBuilder();
            for (OrderItems item : items) {
                productRows.append("<tr>")
                        .append("<td>").append(item.getProducts().getName()).append("</td>")
                        .append("<td>").append(item.getPriceAtOrderTime()).append("</td>")
                        .append("<td>").append(item.getQuantity()).append("</td>")
                        .append("<td>").append(item.getTotal()).append("</td>")
                        .append("</tr>");
            }

            // Replace placeholders

            htmlContent = htmlContent
                    .replace("{{orderId}}", String.valueOf(order.getId()))
                    .replace("{{userName}}", order.getUser().getFirstName())
                    .replace("{{productRows}}", productRows.toString())
                    .replace("{{totalAmount}}", String.valueOf(order.getTotalAmount()))
                    .replace("{{paymentMethod}}", order.getPaymentMethod())
                    .replace("{{shippingAddress}}", order.getShippingAddress());

            helper.setText(htmlContent, true);
            mailSender.send(message);

            log.info("HTML order confirmation email sent to {}", order.getUser().getEmail());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        //log.info("Email is sent to respective user");
    }

    public void send(String sendTo, String subject, String text){
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(sendTo);
        message.setSubject(subject);
        message.setText(text);
        message.setFrom("hawarijawed143@gmail.com");
        mailSender.send(message);
    }
}
