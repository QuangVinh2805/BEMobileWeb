package com.example.mobile_store.controller;


import com.example.mobile_store.models.Color;
import com.example.mobile_store.services.ProductPriceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
}
