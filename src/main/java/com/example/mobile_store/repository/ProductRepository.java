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
}
