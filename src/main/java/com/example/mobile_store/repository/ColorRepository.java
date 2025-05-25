package com.example.mobile_store.repository;

import com.example.mobile_store.models.Color;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ColorRepository extends JpaRepository<Color, Long> {
    Color findColorById(Long id); // Thêm method này để không dùng Optional

    Color findByColor(String color); // trả về null nếu không có

    boolean existsByColor(String color);

}
