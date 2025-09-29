package com.example.elctronic.store.ElectronicStore.services.impl;

import com.example.elctronic.store.ElectronicStore.dtos.PageableResponse;
import com.example.elctronic.store.ElectronicStore.dtos.ProductDto;
import com.example.elctronic.store.ElectronicStore.entities.Category;
import com.example.elctronic.store.ElectronicStore.entities.Product;
import com.example.elctronic.store.ElectronicStore.exception.ResourceNotFoundException;
import com.example.elctronic.store.ElectronicStore.helper.Helper;
import com.example.elctronic.store.ElectronicStore.repositories.CategoryRepository;
import com.example.elctronic.store.ElectronicStore.repositories.ProductRepository;
import com.example.elctronic.store.ElectronicStore.services.ProductService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;
import java.util.UUID;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ModelMapper mapper;

    @Value("product.image.path")
    private String imagePath;

    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public ProductDto create(ProductDto productDto) {

        String productId = UUID.randomUUID().toString();
        productDto.setId(productId);

        productDto.setAddedDate(new Date());

        Product product = mapper.map(productDto, Product.class);
        Product savedProduct = productRepository.save(product);
        return mapper.map(savedProduct, ProductDto.class);
    }

    @Override
    public ProductDto update(ProductDto productDto, String productId) {

        //fetch the product of given id
        Product product = productRepository.findById(productId).orElseThrow(()-> new ResourceNotFoundException("Product not found of given Id !!"));

        product.setTitle(productDto.getTitle());
        product.setDescription(productDto.getDescription());
        product.setPrice(productDto.getPrice());
        product.setDiscountedPrice(productDto.getDiscountedPrice());
        product.setQuantity(productDto.getQuantity());
        product.setLive(productDto.isLive());
        product.setStock(product.isStock());
        product.setProductImageName(productDto.getProductImageName());

        Product updatedProduct = productRepository.save(product);
        return mapper.map(updatedProduct, ProductDto.class);

    }

    @Override
    public void delete(String productId) {

        Product product = productRepository.findById(productId).orElseThrow(()-> new ResourceNotFoundException("Product with this id is not found!!"));
        //productRepository.delete(product);

        String fullPath = imagePath + product.getProductImageName();
        try{
            Path path = Paths.get(fullPath);
            Files.delete(path);
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }

    }

    @Override
    public ProductDto getSingleProduct(String productId) {
       Product product = productRepository.findById(productId).orElseThrow(()-> new RuntimeException("Not found!!"));

       return mapper.map(product, ProductDto.class);
    }

    @Override
    public PageableResponse<ProductDto> getAllProduct(int pageNumber, int pageSize, String sortBy, String sortDir) {

        Sort sort = (sortDir.equalsIgnoreCase("desc")) ? (Sort.by(sortBy).descending()) : (Sort.by(sortBy).ascending());
        Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);
        Page<Product> page = productRepository.findAll(pageable);
        return Helper.getPageableResponse(page, ProductDto.class);
    }

    @Override
    public PageableResponse<ProductDto> getAllLive(int pageNumber, int pageSize, String sortBy, String sortDir) {

        Sort sort = (sortDir.equalsIgnoreCase("desc")) ? (Sort.by(sortBy).descending()) : (Sort.by(sortBy).ascending());
        Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);
        Page<Product> page = productRepository.findByLiveTrue(pageable);
        return Helper.getPageableResponse(page, ProductDto.class);
    }

    @Override
    public PageableResponse<ProductDto> searchByTitle(String subTitle, int pageNumber, int pageSize, String sortBy, String sortDir) {

        Sort sort = (sortDir.equalsIgnoreCase("desc")) ? (Sort.by(sortBy).descending()) : (Sort.by(sortBy).ascending());
        Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);
        Page<Product> page = productRepository.findByTitleContaining(subTitle, pageable);
        return Helper.getPageableResponse(page, ProductDto.class);
    }

    @Override
    public ProductDto createProductWithCategory(ProductDto productDto, String categoryId) {

        //fetch the category from db
        Category category = categoryRepository.findById(categoryId).orElseThrow(()->new ResourceNotFoundException("Category not found!!"));
        Product product = mapper.map(productDto, Product.class);

        String productId = UUID.randomUUID().toString();
        product.setId(productId);

        productDto.setAddedDate(new Date());
        product.setCategory(category);

        Product savedProduct = productRepository.save(product);
        return mapper.map(savedProduct, ProductDto.class);

    }

    @Override
    public ProductDto updateCategory(String productId, String categoryId) {

        //product fetch
        Product product = productRepository.findById(productId).orElseThrow(() -> new ResourceNotFoundException("Product of given id not found"));
        Category category = categoryRepository.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException("Category of the given id not found"));

        product.setCategory(category);
        Product savedProduct = productRepository.save(product);

        return mapper.map(savedProduct, ProductDto.class);
    }

    @Override
    public PageableResponse<ProductDto> getAllOfCategory(String categoryId, int pageNumber, int pageSize, String sortBy, String sortDir) {

        Category category = categoryRepository.findById(categoryId).orElseThrow(()-> new ResourceNotFoundException("Not found!!"));
        Sort sort = (sortDir.equalsIgnoreCase("desc")) ? (Sort.by(sortBy).descending()) : (Sort.by(sortBy).ascending());
        Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);
        Page <Product> page = productRepository.findByCategory(category, pageable);
        return Helper.getPageableResponse(page, ProductDto.class);
    }

}
