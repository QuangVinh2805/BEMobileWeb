package com.example.mobile_store.controller;

import com.example.mobile_store.models.Cart;
import com.example.mobile_store.repository.CartRepository;
import com.example.mobile_store.request.CartRequest;
import com.example.mobile_store.services.CartService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cart")
public class CartController {
    @Autowired
    CartService cartService;

    public static Logger logger = LoggerFactory.getLogger(CartController.class);
    @Autowired
    private CartRepository cartRepository;

    @RequestMapping(value = "/all", method = RequestMethod.GET)
    public ResponseEntity<List<Cart>> listAllCart() {
        return cartService.listAllCart();
    }

    @RequestMapping(value = "/getCartByUser", method = RequestMethod.GET)
    public ResponseEntity<List<Cart>> listCartByUser(@RequestParam Long userId) {
        return cartService.listCartByUser(userId);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteCart(@PathVariable("id") long id) {
        return cartService.deleteCart(id);
    }

    @DeleteMapping("/deleteAll")
    public ResponseEntity<HttpStatus> deleteAllCart() {
        try {
            cartRepository.deleteAll();
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/update")
    public ResponseEntity<Cart> updateCart(@RequestBody CartRequest cartRequest) {
        return cartService.updateCart(cartRequest);
    }

    @PostMapping("/create")
    public ResponseEntity<Cart> createCart(@RequestBody CartRequest cartRequest) {
        return cartService.createCart(cartRequest);
    }
}
