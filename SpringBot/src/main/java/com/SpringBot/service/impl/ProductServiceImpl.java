package com.SpringBot.service.impl;

import com.SpringBot.model.entity.Product;
import com.SpringBot.DTO.ProductDto;
import com.SpringBot.model.repository.ProductRepository;
import com.SpringBot.service.ProductService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

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

    private void mapper(Product product, ProductDto productDto) {
        productDto.setId(product.getId());
        productDto.setTitle(product.getTitle());
        productDto.setDescription(product.getDescription());
        productDto.setType(product.getType());
        productDto.setGenres(product.getGenres());
        productDto.setRating(product.getRating());
        productDto.setUrl(product.getUrl());
        productDto.setYear(product.getYear());
    }

}
