package com.example.mywebquizengine.Service;

import org.springframework.core.io.Resource;

import java.io.IOException;
import java.io.InputStream;

public interface FileStorageService {

    /**
     * Store file in temporary location
     *
     * @param originalFilename user's filename
     * @return saved unique filename
     */
    String store(InputStream inputStream, String originalFilename) throws IOException;

    Resource loadAsResource(String filename);

}