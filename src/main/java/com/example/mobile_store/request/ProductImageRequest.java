package com.example.mobile_store.request;

import lombok.Data;

@Data
public class ProductImageRequest {
    private String image;
    private Long productId;
    private String color;
}
