package com.example.mobile_store.controller;


import com.example.mobile_store.models.Product;
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

    @RequestMapping(value = "/all", method = RequestMethod.GET)
    public ResponseEntity<List<Product>> listAllProduct(){
        return productService.listAllProduct();
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
}
