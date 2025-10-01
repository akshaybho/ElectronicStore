package com.example.elctronic.store.ElectronicStore.dtos;

import com.example.elctronic.store.ElectronicStore.entities.CartItem;
import com.example.elctronic.store.ElectronicStore.entities.User;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CartDto {


    private String cartId;
    private Date createdAt;
    private UserDto user;
    private List<CartItemDto> items = new ArrayList<>();
}
