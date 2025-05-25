package com.example.mobile_store.services;


import com.example.mobile_store.models.Color;
import com.example.mobile_store.models.Product;
import com.example.mobile_store.models.ProductPrice;
import com.example.mobile_store.repository.ColorRepository;
import com.example.mobile_store.repository.ProductPriceRepository;
import com.example.mobile_store.repository.ProductRepository;
import com.example.mobile_store.request.CapacityRequest;
import com.example.mobile_store.request.ColorRequest;
import com.example.mobile_store.request.ProductPriceRequest;
import com.example.mobile_store.request.UpdateCapacityRequest;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ProductPriceService {
    @Autowired
    ProductPriceRepository productPriceRepository;

    @Autowired
    ColorRepository colorRepository;

    @Autowired
    ProductRepository productRepository;

    public List<String> getCapacityByProductId(Long productId) {
        return productPriceRepository.findCapacityByProductId(productId);
    }

    public Optional<ProductPrice> getPriceByProductIdAndColorAndCapacity(Long productId, String color, String capacity) {
        return productPriceRepository.findByProduct_IdAndColorAndCapacity(productId, color, capacity);
    }

    @Transactional
    public ProductPriceRequest addCapacityWithColor(Long productId, CapacityRequest request) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy sản phẩm ID: " + productId));

        String capacity = request.getCapacity().trim();
        String colorStr = request.getColor().trim().toLowerCase();

        Color color = colorRepository.findByColor(colorStr);
        if (color == null) {
            color = colorRepository.save(new Color(colorStr));
        }

        if (productPriceRepository.findByProduct_IdAndColorAndCapacity(productId, colorStr, capacity).isPresent()) {
            throw new RuntimeException("Dung lượng và màu này đã tồn tại.");
        }

        ProductPrice price = new ProductPrice();
        price.setProduct(product);
        price.setCapacity(capacity);
        price.setColor(colorStr);
        price.setColorId(color);
        price.setPrice(request.getPrice() != null ? request.getPrice() : 0L);

        return convertToResponse(productPriceRepository.save(price));
    }

    @Transactional
    public ProductPriceRequest updateCapacity(Long productId, UpdateCapacityRequest request) {
        String oldColor = Optional.ofNullable(request.getOldColor())
                .map(String::trim)
                .map(String::toLowerCase)
                .orElseThrow(() -> new IllegalArgumentException("Màu cũ không được để trống"));

        String oldCapacity = Optional.ofNullable(request.getOldCapacity())
                .map(String::trim)
                .orElseThrow(() -> new IllegalArgumentException("Dung lượng cũ không được để trống"));


        String newColor = request.getNewColor().trim().toLowerCase();
        String newCapacity = request.getNewCapacity().trim();

        // Tìm bản ghi cũ
        ProductPrice productPrice = productPriceRepository
                .findByProduct_IdAndColorAndCapacity(productId, oldColor, oldCapacity)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Không tìm thấy bản ghi với dung lượng " + oldCapacity + " và màu " + oldColor));


        // Nếu đổi sang màu hoặc capacity mới → kiểm tra trùng
        boolean isColorChanged = !oldColor.equalsIgnoreCase(newColor);
        boolean isCapacityChanged = !oldCapacity.equalsIgnoreCase(newCapacity);

        if (isColorChanged || isCapacityChanged) {
            Optional<ProductPrice> existing = productPriceRepository
                    .findByProduct_IdAndColorAndCapacity(productId, newColor, newCapacity);

            if (existing.isPresent()) {
                throw new RuntimeException("Đã tồn tại sản phẩm với dung lượng " + newCapacity + " và màu " + newColor);
            }
        }

        // Cập nhật color
        if (isColorChanged) {
            Color newColorEntity = colorRepository.findByColor(newColor);
            if (newColorEntity == null) {
                newColorEntity = colorRepository.save(new Color(newColor));
            }
            productPrice.setColor(newColor);
            productPrice.setColorId(newColorEntity);
        }

        // Cập nhật capacity nếu thay đổi
        if (isCapacityChanged) {
            productPrice.setCapacity(newCapacity);
        }

        // Cập nhật giá nếu có
        if (request.getPrice() != null) {
            productPrice.setPrice(request.getPrice());
        }

        return convertToResponse(productPriceRepository.save(productPrice));
    }




    @Transactional
    public void deleteCapacity(Long productId, CapacityRequest request) {
        String capacity = Optional.ofNullable(request.getCapacity())
                .map(String::trim)
                .orElseThrow(() -> new IllegalArgumentException("Dung lượng không được để trống"));

        // Xóa tất cả productPrice có productId và capacity đó
        List<ProductPrice> productPrices = productPriceRepository.findAllByProduct_IdAndCapacity(productId, capacity);

        if (productPrices.isEmpty()) {
            throw new ResourceNotFoundException("Không tìm thấy bản ghi với dung lượng: " + capacity);
        }

        productPriceRepository.deleteAll(productPrices);
    }



    private ProductPriceRequest convertToResponse(ProductPrice pp) {
        ProductPriceRequest res = new ProductPriceRequest();
        res.setProductPriceId(pp.getId());
        res.setProductId(pp.getProduct().getId());
        res.setCapacity(pp.getCapacity());
        res.setColor(pp.getColor());
        res.setPrice(pp.getPrice());
        res.setColorId(pp.getColorId().getId());
        return res;
    }

}
