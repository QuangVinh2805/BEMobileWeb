package com.example.mobile_store.services;

import com.example.mobile_store.models.Color;
import com.example.mobile_store.models.Product;
import com.example.mobile_store.models.ProductImage;
import com.example.mobile_store.repository.ColorRepository;
import com.example.mobile_store.repository.ProductImageRepository;
import com.example.mobile_store.repository.ProductPriceRepository;
import com.example.mobile_store.repository.ProductRepository;
import com.example.mobile_store.request.ProductImageRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductImageService {
    @Autowired
    ProductImageRepository productImageRepository;

    @Autowired
    ProductPriceRepository productPriceRepository;

    @Autowired
    ProductRepository productRepository;

    @Autowired
    ColorRepository colorRepository;

    public List<ProductImage> getProductImagesByProductAndColor(long productId, String color) {
        return productImageRepository.findByProductIdAndColor_Color(productId, color);
    }

    public List<Color> getColorsByProductId(Long productId) {
        return productPriceRepository.findColorsByProductId(productId);
    }

    public ProductImage addImage(ProductImageRequest request) {
        Product product = productRepository.findProductById(request.getProductId());
        if (product == null) {
            throw new RuntimeException("Không tìm thấy sản phẩm.");
        }
        Color color = colorRepository.findByColor(request.getColor().trim().toLowerCase());
        if (color == null) {
            throw new RuntimeException("Không tìm thấy màu.");
        }


        ProductImage image = new ProductImage();
        image.setImage(request.getImage());
        image.setProduct(product);
        image.setColor(color);
        return productImageRepository.save(image);
    }

    public ProductImage updateImage(Long id, ProductImageRequest request) {
        ProductImage image = productImageRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy ảnh."));

        if (request.getImage() != null) image.setImage(request.getImage());
        return productImageRepository.save(image);
    }

    public void deleteImage(Long id) {
        productImageRepository.deleteById(id);
    }


}
