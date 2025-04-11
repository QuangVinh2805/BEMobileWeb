package com.example.mobile_store.services;

import com.example.mobile_store.models.Product;
import com.example.mobile_store.models.ProductDetail;
import com.example.mobile_store.models.ProductPrice;
import com.example.mobile_store.repository.ProductDetailRepository;
import com.example.mobile_store.repository.ProductRepository;
import com.example.mobile_store.request.ProductRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {
    @Autowired
    ProductRepository productRepository;
    @Autowired
    private ProductDetailRepository productDetailRepository;

    public ResponseEntity<List<Product>> listAllProduct(){
        List<Product> listProduct= productRepository.findAll();
        if(listProduct.isEmpty()) {
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<List<Product>>(listProduct, HttpStatus.OK);
    }

    public ResponseEntity<Product> createProduct(ProductRequest productRequest){
        if (productRepository.findByProductName(productRequest.getProductName()) != null) {
            return new ResponseEntity("Tên sản phẩm đã tồn tại",HttpStatus.CONFLICT);
        }
        Product product = new Product();
        product.setProductName(productRequest.getProductName());
        product.setPrice(productRequest.getPrice());
        product.setAvatar(productRequest.getAvatar());
        product.setStatus(1L);


        ProductDetail productDetail = new ProductDetail();
        productDetail.setDescription(productRequest.getDescription());
        productDetail.setScreen(productRequest.getScreen());
        productDetail.setFrequency(productRequest.getFrequency());
        productDetail.setResolution(productRequest.getResolution());
        productDetail.setScreenSize(productRequest.getScreenSize());
        productDetail.setScreenBrightness(productRequest.getScreenBrightness());
        productDetail.setRearCameraResolution(productRequest.getRearCameraResolution());
        productDetail.setRearCameraFilm(productRequest.getRearCameraFilm());
        productDetail.setRearCameraFeature(productRequest.getRearCameraFeature());
        productDetail.setFlash(productRequest.getFlash());
        productDetail.setFrontCameraResolution(productRequest.getFrontCameraResolution());
        productDetail.setFrontCameraFilm(productRequest.getFrontCameraFilm());
        productDetail.setFrontCameraFeature(productRequest.getFrontCameraFeature());
        productDetail.setMicroprocessor(productRequest.getMicroprocessor());
        productDetail.setCpuSpeed(productRequest.getCpuSpeed());
        productDetail.setGraphicsProcessor(productRequest.getGraphicsProcessor());
        productDetail.setOperatingSystem(productRequest.getOperatingSystem());
        productDetail.setExternalMemoryCard(productRequest.getExternalMemoryCard());
        productDetail.setRam(productRequest.getRam());
        productDetail.setNfc(productRequest.getNfc());
        productDetail.setNetwork(productRequest.getNetwork());
        productDetail.setSimSlot(productRequest.getSimSlot());
        productDetail.setWifi(productRequest.getWifi());
        productDetail.setBluetooth(productRequest.getBluetooth());
        productDetail.setJackEarphone(productRequest.getJackEarphone());
        productDetail.setCharger(productRequest.getCharger());
        productDetail.setSensor(productRequest.getSensor());
        productDetail.setSize(productRequest.getSize());
        productDetail.setWeight(productRequest.getWeight());
        productDetail.setMaterial(productRequest.getMaterial());
        productDetail.setDesign(productRequest.getDesign());
        productDetail.setBatteryCapacity(productRequest.getBatteryCapacityDetail());
        productDetail.setBatteryTechnology(productRequest.getBatteryTechnology());
        productDetail.setBatteryType(productRequest.getBatteryType());
        productDetail.setMaximumCharge(productRequest.getMaximumCharge());
        productDetail.setSpecialFeatures(productRequest.getSpecialFeatures());
        productDetail.setSecurity(productRequest.getSecurity());
        productDetail.setResistant(productRequest.getResistant());
        productDetail.setLaunchTime(productRequest.getLaunchTime());


        Product savedProduct = productRepository.save(product);
        productDetail.setProduct(savedProduct);
        productDetailRepository.save(productDetail);

        return new ResponseEntity<>(savedProduct, HttpStatus.CREATED);
    }

    public ResponseEntity<Product> updateProduct(ProductRequest productRequest) {
        Long productId = productRepository.findIdByProductId(productRequest.getId());
        if (productId == null) {
            String message = "product id " + productId + " not found";
            System.out.println(message);
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }

        Optional<Product> optionalProduct = productRepository.findById(productRequest.getId());
        ProductDetail productDetail = productDetailRepository.findByProductId(productId);

        if (optionalProduct.isEmpty() || productDetail == null) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }

        Product product = optionalProduct.get();

        // Cập nhật các trường của Product nếu giá trị không phải null
        if (productRequest.getProductName() != null) {
            product.setProductName(productRequest.getProductName());
        }
        if (productRequest.getAvatar() != null) {
            product.setAvatar(productRequest.getAvatar());
        }
        if (productRequest.getStatus() != null) {
            product.setStatus(productRequest.getStatus());
        }
        if (productRequest.getPrice() != null) {
            product.setPrice(productRequest.getPrice());
        }
        if (productRequest.getMicroprocessor() != null) {
            product.setMicroprocessor(productRequest.getMicroprocessor());
        }
        if (productRequest.getBatteryCapacity() != null) {
            product.setBatteryCapacity(productRequest.getBatteryCapacity());
        }
        if (productRequest.getRam() != null) {
            product.setRam(productRequest.getRam());
        }

        // Cập nhật các trường của ProductDetail nếu giá trị không phải null
        if (productRequest.getDescription() != null) {
            productDetail.setDescription(productRequest.getDescription());
        }
        if (productRequest.getScreen() != null) {
            productDetail.setScreen(productRequest.getScreen());
        }
        if (productRequest.getFrequency() != null) {
            productDetail.setFrequency(productRequest.getFrequency());
        }
        if (productRequest.getResolution() != null) {
            productDetail.setResolution(productRequest.getResolution());
        }
        if (productRequest.getScreenSize() != null) {
            productDetail.setScreenSize(productRequest.getScreenSize());
        }
        if (productRequest.getScreenBrightness() != null) {
            productDetail.setScreenBrightness(productRequest.getScreenBrightness());
        }
        if (productRequest.getRearCameraResolution() != null) {
            productDetail.setRearCameraResolution(productRequest.getRearCameraResolution());
        }
        if (productRequest.getRearCameraFilm() != null) {
            productDetail.setRearCameraFilm(productRequest.getRearCameraFilm());
        }
        if (productRequest.getRearCameraFeature() != null) {
            productDetail.setRearCameraFeature(productRequest.getRearCameraFeature());
        }
        if (productRequest.getFlash() != null) {
            productDetail.setFlash(productRequest.getFlash());
        }
        if (productRequest.getFrontCameraResolution() != null) {
            productDetail.setFrontCameraResolution(productRequest.getFrontCameraResolution());
        }
        if (productRequest.getFrontCameraFilm() != null) {
            productDetail.setFrontCameraFilm(productRequest.getFrontCameraFilm());
        }
        if (productRequest.getFrontCameraFeature() != null) {
            productDetail.setFrontCameraFeature(productRequest.getFrontCameraFeature());
        }
        if (productRequest.getCpuSpeed() != null) {
            productDetail.setCpuSpeed(productRequest.getCpuSpeed());
        }
        if (productRequest.getGraphicsProcessor() != null) {
            productDetail.setGraphicsProcessor(productRequest.getGraphicsProcessor());
        }
        if (productRequest.getOperatingSystem() != null) {
            productDetail.setOperatingSystem(productRequest.getOperatingSystem());
        }
        if (productRequest.getExternalMemoryCard() != null) {
            productDetail.setExternalMemoryCard(productRequest.getExternalMemoryCard());
        }
        if (productRequest.getNfc() != null) {
            productDetail.setNfc(productRequest.getNfc());
        }
        if (productRequest.getNetwork() != null) {
            productDetail.setNetwork(productRequest.getNetwork());
        }
        if (productRequest.getSimSlot() != null) {
            productDetail.setSimSlot(productRequest.getSimSlot());
        }
        if (productRequest.getWifi() != null) {
            productDetail.setWifi(productRequest.getWifi());
        }
        if (productRequest.getPositioning() != null) {
            productDetail.setPositioning(productRequest.getPositioning());
        }
        if (productRequest.getBluetooth() != null) {
            productDetail.setBluetooth(productRequest.getBluetooth());
        }
        if (productRequest.getJackEarphone() != null) {
            productDetail.setJackEarphone(productRequest.getJackEarphone());
        }
        if (productRequest.getCharger() != null) {
            productDetail.setCharger(productRequest.getCharger());
        }
        if (productRequest.getSensor() != null) {
            productDetail.setSensor(productRequest.getSensor());
        }
        if (productRequest.getSize() != null) {
            productDetail.setSize(productRequest.getSize());
        }
        if (productRequest.getWeight() != null) {
            productDetail.setWeight(productRequest.getWeight());
        }
        if (productRequest.getMaterial() != null) {
            productDetail.setMaterial(productRequest.getMaterial());
        }
        if (productRequest.getDesign() != null) {
            productDetail.setDesign(productRequest.getDesign());
        }
        if (productRequest.getBatteryTechnology() != null) {
            productDetail.setBatteryTechnology(productRequest.getBatteryTechnology());
        }
        if (productRequest.getBatteryType() != null) {
            productDetail.setBatteryType(productRequest.getBatteryType());
        }
        if (productRequest.getMaximumCharge() != null) {
            productDetail.setMaximumCharge(productRequest.getMaximumCharge());
        }
        if (productRequest.getSpecialFeatures() != null) {
            productDetail.setSpecialFeatures(productRequest.getSpecialFeatures());
        }
        if (productRequest.getSecurity() != null) {
            productDetail.setSecurity(productRequest.getSecurity());
        }
        if (productRequest.getResistant() != null) {
            productDetail.setResistant(productRequest.getResistant());
        }
        if (productRequest.getLaunchTime() != null) {
            productDetail.setLaunchTime(productRequest.getLaunchTime());
        }

        // Lưu sản phẩm đã cập nhật vào database
        productRepository.save(product);
        productDetailRepository.save(productDetail);

        return new ResponseEntity<>(product, HttpStatus.OK);
    }

    public ResponseEntity<String> deleteProduct(Long id) {
        Long productId = productRepository.findIdByProductId(id);
        if (productId == null) {
            String message = "product id not found";
            System.out.println(message + productId);
            return new ResponseEntity(message, HttpStatus.FORBIDDEN);

        }
        productRepository.deleteById(id);
//        ProductDetail productDetail = productDetailRepository.findByProductId(productId);
//        if (productDetail.getStatus() == 0L) {
//            productDetail.setStatus(1L);
//        } else {
//            productDetail.setStatus(0L);
//        }
//
//        productDetailRepository.save(productDetail);
////        Product product = productRepository.findByProductId(productId);
//        if (product.getStatus() == 0L) {
//            product.setStatus(1L);
//        } else {
//            product.setStatus(0L);
//        }
//        productRepository.save(product);
        return new ResponseEntity(HttpStatus.OK);
    }


    public ResponseEntity<String> hideProduct(Long id) {
        Long productId = productRepository.findIdByProductId(id);
        if (productId == null) {
            String message = "Product ID not found";
            System.out.println(message);
            return new ResponseEntity<>(message, HttpStatus.FORBIDDEN);
        }
        Product product = productRepository.findByProductId(productId);

        if (product == null) {
            return new ResponseEntity<>("Product not found after deletion", HttpStatus.NOT_FOUND);
        }

        if (product.getStatus() == 0L) {
            product.setStatus(1L);
        } else {
            product.setStatus(0L);
        }

        productRepository.save(product);
        return new ResponseEntity<>(HttpStatus.OK);
    }


//    public List<Product> findProductByCategoryId(Long categoryId) {
//        return productRepository.findByCategoryId(categoryId);
//    }
//
//    public List<Product> findProductByCategoryDetailId(Long categoryDetailId) {
//        return productRepository.findByCategoryDetailId(categoryDetailId);
//    }

}
