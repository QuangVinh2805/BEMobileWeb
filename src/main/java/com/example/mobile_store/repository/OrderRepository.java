package com.example.mobile_store.repository;

import com.example.mobile_store.models.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;


@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    @Override
    List<Order> findAll();

    @Query("SELECT o FROM Order o WHERE o.createdAt >= :startDate AND o.createdAt < :endDate")
    List<Order> findByDate(@Param("startDate") Date startDate, @Param("endDate") Date endDate);

    Order findOrderById(long id);

    @Query(value = "select o.id from Order o where o.id = :id",nativeQuery = false)
    Long findIdByOrderId(@Param("id") Long id);
}
