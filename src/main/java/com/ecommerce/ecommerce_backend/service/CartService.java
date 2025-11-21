package com.ecommerce.ecommerce_backend.service;

import com.ecommerce.ecommerce_backend.dto.Cart.AddToCart;
import com.ecommerce.ecommerce_backend.dto.Cart.UpdateCartDTO;
import com.ecommerce.ecommerce_backend.models.Cart;
import com.ecommerce.ecommerce_backend.models.CartItems;
import com.ecommerce.ecommerce_backend.models.Products;
import com.ecommerce.ecommerce_backend.models.Users;
import com.ecommerce.ecommerce_backend.repository.CartItemsRepository;
import com.ecommerce.ecommerce_backend.repository.CartRepository;
import com.ecommerce.ecommerce_backend.repository.ProductRepository;
import com.ecommerce.ecommerce_backend.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
public class CartService {
    private final ProductRepository productRepository;
    private final UserRepository userRepository;
    private final CartRepository cartRepository;
    private final CartItemsRepository cartItemsRepository;
    public CartService(ProductRepository productRepository,
                       UserRepository userRepository,
                       CartRepository cartRepository,
                       CartItemsRepository cartItemsRepository){
        this.productRepository = productRepository;
        this.userRepository = userRepository;
        this.cartRepository = cartRepository;
        this.cartItemsRepository = cartItemsRepository;
    }


    @Transactional
    public String addToCart(AddToCart addToCart){
        try{
            Users user = userRepository.findById(addToCart.getUserId()).orElseThrow(
                    ()-> new RuntimeException("No user found")
            );

            Products products = productRepository.findById(addToCart.getProductId()).orElseThrow(
                    () -> new RuntimeException("No Product found")
            );

            if(products.getStockQuantity() < addToCart.getQuantity()){
                throw new RuntimeException("In sufficient stock quantity");
            }

            Cart cart = cartRepository.findByUserId(addToCart.getUserId()).orElseGet(
                    () -> {
                        Cart newCart = new Cart();
                        newCart.setUser(user);
                        newCart.setTotalPrice(BigDecimal.ZERO);

                        return cartRepository.save(newCart);
                    }
            );

            Optional<CartItems> ExistingCartItems = cartItemsRepository.findByCartIdAndProductsId(cart.getId(), addToCart.getProductId());

            if(ExistingCartItems.isPresent()){
                CartItems cartItems = ExistingCartItems.get();
                int newQuantity = cartItems.getQuantity() + addToCart.getQuantity();

                cartItems.setQuantity(newQuantity);
                BigDecimal total = BigDecimal.valueOf(products.getPrice()).multiply(BigDecimal.valueOf(newQuantity));
                cartItems.setTotal(total);
                cartItemsRepository.save(cartItems);
            }
            else{
                CartItems cartItems = new CartItems();

                cartItems.setCart(cart);
                cartItems.setProducts(products);
                cartItems.setPrice(BigDecimal.valueOf(products.getPrice()));
                cartItems.setQuantity(addToCart.getQuantity());
                cartItems.setTotal(BigDecimal.valueOf(products.getPrice()).multiply(BigDecimal.valueOf(addToCart.getQuantity())));

                cart.getCartItems().add(cartItems);
            }

            // 5. Recalculate cart total price
            BigDecimal total = cart.getCartItems().stream()
                    .map(CartItems::getTotal)
                    .reduce(BigDecimal.ZERO, BigDecimal::add);

            cart.setTotalPrice(total);

            cartRepository.save(cart);
            return "Product added to cart";
        } catch (RuntimeException e) {
            throw new RuntimeException(e);
        }
    }

    @Transactional
    public String removeProductFromCart(Long userId, Long productId){
        try{
            Optional<Cart> cart1 = cartRepository.findByUserId(userId);
            if(!cart1.isPresent()){
                throw new RuntimeException("Cart not found");
            }

            Cart cart = cart1.get();

            Optional<CartItems> cartItemsOptional = cartItemsRepository.findByCartIdAndProductsId(cart.getId(), productId);
            if(cartItemsOptional.isEmpty()){
                return "No cart items found for this product";
            }

            CartItems cartItems = cartItemsOptional.get();

            if(!cartItems.getCart().getId().equals(cart.getId())){
                return "Cart and User mismatch";
            }

            cart.getCartItems().remove(cartItems);


            // update totals
            BigDecimal total = cart.getCartItems().stream()
                    .map(CartItems::getTotal)
                    .reduce(BigDecimal.ZERO, BigDecimal::add);

            cart.setTotalPrice(total);
            cartRepository.save(cart);

            return "Item removed from cart";
        } catch (RuntimeException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Cart> viewCarts(Long userId){
        return cartRepository.findByUserId(userId)
                .map(List:: of)
                .orElse(List.of());
    }
    @Transactional
    public String updateCart(Long userId, UpdateCartDTO updateCartDTO) {

        Cart cart = cartRepository.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("Cart is empty"));

        CartItems cartItem = cartItemsRepository
                .findByCartIdAndProductsId(cart.getId(), updateCartDTO.getProductId())
                .orElseThrow(() -> new RuntimeException("No cart item found"));

        Products product = cartItem.getProducts(); // already linked

        // Validate stock
        if (updateCartDTO.getQuantity() > product.getStockQuantity()) {
            return "Insufficient product quantity";
        }

        // -------- REMOVE ITEM CASE --------
        if (updateCartDTO.getQuantity() == 0) {

            // delete only from THIS USER'S CART
            cart.getCartItems().remove(cartItem);
            cartItemsRepository.delete(cartItem);

            // recalc total
            BigDecimal newTotal = cart.getCartItems().stream()
                    .map(CartItems::getTotal)
                    .reduce(BigDecimal.ZERO, BigDecimal::add);

            cart.setTotalPrice(newTotal);
            cartRepository.save(cart);

            return "Item removed from cart";
        }

        // -------- NORMAL UPDATE CASE --------
        cartItem.setQuantity(updateCartDTO.getQuantity());

        BigDecimal itemTotal = BigDecimal.valueOf(product.getPrice())
                .multiply(BigDecimal.valueOf(updateCartDTO.getQuantity()));
        cartItem.setTotal(itemTotal);

        cartItemsRepository.save(cartItem);

        // Update cart total
        BigDecimal cartTotal = cart.getCartItems().stream()
                .map(CartItems::getTotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        cart.setTotalPrice(cartTotal);
        cartRepository.save(cart);

        return "Cart updated successfully";
    }

    @Transactional
    public String clearCart(Long userId){
        Cart cart = cartRepository.findByUserId(userId).orElseThrow(()->new RuntimeException("Cart not found"));

        cartItemsRepository.deleteByCartId(cart.getId());

        cart.setTotalPrice(BigDecimal.ZERO);

        cartRepository.save(cart);

        return "Cart cleared successfully";
    }

}

//add item, remove item, update quantity, auto recalc price