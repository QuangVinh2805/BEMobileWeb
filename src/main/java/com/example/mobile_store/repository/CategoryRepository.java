package com.example.mobile_store.repository;

import com.example.mobile_store.models.Category;
import com.example.mobile_store.models.CategoryDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
    List<Category> findAll();

    Category findByCategoryName(String categoryName);
}
