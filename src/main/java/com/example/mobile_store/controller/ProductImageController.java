package com.example.mobile_store.controller;


import com.example.mobile_store.models.Color;
import com.example.mobile_store.models.ProductImage;
import com.example.mobile_store.services.ProductImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/product/image")
public class ProductImageController {
    @Autowired
    ProductImageService productImageService;

    @GetMapping("/allImage")
    public ResponseEntity<List<ProductImage>> getProductImages(
            @RequestParam("product_id") long productId,
            @RequestParam("color") String color) { // Thay đổi từ color_id sang color
        List<ProductImage> images = productImageService.getProductImagesByProductAndColor(productId, color);
        return ResponseEntity.ok(images);
    }

    @GetMapping("/colors")
    public List<Color> getColorIdsByProductId(@RequestParam Long productId) {
        return productImageService.getColorsByProductId(productId);
    }

}
