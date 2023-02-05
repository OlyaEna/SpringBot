package com.SpringBot.service;

import com.SpringBot.DTO.ProductDto;
import com.SpringBot.model.entity.Product;
import com.SpringBot.model.entity.Selection;

import java.util.List;

public interface ProductService {
    ProductDto exampleMovie();
    ProductDto exampleSeries();

    List<ProductDto> findProductsBySelection(Long id);

}
