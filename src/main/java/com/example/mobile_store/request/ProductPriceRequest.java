package com.example.mobile_store.request;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Setter
@Getter
public class ProductPriceRequest {
    private Long productPriceId;
    private Long productId;
    private String color;
    private Long price;
    private String capacity;
    private Long colorId;
}
