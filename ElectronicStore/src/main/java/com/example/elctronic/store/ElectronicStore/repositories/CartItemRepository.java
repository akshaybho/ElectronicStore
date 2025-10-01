package com.example.elctronic.store.ElectronicStore.repositories;

import com.example.elctronic.store.ElectronicStore.entities.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartItemRepository extends JpaRepository<CartItem, Integer> {

}
