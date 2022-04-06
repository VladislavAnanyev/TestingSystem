package com.example.mywebquizengine.Controller;

import com.example.mywebquizengine.Model.User;
import com.example.mywebquizengine.Service.CourseService;
import com.example.mywebquizengine.Service.MessageService;
import com.example.mywebquizengine.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

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

    @GetMapping(path = "/test")
    public String test() {
        return "saved_resource";
    }

}
