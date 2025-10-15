package com.example.elctronic.store.ElectronicStore.services;

import com.example.elctronic.store.ElectronicStore.dtos.AddItemToCartRequest;
import com.example.elctronic.store.ElectronicStore.dtos.CartDto;

public interface CartService {

    //add items to cart

    //case 1: cart for user is not available : we will create the cart and add the items
    //case 2: cart available add the items to the cart

    CartDto addItemToCart(String userId, AddItemToCartRequest request);

    //remove item from cart
    void removeItemFromCart(String userId, int cartItem);

    //remove all items from cart
    void clearCart(String userId);

    CartDto getCartByUser(String userId);
}
