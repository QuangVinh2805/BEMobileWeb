package com.example.mobile_store.services;

import com.example.mobile_store.models.Color;
import com.example.mobile_store.models.Product;
import com.example.mobile_store.models.ProductImage;
import com.example.mobile_store.repository.ColorRepository;
import com.example.mobile_store.repository.ProductImageRepository;
import com.example.mobile_store.repository.ProductPriceRepository;
import com.example.mobile_store.repository.ProductRepository;
import com.example.mobile_store.request.ProductImageRequest;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

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

    @Autowired
    StorageService storageService;

    public List<ProductImage> getProductImagesByProductAndColor(long productId, String color) {
        return productImageRepository.findByProductIdAndColor_Color(productId, color);
    }

    public List<Color> getColorsByProductId(Long productId) {
        return productPriceRepository.findColorsByProductId(productId);
    }

    @Transactional
    public ProductImage uploadImage(ProductImageRequest req, MultipartFile file) {

        Product product = productRepository.findById(req.getProductId())
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy sản phẩm"));

        Color color = colorRepository.findByColor(req.getColor().trim().toLowerCase());
        if (color == null) throw new RuntimeException("Không tìm thấy màu");

        String url = storageService.upload(file);     // /uploads/uuid_name.jpg

        ProductImage img = new ProductImage();
        img.setProduct(product);
        img.setColor(color);
        img.setImage(url);

        return productImageRepository.save(img);
    }

    /* ---------- UPDATE (đổi file) ---------- */
    @Transactional
    public ProductImage updateImage(Long id, MultipartFile newFile) {
        ProductImage img = productImageRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy ảnh"));

        if (newFile != null && !newFile.isEmpty()) {
            img.setImage(storageService.upload(newFile));  // ghi đè URL mới
        }
        return productImageRepository.save(img);
    }

    /* ---------- DELETE ---------- */
    public void deleteImage(Long id) {
        productImageRepository.deleteById(id);
    }


}
