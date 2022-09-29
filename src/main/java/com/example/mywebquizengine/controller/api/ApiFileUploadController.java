package com.example.mywebquizengine.controller.api;

import com.example.mywebquizengine.model.User;
import com.example.mywebquizengine.service.FileSystemStorageService;
import com.example.mywebquizengine.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping(path = "/api")
public class ApiFileUploadController {

    @Autowired
    private UserService userService;

    @Autowired
    private FileSystemStorageService fileStorageService;

    @PostMapping(path = "/upload")
    public void handleFileUpload(@RequestParam("file") MultipartFile file,
                                 @AuthenticationPrincipal User authUser) {
        userService.uploadPhoto(file, authUser.getUserId());
    }

    @GetMapping("/download/{filename}")
    public ResponseEntity<Resource> downloadFile(@PathVariable String filename) {
        Resource resource = fileStorageService.loadAsResource(filename);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename=\"" + resource.getFilename() + "\"")
                .body(resource);
    }

}
