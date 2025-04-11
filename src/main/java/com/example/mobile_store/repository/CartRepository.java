package com.example.mobile_store.repository;

import com.example.mobile_store.models.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CartRepository extends JpaRepository<Cart, Long> {
    @Query("SELECT c FROM Cart c WHERE c.user.id = :userId AND c.productPrice.id = :productPriceId")
    Cart findByUserIdAndProductPriceId(@Param("userId") Long userId, @Param("productPriceId") Long productPriceId);

    List<Cart> findByUserId(Long userId);
}
