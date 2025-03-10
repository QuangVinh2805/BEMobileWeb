package com.example.mobile_store.request;

import lombok.*;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProductRequest {
    private Long productId;
    private String productName;
    private String brand;
    private String color;
    private String capacity;
    private String image;

}
