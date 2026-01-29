package com.example.elctronic.store.ElectronicStore.dtos;

import com.example.elctronic.store.ElectronicStore.entities.Cart;
import com.example.elctronic.store.ElectronicStore.entities.Product;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CartItemDto {

    private int cartItemId;

    private ProductDto product;

    private int quantity;

    private int totalPrice;

}
