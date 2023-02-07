package com.SpringBot.service.impl;

import com.SpringBot.model.entity.Product;
import com.SpringBot.DTO.ProductDto;
import com.SpringBot.model.repository.ProductRepository;
import com.SpringBot.service.ProductService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;

    @Override
    public ProductDto exampleMovie() {
        Product product = productRepository.exampleMovie();
        ProductDto productDto = new ProductDto();
        mapper(product, productDto);
        return productDto;
    }

    @Override
    public ProductDto exampleSeries() {
        Product product = productRepository.exampleSeries();
        ProductDto productDto = new ProductDto();
        mapper(product, productDto);
        return productDto;
    }

    @Override
    public List<ProductDto> findProductsBySelection(Long id) {
        List<Product> products = productRepository.findProductsBySelection(id);
        List<ProductDto> productDtoList = transfer(products);
        return productDtoList;
    }

    @Override
    public List<ProductDto> findAll() {
        List<Product> products = productRepository.findAll();
        List<ProductDto> productDtoList = transfer(products);
        return productDtoList;
    }

    @Override
    public List<ProductDto> findProductsByGenre(Long id) {
        List<Product> products = productRepository.findProductsById(productRepository.findProductsByGenre(id));
        List<ProductDto> productDtoList = transfer(products);
        return productDtoList;
    }

    private void mapper(Product product, ProductDto productDto) {
        productDto.setId(product.getId());
        productDto.setTitle(product.getTitle());
        productDto.setDescription(product.getDescription());
        productDto.setType(product.getType());
        productDto.setGenres(product.getGenres());
        productDto.setRating(product.getRating());
        productDto.setUrl(product.getUrl());
        productDto.setHdUrl(product.getHdUrl());
        productDto.setYear(product.getYear());
        productDto.setSelection(product.getSelection());
    }

    private List<ProductDto> transfer(List<Product> products) {
        List<ProductDto> productDtoList = new ArrayList<>();
        for (Product product : products) {
            ProductDto productDto = new ProductDto();
            mapper(product, productDto);
            productDtoList.add(productDto);
        }
        return productDtoList;
    }

}
