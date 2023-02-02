package com.SpringBot.model.repository;

import com.SpringBot.model.entity.Genre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GenreRepository extends JpaRepository<Genre, Long> {
    @Query(value = "select (genre_id) from  product_genres  pg  where product_id=genre_id", nativeQuery = true)
    List<String> findAllGenres();
}
