package com.ecommerce.ecommerce_backend.service;

import com.ecommerce.ecommerce_backend.dto.PlaceOrder.BuyNowDTO;
import com.ecommerce.ecommerce_backend.dto.PlaceOrder.CheckoutOrderDTO;
import com.ecommerce.ecommerce_backend.dto.PlaceOrder.OrderItemResponseDTO;
import com.ecommerce.ecommerce_backend.dto.PlaceOrder.OrderResponseDTO;
import com.ecommerce.ecommerce_backend.models.*;
import com.ecommerce.ecommerce_backend.repository.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.User;
import org.aspectj.weaver.ast.Or;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class OrderServices {
    private final ProductRepository productRepository;
    private final OrdersRepository ordersRepository;
    private final OrderItemsRepository orderItemsRepository;
    private final UserRepository userRepository;
    private final CartRepository cartRepository;
    private final CartItemsRepository cartItemsRepository;
    public OrderServices(ProductRepository productRepository,
                         OrdersRepository ordersRepository,
                         OrderItemsRepository orderItemsRepository,
                         UserRepository userRepository,
                         CartRepository cartRepository,
                         CartItemsRepository cartItemsRepository){
        this.orderItemsRepository = orderItemsRepository;
        this.ordersRepository = ordersRepository;
        this.productRepository = productRepository;
        this.userRepository = userRepository;
        this.cartRepository = cartRepository;
        this.cartItemsRepository = cartItemsRepository;
    }

    @Transactional
    public String buyNowOrder(Long userId, BuyNowDTO dto) {
        //1 Validate user
        Users user = userRepository.findById(userId).orElseThrow(
                () -> new RuntimeException("User not found")
        );

        //2. Validate Product
        Products products = productRepository.findById(dto.getProductId()).orElseThrow(
                () -> new RuntimeException("Product not found")
        );

        //3. Check the product stock quantity
        if (dto.getQuantity() > products.getStockQuantity()) {
            return "Insufficient Stock !!";
        }

        //4. Create new Order
        Orders newOrder = new Orders();
        newOrder.setUser(user);
        newOrder.setOrderTime(LocalDateTime.now());
        newOrder.setShippingAddress(dto.getShippingAddress());
        newOrder.setPaymentMethod(dto.getPaymentMethod());
        newOrder.setOrderStatusEnum(OrderStatus.PENDING);

        // Save order to get OrderId
        Orders savedOrder = ordersRepository.save(newOrder);

        //5. Create Order Item
        OrderItems orderItems = new OrderItems();
        orderItems.setOrders(savedOrder);
        orderItems.setProducts(products);
        orderItems.setQuantity(dto.getQuantity());
        orderItems.setPriceAtOrderTime(BigDecimal.valueOf(products.getPrice()));

        BigDecimal total = BigDecimal.valueOf(products.getPrice())
                .multiply(BigDecimal.valueOf(dto.getQuantity()));

        orderItems.setTotal(total);
        orderItemsRepository.save(orderItems);

        //6. Update order total
        savedOrder.setTotalAmount(total);
        ordersRepository.save(savedOrder);


        //log.info("Product previous quantity: {}",products.getStockQuantity());
        //7. Deduct Product Stock

        products.setStockQuantity(products.getStockQuantity() - dto.getQuantity());

        productRepository.save(products);
        //log.info("Product current quantity: {}",products.getStockQuantity());
        return "Order Placed Successfully... Order ID: " + savedOrder.getId();
    }

    public List<OrderResponseDTO> allOrders(Long userId){
        if(!userRepository.existsById(userId)){
            throw new RuntimeException("User not found");
        }
        List<Orders> ordersList = ordersRepository.findOrdersByUserIdOrderByOrderTimeDesc(userId);
        return ordersList.stream().map(order -> {
            OrderResponseDTO dto = new OrderResponseDTO();
            dto.setOrderId(order.getId());
            dto.setOrderTime(order.getOrderTime());
            dto.setTotalAmount(order.getTotalAmount());
            dto.setOrderStatus(order.getOrderStatusEnum().name());

            List<OrderItemResponseDTO> itemDTOs = order.getOrderItems().stream().map(item -> {
                OrderItemResponseDTO i = new OrderItemResponseDTO();
                i.setProductId(item.getProducts().getId());
                i.setProductName(item.getProducts().getName());
                i.setQuantity(item.getQuantity());
                i.setPriceAtOrderTime(item.getPriceAtOrderTime().intValue());
                i.setTotal(item.getTotal().intValue());
                return i;
            }).toList();

            dto.setItems(itemDTOs);

            return dto;
        }).toList();

    }

    public String deleteAllOrders(){
        ordersRepository.deleteAll();
        return "All orders removed";
    }

    @Transactional
    public Orders checkoutOrder(Long userId, CheckoutOrderDTO dto){
        // Fetch cart
        Cart cart = cartRepository.findByUserId(userId).orElseThrow(
                ()->new RuntimeException("Cart not found")
        );

        if(cart.getCartItems().isEmpty()){
            throw new RuntimeException("Cart is empty");
        }

        // Validate Stock
        for(CartItems item: cart.getCartItems()){
            if(item.getProducts().getStockQuantity() < item.getQuantity()){
                throw new RuntimeException("Insufficient Stocks");
            }
        }

        //Create Order
        Orders orders = new Orders();
        orders.setUser(cart.getUser());
        orders.setOrderTime(LocalDateTime.now());
        orders.setOrderStatusEnum(OrderStatus.SHIPPED);
        orders.setPaymentMethod(dto.getPaymentMethod());
        orders.setShippingAddress(dto.getShippingAddress());
        orders.setTotalAmount(cart.getTotalPrice());
        ordersRepository.save(orders);

        // Covert cart items to order items
        List<OrderItems> orderItemsList = new ArrayList<>();

        for(CartItems item: cart.getCartItems()){
            // Create orderItem
            OrderItems orderItems = new OrderItems();
            orderItems.setOrders(orders);
            orderItems.setProducts(item.getProducts());
            orderItems.setQuantity(item.getQuantity());
            orderItems.setPriceAtOrderTime(item.getPrice());
            orderItems.setTotal(item.getTotal());

            orderItemsList.add(orderItems);

            // Deduct Stock
            Products products = item.getProducts();
            products.setStockQuantity(products.getStockQuantity() - item.getQuantity());
            productRepository.save(products);
        }

        orders.setOrderItems(orderItemsList);
        ordersRepository.save(orders);

        // Clear Cart
        cart.getCartItems().clear();
        cart.setTotalPrice(BigDecimal.ZERO);
        cartRepository.save(cart);

        return orders;
    }
}
