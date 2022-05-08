package com.example.mywebquizengine.Controller;

import com.example.mywebquizengine.Model.*;
import com.example.mywebquizengine.Model.Projection.UserView;
import com.example.mywebquizengine.Security.ActiveUserStore;
import com.example.mywebquizengine.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.security.Principal;
import java.util.*;


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
        userService.processCheckIn(request.getActivationCode(), null, request.getFirstName(), request.getLastName(), request.getPassword());
        return "redirect:/signin";
    }

    @PostMapping(path = "/signup/admin")
    public String signUpAdmin(@Valid AdminRegistrationRequest request) {
        userService.processCheckIn(null, request.getEmail(), request.getFirstName(), request.getLastName(), request.getPassword());
        return "redirect:/signin";
    }

    @PostMapping(path = "/update/userinfo/password", consumes ={"application/json"} )
    public void tryToChangePassWithAuth(@AuthenticationPrincipal User authUser) {
        userService.sendCodeForChangePassword(authUser.getUserId());
    }

    /*@GetMapping("/loggedUsers")
    @ResponseBody
    public ArrayList<String> getLoggedUsers(Locale locale, Model model) {
        return (ArrayList<String>) activeUserStore.getUsers();
    }*/

    @PostMapping(path = "/update/userinfo/pswrdwithoutauth", consumes ={"application/json"} )
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

    @PutMapping(path = "/updatepass/{changePasswordCode}", consumes ={"application/json"})
    public String changePasswordUsingCode(@RequestBody String password, @PathVariable String changePasswordCode) {
        userService.updatePassword(password, changePasswordCode);
        return "changePassword";
    }

    @Transactional
    @PutMapping(path = "/update/user/{userId}", consumes={"application/json"})
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
