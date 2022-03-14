package com.example.mywebquizengine.Controller;

import com.example.mywebquizengine.Model.User;
import com.example.mywebquizengine.Service.UserService;
import io.micrometer.core.instrument.util.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.charset.Charset;
import java.security.Principal;
import java.util.Random;
import java.util.UUID;


@Controller
public class FileUploadController {

    @Autowired
    private UserService userService;

    @PostMapping(path = "/upload")
    public String handleFileUpload(Model model, @RequestParam("file") MultipartFile file,
                                   @AuthenticationPrincipal Principal principal) {

        userService.uploadPhoto(file, principal.getName());

        User userLogin = userService.loadUserByUsernameProxy(principal.getName());
        //userLogin.setAvatar("https://" + hostname + "/img/" + uuid + ".jpg");
        model.addAttribute("user", userLogin);
        return "profile";

    }



}
