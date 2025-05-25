package com.example.mobile_store.request;

import lombok.*;
import org.springframework.web.multipart.MultipartFile;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ProductImageRequest {
    private Long productId;
    private String color;

}
