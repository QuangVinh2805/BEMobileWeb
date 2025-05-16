package com.example.mobile_store.controller;


import com.example.mobile_store.models.Color;
import com.example.mobile_store.models.ProductImage;
import com.example.mobile_store.models.ProductPrice;
import com.example.mobile_store.request.ColorRequest;
import com.example.mobile_store.request.ProductImageRequest;
import com.example.mobile_store.request.ProductPriceRequest;
import com.example.mobile_store.services.ProductImageService;
import com.example.mobile_store.services.ProductPriceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/product/image")
public class ProductImageController {
    @Autowired
    ProductImageService productImageService;

    @Autowired
    ProductPriceService productPriceService;

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

    @PostMapping("/create")
    public ResponseEntity<ProductImage> addImage(@RequestBody ProductImageRequest request) {
        return ResponseEntity.ok(productImageService.addImage(request));
    }

    @PutMapping("/update")
    public ResponseEntity<ProductImage> updateImage(@RequestParam Long id, @RequestBody ProductImageRequest request) {
        return ResponseEntity.ok(productImageService.updateImage(id, request));
    }

    @DeleteMapping("/delete")
    public ResponseEntity<Void> deleteImage(@RequestParam Long id) {
        productImageService.deleteImage(id);
        return ResponseEntity.noContent().build();
    }




}
