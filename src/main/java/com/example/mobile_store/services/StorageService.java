package com.example.mobile_store.services;

import org.springframework.web.multipart.MultipartFile;

public interface StorageService {
    String upload(MultipartFile file);  // trả về URL /uploads/abc.jpg
}
