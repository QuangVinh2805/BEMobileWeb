package com.example.mobile_store.services;


import com.example.mobile_store.models.Cart;
import com.example.mobile_store.models.Product;
import com.example.mobile_store.models.ProductPrice;
import com.example.mobile_store.models.User;
import com.example.mobile_store.repository.CartRepository;
import com.example.mobile_store.repository.ProductPriceRepository;
import com.example.mobile_store.repository.ProductRepository;
import com.example.mobile_store.repository.UserRepository;
import com.example.mobile_store.request.CartRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class CartService {
    @Autowired
    CartRepository cartRepository;

    @Autowired
    UserRepository userRepository;


    @Autowired
    ProductRepository productRepository;

    @Autowired
    ProductPriceRepository productPriceRepository;

    public ResponseEntity<List<Cart>> listAllCart() {
        List<Cart> listCart = cartRepository.findAll();
        if (listCart.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(listCart, HttpStatus.OK);
    }

    public void save(Cart cart) {
        cartRepository.save(cart);
    }

    public ResponseEntity<List<Cart>> listCartByUser(Long userId) {
        List<Cart> carts = cartRepository.findByUserId(userId);

        if (carts.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(carts, HttpStatus.OK);
    }

    public ResponseEntity<Cart> createCart(@Valid CartRequest cartRequest) {
        Long userId = cartRequest.getUserId();
        Long productPriceId = cartRequest.getProductPriceId();
        Long productId = cartRequest.getProductId();
        Long quantity = cartRequest.getQuantity();

        if (quantity == null || quantity <= 0) {
            return new ResponseEntity("Quantity phải lớn hơn 0", HttpStatus.BAD_REQUEST);
        }

        // Kiểm tra user
        User user = userRepository.findByUserId(userId);
        if (user == null) {
            return new ResponseEntity("User không tồn tại", HttpStatus.NOT_FOUND);
        }

        // Kiểm tra product
        Product product = productRepository.findByProductId(productId);
        if (product == null) {
            return new ResponseEntity("Product không tồn tại", HttpStatus.NOT_FOUND);
        }

        // Kiểm tra product price
        ProductPrice productPrice = productPriceRepository.findByProductPriceId(productPriceId);
        if (productPrice == null) {
            return new ResponseEntity("ProductPrice không tồn tại", HttpStatus.NOT_FOUND);
        }

        // Kiểm tra tồn kho
        if (product.getQuantity() == null || product.getQuantity() < quantity) {
            return new ResponseEntity("Quá số lượng quy định", HttpStatus.FORBIDDEN);
        }

        // Tìm cart đã tồn tại
        Cart cart = cartRepository.findByUserIdAndProductPriceId(userId, productPriceId);

        if (cart == null) {
            Long totalPrice = productPrice.getPrice() * quantity;

            cart = new Cart();
            cart.setUser(user);
            cart.setProduct(product);
            cart.setProductPrice(productPrice);
            cart.setQuantity(quantity);
            cart.setUnitPrice(productPrice.getPrice()); // ✅ Thêm dòng này
            cart.setTotalPrice(totalPrice);
            cart.setCreatedAt(new Date());
            cart.setUpdatedAt(new Date());
        } else {
            // Cộng thêm vào giỏ
            if (cart.getQuantity() == null) {
                cart.setQuantity(0L);
            }
            cart.setQuantity(cart.getQuantity() + quantity);

            // ✅ Đảm bảo unitPrice không bị null
            if (cart.getUnitPrice() == null) {
                cart.setUnitPrice(productPrice.getPrice());
            }

            cart.setTotalPrice(cart.getUnitPrice() * cart.getQuantity());
            cart.setUpdatedAt(new Date());
        }

        // Kiểm tra lại sau khi cập nhật số lượng trong giỏ
        if (cart.getQuantity() > product.getQuantity()) {
            return new ResponseEntity("Quá số lượng quy định", HttpStatus.FORBIDDEN);
        }

        // Trừ hàng trong kho
        product.setQuantity(product.getQuantity() - quantity);
        productRepository.save(product);

        // Lưu cart
        Cart savedCart = cartRepository.save(cart);
        return new ResponseEntity<>(savedCart, HttpStatus.OK);
    }



    public ResponseEntity<Cart> updateCart(CartRequest cartRequest) {
        Long cartId = cartRequest.getId();
        Long quantity = cartRequest.getQuantity();

        if (quantity == null || quantity <= 0) {
            return new ResponseEntity("Quantity phải lớn hơn 0", HttpStatus.BAD_REQUEST);
        }

        if (cartId == null) {
            return new ResponseEntity("Cart ID not provided", HttpStatus.BAD_REQUEST);
        }

        Cart cart = cartRepository.findById(cartId).orElse(null);

        if (cart == null) {
            return new ResponseEntity("Cart not found", HttpStatus.NOT_FOUND);
        }

        Product product = cart.getProduct();
        ProductPrice productPrice = cart.getProductPrice();

        if (quantity <= 0) {
            cartRepository.delete(cart);
            return new ResponseEntity("Cart deleted", HttpStatus.OK);
        }

        if (quantity > product.getQuantity()) {
            return new ResponseEntity("Quantity exceeds available stock", HttpStatus.BAD_REQUEST);
        }
        product.setQuantity(product.getQuantity() + (cart.getQuantity() - quantity));
        productRepository.save(product);
        cart.setQuantity(quantity);
        Long totalPrice = quantity * productPrice.getPrice();
        cart.setTotalPrice(totalPrice);
        cart.setUpdatedAt(new Date());

        cartRepository.save(cart);
        return new ResponseEntity<>(cart, HttpStatus.OK);
    }

    public ResponseEntity<String> deleteCart(Long id) {
        if (id == null) {
            return new ResponseEntity<>("Cart ID not provided", HttpStatus.BAD_REQUEST);
        }

        Cart cart = cartRepository.findById(id).orElse(null);

        if (cart == null) {
            return new ResponseEntity<>("Cart not found", HttpStatus.NOT_FOUND);
        }

        cartRepository.delete(cart);
        return new ResponseEntity<>("Cart deleted", HttpStatus.OK);
    }
}
