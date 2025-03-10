package com.example.mobile_store.controller;


import com.example.mobile_store.request.ProductRequest;
import com.example.mobile_store.services.ProductImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/product/image")
public class ProductImageController {
    @Autowired
    ProductImageService productImageService;

    @GetMapping("/allImage")
    public ResponseEntity<List<String>> getAllProductImage(@RequestParam Long productId,
                                                        @RequestParam Long productColorId,
                                                        @RequestParam Long productCapacityId) {
        List<String> images = productImageService.getAllProductImage(productId, productColorId, productCapacityId);
        return ResponseEntity.ok(images);
    }

    @GetMapping("/oneImage")
    public ResponseEntity<String> getOneProductImage(
            @RequestParam Long productId,
            @RequestParam Long productColorId,
            @RequestParam Long productCapacityId) {
        String image = productImageService.getOneProductImage(productId, productColorId, productCapacityId);
        return ResponseEntity.ok(image);
    }

    @GetMapping("/details")
    public ResponseEntity<List<ProductRequest>> getProductDetails(
            @RequestParam Long productId,
            @RequestParam Long colorId,
            @RequestParam Long capacityId) {

        List<ProductRequest> result = productImageService.getProductDetails(productId, colorId, capacityId);
        return ResponseEntity.ok(result);
    }
}
