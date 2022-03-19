package com.example.mywebquizengine.Controller;

import com.example.mywebquizengine.Model.*;
import com.example.mywebquizengine.Model.Projection.UserView;
import com.example.mywebquizengine.Security.ActiveUserStore;
import com.example.mywebquizengine.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.security.Principal;
import java.util.*;


@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private ActiveUserStore activeUserStore;

    @GetMapping(path = "/profile")
    public String getProfile(Model model , @AuthenticationPrincipal Principal principal) {
        UserView user = userService.getAuthUser(principal.getName());
        model.addAttribute("user", user);
        return "profile";
    }

    @GetMapping("/reg")
    public String login() {
        return "reg";
    }

    @GetMapping(path = "/authuser")
    @ResponseBody
    public String getAuthUsername(@AuthenticationPrincipal Principal principal) {
        return principal.getName();
    }

    @PostMapping(path = "/signup")
    public String checkIn(RegistrationRequest request) {
        userService.processCheckIn(request.getActivationCode(), request.getFirstName(), request.getLastName(), request.getPassword());
        return "redirect:/profile";
    }

    @PostMapping(path = "/update/userinfo/password", consumes ={"application/json"} )
    public void tryToChangePassWithAuth(@AuthenticationPrincipal Principal principal) {
        userService.sendCodeForChangePassword(principal.getName());
    }

    @GetMapping("/loggedUsers")
    @ResponseBody
    public ArrayList<String> getLoggedUsers(Locale locale, Model model) {
        return (ArrayList<String>) activeUserStore.getUsers();
    }

    @PostMapping(path = "/update/userinfo/pswrdwithoutauth", consumes ={"application/json"} )
    public void tryToChangePassWithoutAuth(@RequestBody User in) {
        userService.sendCodeForChangePassword(in.getUsername());
    }

    @GetMapping(path = "/updatepass/{changePasswordCode}")
    public String changePasswordPage(@PathVariable String changePasswordCode) {
        userService.getUserViaChangePasswordCode(changePasswordCode);
        return "changePassword";
    }

    @GetMapping(path = "/signin")
    public String singin() {
        return "singin";
    }

    @PutMapping(path = "/updatepass/{changePasswordCode}", consumes ={"application/json"})
    public String changePasswordUsingCode(@RequestBody User in, @PathVariable String changePasswordCode) {
        userService.updatePassword(in, changePasswordCode);
        return "changePassword";
    }

    @Transactional
    @PutMapping(path = "/update/user/{username}", consumes={"application/json"})
    @PreAuthorize(value = "#principal.name.equals(#username)")
    public void changeUser(@PathVariable String username, @RequestBody User user, @AuthenticationPrincipal Principal principal) {
        userService.updateUser(user.getLastName(), user.getFirstName(), username);
    }

    @GetMapping(path = "/about/{username}")
    public String getInfoAboutUser(Model model, @PathVariable String username, @AuthenticationPrincipal Principal principal) {
        if (principal != null && username.equals(userService.loadUserByUsername(principal.getName()).getUsername())) {
            return "redirect:/profile";
        } else {
            User user = userService.loadUserByUsername(username);
            model.addAttribute("user", user);
            return "user";
        }
    }

    @GetMapping(path = "/getUserList")
    @ResponseBody
    public ArrayList<User> getUserList() {
        return userService.getUserList();
    }

    @GetMapping(path = "/testConnection")
    @ResponseBody
    public String testConnection() {
        return "OK";
    }
}
