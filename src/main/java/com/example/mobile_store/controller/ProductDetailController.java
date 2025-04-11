package com.example.mobile_store.controller;


import com.example.mobile_store.models.Product;
import com.example.mobile_store.models.ProductDetail;
import com.example.mobile_store.repository.ProductDetailRepository;
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
    @Autowired
    ProductDetailRepository productDetailRepository;

    @GetMapping("/detail")
    public ResponseEntity<List<ProductDetail>> findProductDetailsByProductId(@PathVariable("productId") long productId) {
        List<ProductDetail> productDetails = productDetailRepository.findProductDetailsByProductId(productId);
        if (productDetails.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(productDetails);
    }
}
