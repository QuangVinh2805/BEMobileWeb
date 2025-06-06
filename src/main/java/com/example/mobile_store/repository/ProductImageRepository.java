package com.example.mobile_store.repository;

import com.example.mobile_store.models.Color;
import com.example.mobile_store.models.ProductImage;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductImageRepository extends JpaRepository<ProductImage, Long> {
    List<ProductImage> findByProductIdAndColor_Color(long productId, String color);

    void deleteByProductIdAndColorId(Long productId, Long colorId);

}
