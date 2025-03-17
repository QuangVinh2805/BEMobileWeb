package com.example.mobile_store.repository;

import com.example.mobile_store.models.Color;
import com.example.mobile_store.models.ProductPrice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductPriceRepository extends JpaRepository<ProductPrice, Long> {
    @Query("SELECT DISTINCT p.capacity FROM ProductPrice p WHERE p.product.id = :productId")
    List<String> findCapacityByProductId(@Param("productId") Long productId);

    Optional<ProductPrice> findByProductIdAndColorAndCapacity(Long productId, String color, String capacity);
}
