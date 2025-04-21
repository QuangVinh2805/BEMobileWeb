package com.example.mobile_store.repository;

import com.example.mobile_store.models.Order;
import com.example.mobile_store.models.OrderDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface OrderDetailRepository extends JpaRepository<OrderDetail,Long> {
    List<OrderDetail> findOrderDetailByOrder(Order order);

    OrderDetail findOrderDetailById(Long id);

    @Query(value = "select * from order_detail od where order_id in (select id from `order` where user_id = :userId) order by order_id desc", nativeQuery = true)
    List<OrderDetail> getOrderDetailsByUserId(Long userId);

    @Query(value = "SELECT * FROM order_detail od " +
            "WHERE od.order_id IN (SELECT o.id FROM `order` o WHERE o.user_id = :userId) " +
            "AND od.id = :orderDetailId " +
            "ORDER BY od.order_id DESC",
            nativeQuery = true)
    List<OrderDetail> findByUserIdAndOrderDetailId(@Param("userId") Long userId,
                                                       @Param("orderDetailId") Long orderDetailId);

}
