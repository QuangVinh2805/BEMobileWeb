package com.example.mobile_store.controller;


import com.example.mobile_store.models.Color;
import com.example.mobile_store.models.ProductPrice;
import com.example.mobile_store.request.CapacityRequest;
import com.example.mobile_store.request.ColorRequest;
import com.example.mobile_store.request.ProductPriceRequest;
import com.example.mobile_store.services.ProductPriceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/product")
public class ProductCapacityController {
    @Autowired
    ProductPriceService productPriceService;

    @GetMapping("/capacity")
    public List<String> getCapacityByProductId(@RequestParam Long productId) {
        return productPriceService.getCapacityByProductId(productId);
    }

    @PostMapping("/capacity/create/noColor")
    public ResponseEntity<ProductPriceRequest> addCapacityOnly(
            @RequestParam Long productId,
            @RequestBody CapacityRequest request) {
        return ResponseEntity.ok(productPriceService.addCapacityOnly(productId, request));
    }


    @PutMapping("/capacity/update")
    public ResponseEntity<ProductPriceRequest> updateCapacityPrice(
            @RequestParam Long productId,
            @RequestParam String capacity,
            @RequestParam String color,
            @RequestBody CapacityRequest request) {
        return ResponseEntity.ok(productPriceService.updateCapacityPrice(productId, color, capacity, request));
    }

    @DeleteMapping("/capacity/delete")
    public ResponseEntity<Void> deleteCapacity(
            @RequestParam Long productId,
            @RequestParam String capacity) {
        productPriceService.deleteCapacity(productId, capacity);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/color/create")
    public ResponseEntity<List<ProductPriceRequest>> addColorToCapacity(
            @RequestParam Long productId,
            @RequestParam String capacity,
            @RequestBody ColorRequest request) {
        return ResponseEntity.ok(productPriceService.addColorToCapacity(productId, capacity, request));
    }

    @PutMapping("color/update")
    public ResponseEntity<ProductPrice> updateColorAndPrice(
            @RequestParam Long productId,
            @RequestParam String capacity,
            @RequestParam String oldColor,
            @RequestBody ColorRequest request) {
        return ResponseEntity.ok(productPriceService.updateColorAndPrice(productId, capacity, oldColor, request));
    }

    @DeleteMapping("/color/delete")
    public ResponseEntity<Void> deleteColorOfProduct(
            @RequestParam Long productId,
            @RequestParam String color) {
        productPriceService.deleteColorOfProduct(productId, color);
        return ResponseEntity.noContent().build();
    }
}
