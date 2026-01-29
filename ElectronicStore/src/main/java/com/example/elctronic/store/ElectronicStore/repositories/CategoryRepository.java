package com.example.elctronic.store.ElectronicStore.repositories;

import com.example.elctronic.store.ElectronicStore.dtos.CategoryDto;
import com.example.elctronic.store.ElectronicStore.entities.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CategoryRepository extends JpaRepository<Category, String> {

    List<Category> findByTitleContainingIgnoreCase(String keyword);
}
