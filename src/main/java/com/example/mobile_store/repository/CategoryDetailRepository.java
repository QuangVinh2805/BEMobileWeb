package com.example.mobile_store.repository;

import com.example.mobile_store.models.CategoryDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryDetailRepository extends JpaRepository<CategoryDetail, Long> {
    List<CategoryDetail> findAll();

    List<CategoryDetail> findByCategoryId(long categoryId);

    CategoryDetail findByCategoryDetailName(String categoryDetailName);
}
