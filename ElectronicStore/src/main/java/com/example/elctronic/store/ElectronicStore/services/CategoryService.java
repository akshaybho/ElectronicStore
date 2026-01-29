package com.example.elctronic.store.ElectronicStore.services;

import com.example.elctronic.store.ElectronicStore.dtos.CategoryDto;
import com.example.elctronic.store.ElectronicStore.dtos.PageableResponse;

import java.util.List;

public interface CategoryService {

    //create
    CategoryDto create(CategoryDto categoryDto);

    // update
    CategoryDto update(CategoryDto categoryDto, String categoryId);

    // delete
    void delete(String categoryId);

    // getAll
    PageableResponse<CategoryDto> getAll(int pageNumber, int pageSize, String sortBy, String sortDir);

    // get single category detail
    CategoryDto get(String categoryId);

    // search
    List<CategoryDto> searchCategory(String keyword);
}
