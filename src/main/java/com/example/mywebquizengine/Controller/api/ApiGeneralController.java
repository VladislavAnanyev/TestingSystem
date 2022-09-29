package com.example.mywebquizengine.Controller.api;

import com.example.mywebquizengine.Model.User;
import com.example.mywebquizengine.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/api")
public class ApiGeneralController {

    @Autowired
    private UserService userService;

    @PostMapping("/start")
    public void checkMember(@RequestParam String email) {
        userService.sendRegistrationLink(email);
    }

    @GetMapping(path = "/start/{activationCode}")
    public User activate(@PathVariable String activationCode) {
        return userService.findUserByActivationCode(activationCode);
    }

}
