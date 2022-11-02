package com.lptech.lp_projectdemo.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class FileStorageService {
    private final Path root = Paths.get("file-upload");
    public void init() throws IOException {
        try {
            Files.createDirectory(root);
        }
        catch (IOException e){
            throw new IOException("Không khởi tạo được thư mục chứa file");
        }
    }

    public void save(MultipartFile file) throws IOException {
        try {
            Files.copy(file.getInputStream(),this.root.resolve(file.getOriginalFilename()));
        } catch (IOException e) {
            throw new IOException("Không lưu được file "+file.getName());
        }
    }

}
