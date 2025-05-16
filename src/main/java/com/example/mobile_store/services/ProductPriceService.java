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

    // 1. Thêm dung lượng mới (chưa có màu)
    @Transactional
    public ProductPriceRequest addCapacityOnly(Long productId, CapacityRequest capacityRequest) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy sản phẩm với ID: " + productId));

        String capacity = capacityRequest.getCapacity().trim();

        // Kiểm tra đã có dung lượng chưa có màu chưa
        if (productPriceRepository.findByProduct_IdAndColorAndCapacity(productId, "__default__", capacity).isPresent()) {
            throw new RuntimeException("Dung lượng '" + capacity + "' đã tồn tại cho sản phẩm này (chưa có màu).");
        }

        // Lấy hoặc tạo màu "__default__"
        Color defaultColor = colorRepository.findByColor("__default__");
        if (defaultColor == null) {
            defaultColor = colorRepository.save(new Color());
        }

        // Tạo bản ghi dung lượng với màu mặc định
        ProductPrice newPrice = new ProductPrice();
        newPrice.setProduct(product);
        newPrice.setCapacity(capacity);
        newPrice.setPrice(capacityRequest.getPrice() != null ? capacityRequest.getPrice() : 0L);
        newPrice.setColor("__default__");
        newPrice.setColorId(defaultColor);

        productPriceRepository.save(newPrice);
        return convertToResponse(newPrice);
    }

    // 2. Thêm màu vào một dung lượng đã có
    @Transactional
    public List<ProductPriceRequest> addColorToCapacity(Long productId, String capacity, ColorRequest request) {
        Product product = productRepository.findProductById(productId);
        if (product == null) {
            throw new ResourceNotFoundException("Không tìm thấy sản phẩm với ID: " + productId);
        }

        // Kiểm tra capacity đã tồn tại cho sản phẩm này chưa (bất kỳ màu nào)
        boolean capacityExists = productPriceRepository.existsByProduct_IdAndCapacity(productId, capacity);
        if (!capacityExists) {
            throw new RuntimeException("Dung lượng '" + capacity + "' chưa tồn tại. Vui lòng thêm dung lượng trước.");
        }

        // Kiểm tra hoặc tạo màu mới
        String requestedColor = request.getColor().trim().toLowerCase();
        Color color = colorRepository.findByColor(requestedColor);
        if (color == null) {
            color = new Color();
            color.setColor(requestedColor);
            color = colorRepository.save(color);
        }


        // Kiểm tra trùng lặp
        if (productPriceRepository.findByProduct_IdAndColorAndCapacity(productId, color.getColor(), capacity).isPresent()) {
            throw new RuntimeException("Dung lượng '" + capacity + "' với màu '" + color.getColor() + "' đã tồn tại.");
        }

        // Thêm bản ghi mới
        ProductPrice newPrice = new ProductPrice();
        newPrice.setProduct(product);
        newPrice.setCapacity(capacity);
        newPrice.setColor(color.getColor());
        newPrice.setColorId(color);
        newPrice.setPrice(request.getPrice());
        productPriceRepository.save(newPrice);

        return List.of(convertToResponse(newPrice));
    }




    // 3. Cập nhật giá của 1 màu + dung lượng cụ thể
    public ProductPriceRequest updateCapacityPrice(Long productId, String color, String capacity, CapacityRequest capacityRequest) {
        ProductPrice productPrice = productPriceRepository.findByProduct_IdAndColorAndCapacity(productId, color, capacity)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Không tìm thấy dung lượng " + capacity + " màu " + color + " cho sản phẩm ID: " + productId));
        productPrice.setPrice(capacityRequest.getPrice());
        ProductPrice updatedPrice = productPriceRepository.save(productPrice);
        return convertToResponse(updatedPrice);
    }

    // 4. Xoá toàn bộ một dung lượng (mọi màu + cả "__default__")
    @Transactional
    public void deleteCapacity(Long productId, String capacity) {
        productPriceRepository.deleteByProduct_IdAndCapacity(productId, capacity);
    }

    // 5. Cập nhật màu và giá cho một bản ghi cụ thể
    public ProductPrice updateColorAndPrice(Long productId, String capacity, String oldColor, ColorRequest request) {
        ProductPrice productPrice = productPriceRepository
                .findByProduct_IdAndColorAndCapacity(
                        productId,
                        oldColor.trim().toLowerCase(),
                        capacity.trim()
                )
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Không tìm thấy bản ghi với màu: " + oldColor + ", dung lượng: " + capacity + ", sản phẩm ID: " + productId));

        String newColorStr = request.getColor().trim().toLowerCase();
        Color newColor = colorRepository.findByColor(newColorStr);
        if (newColor == null) {
            newColor = new Color();
            newColor.setColor(newColorStr);
            newColor = colorRepository.save(newColor);
        }

        productPrice.setColor(newColor.getColor());
        productPrice.setColorId(newColor);

        if (request.getPrice() != null) {
            productPrice.setPrice(request.getPrice());
        }

        return productPriceRepository.save(productPrice);
    }

    // 6. Xoá toàn bộ màu khỏi một sản phẩm
    @Transactional
    public void deleteColorOfProduct(Long productId, String color) {
        Color colorEntity = colorRepository.findByColor(color);
        if (colorEntity == null) {
            throw new ResourceNotFoundException("Không tìm thấy màu với tên: " + color);
        }

        productPriceRepository.deleteByProduct_IdAndColor(productId, colorEntity.getColor());

        List<ProductPrice> stillUsed = productPriceRepository.findByColor(colorEntity.getColor());
        if (stillUsed.isEmpty()) {
            colorRepository.deleteById(colorEntity.getId());
        }
    }

    // Convert entity -> response DTO
    private ProductPriceRequest convertToResponse(ProductPrice productPrice) {
        ProductPriceRequest response = new ProductPriceRequest();
        response.setProductPriceId(productPrice.getId());
        response.setProductId(productPrice.getProduct().getId());
        response.setColor(productPrice.getColor());
        response.setCapacity(productPrice.getCapacity());
        response.setPrice(productPrice.getPrice());
        response.setColorId(productPrice.getColorId() != null ? productPrice.getColorId().getId() : null);
        return response;
    }

}
