package com.example.mobile_store.services;

import com.example.mobile_store.models.Product;
import com.example.mobile_store.models.ProductDetail;
import com.example.mobile_store.repository.ProductDetailRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductDetailService {
    @Autowired
    ProductDetailRepository productDetailRepository;

    public Optional<ProductDetail> getProductDetailByProductId(Long productId) {
        return productDetailRepository.findByProductId(productId);
    }
}
