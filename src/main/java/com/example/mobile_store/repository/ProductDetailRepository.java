package com.example.mobile_store.repository;

import com.example.mobile_store.models.ProductDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductDetailRepository extends JpaRepository<ProductDetail, Long> {
//    Optional<ProductDetail> findByProductId(Long productId);

    @Query(value = "select * from product_detail p where p.product_id = :id", nativeQuery = true)
    ProductDetail findByProductId(@Param("id") Long productId);

    @Query("SELECT pd FROM ProductDetail pd WHERE pd.product.id = :productId")
    List<ProductDetail> findProductDetailsByProductId(@Param("productId") long productId);
}
