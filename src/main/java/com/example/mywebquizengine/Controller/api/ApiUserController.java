package com.example.mywebquizengine.Controller.api;

import com.example.mywebquizengine.Model.Photo;
import com.example.mywebquizengine.Model.Projection.ProfileView;
import com.example.mywebquizengine.Model.Projection.UserCommonView;
import com.example.mywebquizengine.Model.Projection.UserView;
import com.example.mywebquizengine.Model.User;
import com.example.mywebquizengine.Model.UserInfo.AuthRequest;
import com.example.mywebquizengine.Model.UserInfo.AuthResponse;
import com.example.mywebquizengine.Model.UserInfo.GoogleToken;
import com.example.mywebquizengine.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping(path = "/api")
public class ApiUserController {

    @Autowired
    private UserService userService;


    @GetMapping(path = "/friends")
    public List<UserCommonView> getFriends(@AuthenticationPrincipal Principal principal) {
        return userService.findMyFriends(principal);
    }

    @GetMapping(path = "/findbyid")
    public UserCommonView getUserById(@RequestParam String username) {
        return userService.getUserView(username);

    }


    @PostMapping(path = "/signin")
    public AuthResponse jwtSignIn(@RequestBody AuthRequest authRequest) {
        return userService.signInViaJwt(authRequest);
    }


    @PostMapping(path = "/signup")
    public AuthResponse signup(@Valid @RequestBody User user) {
        userService.processCheckIn(user, "BASIC");
        return userService.getJwtToken(user);
    }


    @PostMapping(path = "/googleauth")
    public AuthResponse googleJwt(@RequestBody GoogleToken token) throws GeneralSecurityException, IOException {
        return userService.signinViaGoogleToken(token);
    }


    @GetMapping(path = "/authuser")
    public UserView getApiAuthUser(@AuthenticationPrincipal Principal principal)  {
            return userService.getAuthUser(principal.getName());
    }

    @GetMapping(path = "/user/{username}/profile")
    public ProfileView getProfile(@PathVariable String username) {
        return userService.getUserProfileById(username);
    }


    @PostMapping(path = "/upload")
    public void uploadPhoto(@RequestParam("file") MultipartFile file, @AuthenticationPrincipal Principal principal) {
        userService.uploadPhoto(file, principal.getName());
    }


    @PutMapping(path = "/user", consumes={"application/json"})
    public void changeUser(@RequestBody User user,
                           @AuthenticationPrincipal Principal principal) {
        userService.updateUser(user.getLastName(), user.getFirstName(), principal.getName());
    }

    @PostMapping(path = "/user/send-change-password-code")
    public void sendChangePasswordCodeWithoutAuth(@RequestParam String username) {
        userService.sendCodeForChangePasswordFromPhone(username);
    }

    /*@PostMapping(path = "/user/send-change-password-code")
    public void sendChangePasswordCodeWithAuth(@AuthenticationPrincipal Principal principal) {
        userService.sendCodeForChangePasswordFromPhone(principal.getName());
    }*/

    @PutMapping(path = "/user/password")
    public void changePassword(@RequestBody User user) {
        userService.updatePassword(user, user.getChangePasswordCode());
    }

    @GetMapping(path = "/user/verify-password-code")
    public void verifyChangePasswordCode(@RequestBody User user) {
        userService.getUserViaChangePasswordCodePhoneApi(user.getUsername(), user.getChangePasswordCode());
    }

    @GetMapping(path = "/user/check-username")
    public Boolean checkExistUser(@RequestParam String username) {
        return userService.checkForExistUser(username);
    }

    @PostMapping(path = "/user/swap-photo")
    public void swapPhoto(@AuthenticationPrincipal Principal principal, @RequestBody Photo photo) {
        userService.swapPhoto(photo, principal.getName());
    }
}
