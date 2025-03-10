package com.example.mobile_store.repository;

import com.example.mobile_store.models.ProductImage;

import java.util.List;

import com.example.mobile_store.request.ProductRequest;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductImageRepository extends JpaRepository<ProductImage, Long> {
    @Query(value = "SELECT pi.image FROM product_image pi WHERE pi.product_id = :productId AND pi.product_color_id = :productColorId AND pi.product_capacity_id = :productCapacityId", nativeQuery = true)
    List<String> findAllImagesByProductIdAndColorIdAndCapacityId(@Param("productId") Long productId, @Param("productColorId") Long productColorId, @Param("productCapacityId") Long productCapacityId);

    @Query(value = "SELECT pi.image FROM product_image pi WHERE pi.product_id = :productId AND pi.product_color_id = :productColorId AND pi.product_capacity_id = :productCapacityId LIMIT 1" , nativeQuery = true)
    String findOneImagesByProductIdAndColorIdAndCapacityId(@Param("productId") Long productId, @Param("productColorId") Long productColorId, @Param("productCapacityId") Long productCapacityId);

    @Query("SELECT new com.example.mobile_store.request.ProductRequest(p.id, p.productName, p.brand, pc.color, pp.capacity, pi.image) " +
            "FROM ProductImage pi " +
            "JOIN pi.product p " +
            "JOIN pi.productColor pc " +
            "JOIN pi.productCapacity pp " +
            "WHERE pi.product.id = :productId " +
            "AND pi.productColor.id = :colorId " +
            "AND pi.productCapacity.id = :capacityId")
    List<ProductRequest> findProductDetails(
            @Param("productId") Long productId,
            @Param("colorId") Long colorId,
            @Param("capacityId") Long capacityId

    );
}
