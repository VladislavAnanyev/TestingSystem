package com.example.mywebquizengine.controller.web;

import com.example.mywebquizengine.model.RegistrationRequest;
import com.example.mywebquizengine.model.User;
import com.example.mywebquizengine.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.transaction.Transactional;
import javax.validation.Valid;


@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/signup")
    public String login() {
        return "signup";
    }

    @GetMapping(path = "/authuser")
    @ResponseBody
    public Long getAuthUsername(@AuthenticationPrincipal User authUser) {
        return authUser.getUserId();
    }

    @PostMapping(path = "/signup")
    public String checkIn(@Valid RegistrationRequest request) {
        userService.processCheckIn(request.getActivationCode(), request.getEmail(), request.getFirstName(), request.getLastName(), request.getPassword());
        return "redirect:/signin";
    }

    @PostMapping(path = "/update/userinfo/pswrdwithoutauth", consumes = {"application/json"})
    public void tryToChangePassWithoutAuth(@RequestBody User in) {
        userService.sendCodeForChangePassword(in.getUserId());
        throw new ResponseStatusException(HttpStatus.OK);
    }

    @GetMapping(path = "/updatepass/{changePasswordCode}")
    public String changePasswordPage(Model model, @PathVariable String changePasswordCode) {
        model.addAttribute("user", userService.getUserViaChangePasswordCode(changePasswordCode));
        return "changePassword";
    }

    @GetMapping(path = "/signin")
    public String singin() {
        return "singin";
    }

    @Transactional
    @PutMapping(path = "/update/user/{userId}", consumes = {"application/json"})
//    @PreAuthorize(value = "#principal.name.equals(#userId)")
    public void changeUser(@PathVariable Long userId, @RequestBody User user, @AuthenticationPrincipal User authUser) {
        userService.updateUser(user.getLastName(), user.getFirstName(), userId);
    }

    @GetMapping(path = "/about/{username}")
    public String getInfoAboutUser(Model model, @PathVariable String username, @AuthenticationPrincipal User authUser) {
        if (authUser != null && username.equals(userService.loadUserByUserId(authUser.getUserId()).getUsername())) {
            return "redirect:/profile";
        } else {
            User user = userService.loadUserByUsername(username);
            model.addAttribute("user", user);
            return "user";
        }
    }

    @GetMapping(path = "/password/forget")
    public String getForgetPasswordPage() {
        return "forgetPassword";
    }

    @GetMapping(path = "/testConnection")
    @ResponseBody
    public String testConnection() {
        return "OK";
    }
}
