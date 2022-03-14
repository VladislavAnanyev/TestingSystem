package com.example.mywebquizengine.Controller;

import com.example.mywebquizengine.Model.*;
import com.example.mywebquizengine.Model.Projection.UserView;
import com.example.mywebquizengine.Repos.UserRepository;
import com.example.mywebquizengine.Security.ActiveUserStore;
import com.example.mywebquizengine.Service.RequestService;
import com.example.mywebquizengine.Service.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import freemarker.template.TemplateModelException;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.Principal;
import java.util.*;


@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private RequestService requestService;

    @Autowired
    private ActiveUserStore activeUserStore;

    @GetMapping(path = "/profile")
    public String getProfile(Model model , @AuthenticationPrincipal Principal principal) {

        UserView user = userService.getAuthUser(principal.getName());
        model.addAttribute("user", user);

        model.addAttribute("balance", user.getBalance());

        return "profile";
    }

    @GetMapping(path = "/authuser")
    @ResponseBody
    public String getAuthUsername(@AuthenticationPrincipal Principal principal) {
        return principal.getName();
    }


    @GetMapping(path = "/getbalance")
    @ResponseBody
    public Integer getBalance(@AuthenticationPrincipal Principal principal) {
        User user = userService.loadUserByUsername(principal.getName());
        return user.getBalance();
    }


    @GetMapping(path = "/activate/{activationCode}")
    public String activate(@PathVariable String activationCode) {
        userService.activateAccount(activationCode);
        return "singin";
    }

    @PostMapping(path = "/register")
    public String checkIn(@Valid User user) {

        userService.processCheckIn(user, "BASIC");
        return "reg";

    }

    @PostMapping(path = "/update/userinfo/password", consumes ={"application/json"} )
    public void tryToChangePassWithAuth(@AuthenticationPrincipal Principal principal) {

        //User user = userService.loadUserByUsernameProxy(principal.getName());
        userService.sendCodeForChangePassword(principal.getName());

    }

    @GetMapping("/loggedUsers")
    @ResponseBody
    public ArrayList<String> getLoggedUsers(Locale locale, Model model) {

        return (ArrayList<String>) activeUserStore.getUsers();
    }

    @PostMapping(path = "/update/userinfo/pswrdwithoutauth", consumes ={"application/json"} )
    public void tryToChangePassWithoutAuth(@RequestBody User in) {

        //User user = userService.loadUserByUsername(in.getUsername());

        userService.sendCodeForChangePassword(in.getUsername());

    }

    @GetMapping("/loginSuccess")
    public String getLoginInfo(Authentication authentication, Model model) throws TemplateModelException, IOException {
        return "home";
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

    @PostMapping(path = "/checkyandex")
    @ResponseBody
    @Transactional
    public void checkyandex(String notification_type, String operation_id, Number amount, Number withdraw_amount,
                            String currency, String datetime, String sender, Boolean codepro, String label,
                            String sha1_hash, Boolean test_notification, Boolean unaccepted, String lastname,
                            String firstname, String fathersname, String email, String phone, String city,
                            String street, String building, String suite, String flat, String zip) throws NoSuchAlgorithmException {

        userService.processPayment(notification_type, operation_id, amount, withdraw_amount, currency, datetime, sender, codepro, label, sha1_hash, test_notification, unaccepted, lastname, firstname, fathersname, email, phone, city, street, building, suite, flat, zip);

    }



    @GetMapping(path = "/getUserList")
    @ResponseBody
    public ArrayList<User> getUserList() {
        return userService.getUserList();
    }


    @PostMapping(path = "/sendRequest")
    @ResponseBody
    public void sendRequest(@RequestBody Request request, @AuthenticationPrincipal Principal principal) throws JsonProcessingException, ParseException {
        requestService.sendRequest(request, principal);
    }

    @GetMapping(path = "/requests")
    public String getMyRequests(Model model, @AuthenticationPrincipal Principal principal) {

        model.addAttribute("myUsername", principal.getName());
        model.addAttribute("meetings",
                requestService.findAllMyRequestsViaStatus(principal.getName(), "PENDING"));

        return "requests";
    }


    @PostMapping(path = "/acceptRequest")
    @ResponseBody
    public Long acceptRequest(@RequestBody Request request, @AuthenticationPrincipal Principal principal) {
        return requestService.acceptRequest(request.getId(), principal.getName());
    }

    @PostMapping(path = "/rejectRequest")
    @ResponseBody
    public void rejectRequest(@RequestBody Request requestId, @AuthenticationPrincipal Principal principal) {
        requestService.rejectRequest(requestId.getId(), principal.getName());
    }

    @GetMapping(path = "/testConnection")
    @ResponseBody
    public String testConnection() {
        return "OK";
    }


}
