package com.tren.linea1_service.service;

import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Path;

@Service

public interface StorageService {
    void init();
    String store(MultipartFile file);
    Path load(String filename);
    Resource loadAsResource(String filename);
    void delete(String filename);
}