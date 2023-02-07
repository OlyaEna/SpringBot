package com.SpringBot.service.impl;

import com.SpringBot.DTO.GenreDto;
import com.SpringBot.model.entity.Genre;
import com.SpringBot.model.repository.GenreRepository;
import com.SpringBot.service.GenreService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class GenreServiceImpl implements GenreService {
    private final GenreRepository genreRepository;

    @Override
    public List<GenreDto> findAll() {
        List<Genre> genres = genreRepository.findAll();
        List<GenreDto> genreDtoList = transfer(genres);
        return genreDtoList;
    }

    private void mapper(Genre genre, GenreDto genreDto) {
        genreDto.setId(genre.getId());
        genreDto.setName(genre.getName());
        genreDto.setProducts(genre.getProducts());
    }

    private List<GenreDto> transfer(List<Genre> genres) {
        List<GenreDto> genreDtoList = new ArrayList<>();
        for (Genre genre : genres) {
            GenreDto genreDto = new GenreDto();
            mapper(genre, genreDto);
            genreDtoList.add(genreDto);
        }
        return genreDtoList;
    }
}
