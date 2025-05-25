package com.example.mobile_store.services;

import com.example.mobile_store.models.Color;
import com.example.mobile_store.repository.ColorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ColorService {
    @Autowired
    ColorRepository colorRepository;

    public ResponseEntity<List<Color>> findAll() {
        List<Color> colors = colorRepository.findAll();
        if (colors.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(colors);
    }


    /* Thêm màu */
    public Color add(String rawName) {
        String name = rawName.trim().toLowerCase();
        if (colorRepository.existsByColor(name)) {
            throw new RuntimeException("Màu '" + name + "' đã tồn tại.");
        }
        Color c = new Color();
        c.setColor(name);
        return colorRepository.save(c);
    }

    /* Sửa tên màu */
    public Color update(Long id, String rawName) {
        Color color = colorRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy màu ID: " + id));

        String newName = rawName.trim().toLowerCase();
        if (!newName.equals(color.getColor()) && colorRepository.existsByColor(newName)) {
            throw new RuntimeException("Tên màu '" + newName + "' đã tồn tại.");
        }
        color.setColor(newName);
        return colorRepository.save(color);
    }

    /* Xoá màu */
    public void remove(Long id) {
        if (!colorRepository.existsById(id)) {
            throw new ResourceNotFoundException("Không tìm thấy màu ID: " + id);
        }
        colorRepository.deleteById(id);          // FK ON DELETE CASCADE lo phần phụ thuộc
    }


}
