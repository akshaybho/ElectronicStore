package com.example.elctronic.store.ElectronicStore.services;

import com.example.elctronic.store.ElectronicStore.dtos.PageableResponse;
import com.example.elctronic.store.ElectronicStore.dtos.ProductDto;

public interface ProductService {

    //create
    ProductDto create(ProductDto productDto);

    //update
    ProductDto update(ProductDto productDto, String productId);

    //delete
    void delete(String productId);

    //get single
    ProductDto getSingleProduct(String productId);

    //get all
    PageableResponse<ProductDto> getAllProduct(int pageNumber, int pageSize, String sortBy, String sortDir);

    //get all : live
    PageableResponse <ProductDto> getAllLive(int pageNumber, int pageSize, String sortBy, String sortDir);

    //search product
    PageableResponse <ProductDto> searchByTitle(String subTitle, int pageNumber, int pageSize, String sortBy, String sortDir);

    ProductDto createProductWithCategory(ProductDto productDto, String categoryId);

    ProductDto updateCategory(String productId, String categoryId);
}
