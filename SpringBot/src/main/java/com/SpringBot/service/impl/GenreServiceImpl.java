package com.SpringBot.service.impl;

import com.SpringBot.model.repository.GenreRepository;
import com.SpringBot.service.GenreService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class GenreServiceImpl implements GenreService {
    private final GenreRepository genreRepository;

//    @Override
//    public String genresNames(ProductDto productDto) {
//        List<String> genres = genreRepository.findGenresNameByProduct(productDto);
//        return genres.toString();
//    }
}
