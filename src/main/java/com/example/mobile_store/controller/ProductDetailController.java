package com.example.mobile_store.controller;


import com.example.mobile_store.models.Product;
import com.example.mobile_store.models.ProductDetail;
import com.example.mobile_store.services.ProductDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/product")
public class ProductDetailController {
    @Autowired
    ProductDetailService productDetailService;


    @GetMapping("/detail")
    public ResponseEntity<?> getProductDetail(@RequestParam Long productId) {
        Optional<ProductDetail> productDetail = productDetailService.getProductDetailByProductId(productId);
        return productDetail.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}
