package com.SpringBot.model.repository;

import com.SpringBot.model.entity.Product;
import com.SpringBot.model.entity.Selection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SelectionRepository extends JpaRepository<Selection, Long> {
}
