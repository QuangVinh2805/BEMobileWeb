package com.example.mobile_store.services;


import com.example.mobile_store.models.Color;
import com.example.mobile_store.models.ProductPrice;
import com.example.mobile_store.repository.ProductPriceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductPriceService {
    @Autowired
    ProductPriceRepository productPriceRepository;

    public List<String> getCapacityByProductId(Long productId) {
        return productPriceRepository.findCapacityByProductId(productId);
    }

    public Optional<ProductPrice> getPriceByProductIdAndColorAndCapacity(Long productId, String color, String capacity) {
        return productPriceRepository.findByProductIdAndColorAndCapacity(productId, color, capacity);
    }
}
