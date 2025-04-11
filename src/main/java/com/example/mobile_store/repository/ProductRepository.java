package com.example.mobile_store.repository;

import com.example.mobile_store.models.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findAll();
    Product findById(long id);
    Product findByProductName(String productName);
    @Query(value = "select p.id from Product p where p.id = :id", nativeQuery = false)
    Long findIdByProductId(@Param("id") Long id);
    @Query(value = "select p from Product p where p.id = :id", nativeQuery = false)
    Product findByProductId(@Param("id") Long id);

    // Lấy sản phẩm theo category_id
    @Query("SELECT p FROM Product p WHERE p.category.id = :categoryId")
    List<Product> findByCategoryId(@Param("categoryId") Long categoryId);

    // Lấy sản phẩm theo category_detail_id
    @Query("SELECT p FROM Product p WHERE p.categoryDetail.id = :categoryDetailId")
    List<Product> findByCategoryDetailId(@Param("categoryDetailId") Long categoryDetailId);
}
