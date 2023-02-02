package com.SpringBot.model.repository;

import com.SpringBot.model.entity.Genre;
import com.SpringBot.model.entity.Type;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TypeRepository extends JpaRepository<Type, Long> {
    @Query(value = "select (name) from types t", nativeQuery = true)
    List<String> findAllTypes();
}
