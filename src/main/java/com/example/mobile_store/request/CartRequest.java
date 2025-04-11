package com.example.mobile_store.request;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CartRequest {
    private Long id;

    private Long productPriceId;

    private Long productId;

    private Long userId;

    private Long quantity;
}
