package com.example.mobile_store.controller;


import com.example.mobile_store.models.Color;
import com.example.mobile_store.models.ProductPrice;
import com.example.mobile_store.request.CapacityRequest;
import com.example.mobile_store.request.ColorRequest;
import com.example.mobile_store.request.ProductPriceRequest;
import com.example.mobile_store.request.UpdateCapacityRequest;
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

    @PostMapping("/capacity/create")
    public ResponseEntity<ProductPriceRequest> addCapacity(
            @RequestParam Long productId,
            @RequestBody CapacityRequest request) {
        return ResponseEntity.ok(productPriceService.addCapacityWithColor(productId, request));
    }


    @PutMapping("/capacity/update")
    public ResponseEntity<ProductPriceRequest> updateCapacity(
            @RequestParam Long productId,
            @RequestBody UpdateCapacityRequest request) {
        ProductPriceRequest updated = productPriceService.updateCapacity(productId, request);
        return ResponseEntity.ok(updated);
    }


    @DeleteMapping("/capacity/delete")
    public ResponseEntity<Void> deleteCapacity(
            @RequestParam Long productId,
            @RequestBody CapacityRequest request) {
        productPriceService.deleteCapacity(productId, request);
        return ResponseEntity.noContent().build();
    }




}
