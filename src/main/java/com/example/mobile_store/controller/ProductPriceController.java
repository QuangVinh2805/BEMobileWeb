package com.example.mobile_store.controller;


import com.example.mobile_store.models.ProductPrice;
import com.example.mobile_store.services.ProductPriceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/product")
public class ProductPriceController {
    @Autowired
    ProductPriceService productPriceService;

    @GetMapping("/price")
    public ResponseEntity<?> getProductPrice(
            @RequestParam Long productId,
            @RequestParam String color,
            @RequestParam String capacity) {

        Optional<ProductPrice> productPrice = productPriceService.getPriceByProductIdAndColorAndCapacity(productId, color, capacity);
        return productPrice.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}
