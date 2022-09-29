package com.example.mywebquizengine.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;

@Service
public class FileSystemStorageService implements FileStorageService {

    private final Path location;

    public FileSystemStorageService(@Value("${files.store}") String permanentPath) {
        this.location = Paths.get(permanentPath);
    }

    @Override
    public String store(InputStream inputStream, String originalFilename) throws IOException {
        // generate unique filename (name + extension)
        String filename = UUID.randomUUID() + originalFilename.substring(originalFilename.lastIndexOf("."));
        filename = StringUtils.cleanPath(filename);
        Files.copy(inputStream, this.location.resolve(filename), REPLACE_EXISTING);
        return filename;
    }

    private Path loadFromLocation(String filename) {
        return location.resolve(filename);
    }

    @Override
    public Resource loadAsResource(String filename) {
        try {
            Path file = loadFromLocation(filename);
            Resource resource = new UrlResource(file.toUri());
            if (resource.exists() || resource.isReadable()) {
                return resource;
            } else {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Could not read file: " + filename);
            }
        } catch (MalformedURLException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Could not read file: " + filename, e);
        }
    }
}

