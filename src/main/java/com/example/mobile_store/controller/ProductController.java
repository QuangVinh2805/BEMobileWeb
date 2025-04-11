package com.example.mobile_store.controller;


import com.example.mobile_store.models.Color;
import com.example.mobile_store.models.Product;
import com.example.mobile_store.repository.ProductRepository;
import com.example.mobile_store.request.ProductRequest;
import com.example.mobile_store.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/product")
public class ProductController {
    @Autowired
    ProductService productService;

    @Autowired
    ProductRepository productRepository;

    @RequestMapping(value = "/all", method = RequestMethod.GET)
    public ResponseEntity<List<Product>> listAllProduct(){
        return productService.listAllProduct();
    }

    @GetMapping("/byCategory/{categoryId}")
    public ResponseEntity<?> getProductsByCategory(@PathVariable Long categoryId) {
        List<Product> products = productRepository.findByCategoryId(categoryId);
        return ResponseEntity.ok(products);
    }


    @GetMapping("/byCategoryDetail/{categoryDetailId}")
    public ResponseEntity<?> getProductsByCategoryDetail(@PathVariable Long categoryDetailId) {
        List<Product> products = productRepository.findByCategoryDetailId(categoryDetailId);
        return ResponseEntity.ok(products);
    }

    @PostMapping("/create")
    public ResponseEntity<Product> createProduct(@RequestBody ProductRequest productRequest) {
        return productService.createProduct(productRequest);
    }
    @PutMapping("/update")
    public ResponseEntity<Product> updateProduct(@RequestBody ProductRequest productRequest) {
        return productService.updateProduct(productRequest);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteProduct(@PathVariable("id") long id) {
        return productService.deleteProduct(id);
    }

    @DeleteMapping("/hide/{id}")
    public ResponseEntity<String> hideProduct(@PathVariable("id") long id) {
        return productService.hideProduct(id);
    }

}
