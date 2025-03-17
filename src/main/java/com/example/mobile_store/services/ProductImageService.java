package com.example.mobile_store.services;

import com.example.mobile_store.models.Color;
import com.example.mobile_store.models.ProductImage;
import com.example.mobile_store.repository.ProductImageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductImageService {
    @Autowired
    ProductImageRepository productImageRepository;

    public List<ProductImage> getProductImagesByProductAndColor(long productId, String color) {
        return productImageRepository.findByProductIdAndColor_Color(productId, color);
    }

    public List<Color> getColorsByProductId(Long productId) {
        return productImageRepository.findColorsByProductId(productId);
    }
}
