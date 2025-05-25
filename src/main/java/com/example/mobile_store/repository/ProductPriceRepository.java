package com.example.mobile_store.repository;

import com.example.mobile_store.models.Color;
import com.example.mobile_store.models.Product;
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


    @Query("SELECT p FROM ProductPrice p WHERE p.product.id = :productId AND p.capacity = :capacity AND LOWER(p.color) = LOWER(:color)")
    Optional<ProductPrice> findByProduct_IdAndColorAndCapacity(@Param("productId") Long productId,
                                                               @Param("color") String color,
                                                               @Param("capacity") String capacity);

    ProductPrice findById(long id);

    @Query(value = "select p from ProductPrice p where p.id = :id", nativeQuery = false)
    ProductPrice findByProductPriceId(@Param("id") Long id);

    List<ProductPrice> findByProduct_Id(Long productId);

    void deleteByProduct_IdAndCapacity(Long productId, String capacity);

    List<ProductPrice> findByColor(String color);

    void deleteByProduct_IdAndColor(Long productId, String color);

    List<ProductPrice> findByProduct_IdAndCapacity(Long productId, String capacity);





    @Query("SELECT DISTINCT pp.colorId FROM ProductPrice pp WHERE pp.product.id = :productId")
    List<Color> findColorsByProductId(@Param("productId") Long productId);
//    Optional<ProductPrice> findByProduct_IdAndColorAndCapacity(Long productId, String color, String capacity);

    Optional<ProductPrice> findByProduct_IdAndColorIsNullAndCapacity(Long productId, String capacity);

    boolean existsByProduct_IdAndCapacity(Long productId, String capacity);

    List<ProductPrice> findAllByProduct_IdAndCapacity(Long productId, String capacity);



}
