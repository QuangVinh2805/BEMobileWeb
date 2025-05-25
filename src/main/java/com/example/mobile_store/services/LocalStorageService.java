package com.example.mobile_store.services;


import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

@Service
public class LocalStorageService implements StorageService {

    private final Path root = Paths.get("uploads");  // thư mục gốc

    @Override
    public String upload(MultipartFile file) {
        try {
            if (!Files.exists(root)) Files.createDirectories(root);

            String filename = UUID.randomUUID() + "_" +
                    StringUtils.cleanPath(file.getOriginalFilename());

            Path dest = root.resolve(filename);
            Files.copy(file.getInputStream(), dest, StandardCopyOption.REPLACE_EXISTING);

            return "/uploads/" + filename;            // URL truy cập ảnh
        } catch (IOException e) {
            throw new RuntimeException("Lưu file thất bại", e);
        }
    }
}
