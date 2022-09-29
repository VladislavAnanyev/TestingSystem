package com.example.mywebquizengine.Controller;

import com.example.mywebquizengine.Model.User;
import com.example.mywebquizengine.Service.FileSystemStorageService;
import com.example.mywebquizengine.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.security.Principal;


@Controller
public class FileUploadController {

    @Autowired
    private UserService userService;

    @Autowired
    private FileSystemStorageService fileStorageService;

    @PostMapping(path = "/upload")
    public String handleFileUpload(Model model, @RequestParam("file") MultipartFile file,
                                   @AuthenticationPrincipal User authUser) {
        userService.uploadPhoto(file, authUser.getUserId());
        User userLogin = userService.loadUserByUserIdProxy(authUser.getUserId());
        model.addAttribute("user", userLogin);
        return "profile";
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
