package com.example.payload.Service;

import com.example.payload.Repository.FileMetadataRepository;
import com.example.payload.model.FileMetadata;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;

@Service
public class FileStorageService {

    @Value("${file.upload-dir}")
    private String uploadDir;

    @Autowired
    private FileMetadataRepository fileMetadataRepository;

    public FileMetadata storeFile(MultipartFile file) throws IOException {
        String filename = StringUtils.cleanPath(file.getOriginalFilename());
        Path uploadPath = Paths.get(uploadDir);
        Files.createDirectories(uploadPath);  // ensure directory exists

        Path filePath = uploadPath.resolve(filename);
        Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

        FileMetadata meta = new FileMetadata();
        meta.setFilename(filename);
        meta.setFilePath(filePath.toString());
        meta.setContentType(file.getContentType());
        meta.setUploadTime(LocalDateTime.now());

        return fileMetadataRepository.save(meta);
    }

    public Resource loadFile(Long id) throws IOException {
        FileMetadata file = fileMetadataRepository.findById(id).orElseThrow();
        Path path = Paths.get(file.getFilePath());
        return new UrlResource(path.toUri());
    }
}

