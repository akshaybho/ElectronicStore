package com.example.elctronic.store.ElectronicStore.services.impl;

import com.example.elctronic.store.ElectronicStore.dtos.AddItemToCartRequest;
import com.example.elctronic.store.ElectronicStore.dtos.CartDto;
import com.example.elctronic.store.ElectronicStore.entities.Cart;
import com.example.elctronic.store.ElectronicStore.entities.CartItem;
import com.example.elctronic.store.ElectronicStore.entities.Product;
import com.example.elctronic.store.ElectronicStore.entities.User;
import com.example.elctronic.store.ElectronicStore.exception.BadApiRequest;
import com.example.elctronic.store.ElectronicStore.exception.ResourceNotFoundException;
import com.example.elctronic.store.ElectronicStore.repositories.CartItemRepository;
import com.example.elctronic.store.ElectronicStore.repositories.CartRepository;
import com.example.elctronic.store.ElectronicStore.repositories.ProductRepository;
import com.example.elctronic.store.ElectronicStore.repositories.UserRepository;
import com.example.elctronic.store.ElectronicStore.services.CartService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;

@Service
public class CartServiceImpl implements CartService {


    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private ModelMapper mapper;

    @Autowired
    private CartItemRepository cartItemRepository;

    @Override
    public CartDto addItemToCart(String userId, AddItemToCartRequest request) {

        int quantity = request.getQuantity();
        String productId = request.getProductId();

        if(quantity <= 0)
        {
            throw new BadApiRequest("Requested quantity is not valid !!");
        }

        //fetch the product
        Product product = productRepository.findById(productId).orElseThrow(() -> new ResourceNotFoundException("Product is not found !!"));

        //fetch user from db
        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User is not found !!"));

        Cart cart = null;
        try {
            cart = cartRepository.findByUser(user).get();
        } catch (NoSuchElementException e) {
            cart = new Cart();
            cart.setCreatedAt(new Date());
        }

        //perform cart operation
        //if cart items already present, then update
        AtomicBoolean updated = new AtomicBoolean(false);
        List<CartItem> items = cart.getItems();

        List<CartItem> updatedItems = items.stream().map(item -> {

            if (item.getProduct().getId().equals(productId)) {
                item.setQuantity(quantity);
                item.setTotalPrice(quantity * product.getPrice());
                 updated.set(true);
            }
            return item;
        }).collect(Collectors.toList());

        cart.setItems(updatedItems);

        //create items
        if (!updated.get()) {
            CartItem cartItem = CartItem.builder()
                    .quantity(quantity)
                    .totalPrice(quantity * product.getPrice())
                    .cart(cart)
                    .product(product)
                    .build();


            cart.getItems().add(cartItem);
        }

            cart.setUser(user);

            Cart updatedCart = cartRepository.save(cart);
            return mapper.map(updatedCart, CartDto.class);
        }

    @Override
    public void removeItemFromCart(String userId, int cartItem) {

        CartItem cartItem1 = cartItemRepository.findById(cartItem).orElseThrow(() -> new ResourceNotFoundException("Item in the cart not found !!"));
        cartItemRepository.delete(cartItem1);
    };

    @Override
    public void clearCart(String userId) {

        //fetch user from db
        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User is not found !!"));
        Cart cart = cartRepository.findByUser(user).orElseThrow(() -> new ResourceNotFoundException("Cart of given user not found !!"));
        cart.getItems().clear();
        cartRepository.save(cart);
    }
}
