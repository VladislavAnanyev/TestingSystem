package com.example.mywebquizengine.controller.web;

import com.example.mywebquizengine.model.User;
import com.example.mywebquizengine.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class GeneralController {

    @Autowired
    private UserService userService;

    @GetMapping("/start")
    public String start() {
        return "singin";
    }

    @PostMapping("/start")
    public String checkMember(@RequestParam String email) {
        userService.sendRegistrationLink(email);
        return "signin";
    }

    @GetMapping(path = "/start/{activationCode}")
    public String activate(Model model, @PathVariable String activationCode) {
        User user = userService.findUserByActivationCode(activationCode);
        model.addAttribute("email", user.getEmail());
        model.addAttribute("code", user.getActivationCode());
        return "reg";
    }

    @GetMapping("/")
    public String redirect() {
        return "redirect:/courses";
    }

    @GetMapping(path = "/test")
    public String test() {
        return "saved_resource";
    }

}
