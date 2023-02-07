package com.SpringBot.model.repository;

import com.SpringBot.model.entity.Product;
import com.SpringBot.model.entity.Selection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    @Query(value = "select * from products p  where p.type_id=1 order by rand() asc limit 1 ", nativeQuery = true)
    Product exampleMovie();

    @Query(value = "select * from products p  where p.type_id=2 order by rand() asc limit 1 ", nativeQuery = true)
    Product exampleSeries();

    @Query(value = "select * from  products  p  where p.selection_id=:id", nativeQuery = true)
    List<Product> findProductsBySelection(Long id);
    @Query(value = "select (product_id) from  product_genres  pg  where pg.genre_id=:id", nativeQuery = true)
    List<Long> findProductsByGenre(Long id);
    @Query(value = "select * from  products  p  where p.id IN (:id)", nativeQuery = true)
    List<Product> findProductsById(@Param ("id") List <Long> id);

}