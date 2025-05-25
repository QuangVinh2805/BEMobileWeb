package com.example.mobile_store.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class ProductImageUploadForm {
    private Long productId;
    private String color;


    @Schema(type = "string", format = "binary")   // <-- dòng quan trọng
    private MultipartFile file;
}
