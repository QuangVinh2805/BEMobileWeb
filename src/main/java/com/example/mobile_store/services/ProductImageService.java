package com.example.mobile_store.services;

import com.example.mobile_store.repository.ProductImageRepository;
import com.example.mobile_store.request.ProductRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductImageService {
    @Autowired
    ProductImageRepository productImageRepository;

    public List<String> getAllProductImage(Long productId, Long productColorId, Long productCapacityId) {
        return productImageRepository.findAllImagesByProductIdAndColorIdAndCapacityId(productId,productColorId, productCapacityId);
    }

    public String getOneProductImage(Long productId, Long productColorId, Long productCapacityId) {
        return productImageRepository.findOneImagesByProductIdAndColorIdAndCapacityId(productId, productColorId, productCapacityId);
    }

    public List<ProductRequest> getProductDetails(Long productId, Long colorId, Long capacityId) {
        return productImageRepository.findProductDetails(productId, colorId, capacityId);
    }
}
