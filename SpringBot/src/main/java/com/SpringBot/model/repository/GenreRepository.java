package com.SpringBot.model.repository;

import com.SpringBot.DTO.ProductDto;
import com.SpringBot.model.entity.Genre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GenreRepository extends JpaRepository<Genre, Long> {
    @Query(value = "select (genre_id) from  product_genres  pg  where pg.product_id=:id", nativeQuery = true)
    List<Long> findGenresNames(Long id);

    @Query(value = "select (name) from  genres  g  where g.id IN (:id)", nativeQuery = true)
    List<String> findGenresNamesById(@Param ("id") List<Long> id);

}
