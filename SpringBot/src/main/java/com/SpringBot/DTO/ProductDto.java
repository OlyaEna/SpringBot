package com.SpringBot.DTO;

import com.SpringBot.model.entity.Genre;
import com.SpringBot.model.entity.Type;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductDto {
    private Long id;
    private String title;
    private String url;
    private Type type;
    private String description;
    private String rating;
    private List<Genre> genres;
}
