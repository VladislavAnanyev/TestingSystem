package com.example.mywebquizengine.controller.api;

import com.example.mywebquizengine.model.User;
import com.example.mywebquizengine.model.projection.UserCommonView;
import com.example.mywebquizengine.model.projection.UserView;
import com.example.mywebquizengine.model.userInfo.AuthRequest;
import com.example.mywebquizengine.model.userInfo.AuthResponse;
import com.example.mywebquizengine.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/api")
public class ApiUserController {

    @Autowired
    private UserService userService;

    @GetMapping(path = "/findbyid")
    public UserCommonView getUserById(@RequestParam String username) {
        return userService.getUserView(username);

    }

    @PostMapping(path = "/signin")
    public AuthResponse jwtSignIn(@RequestBody AuthRequest authRequest) {
        return userService.signInViaJwt(authRequest);
    }


    /*@PostMapping(path = "/signup")
    public AuthResponse signup(@Valid @RequestBody User user) {
        userService.processCheckIn(user);
        return userService.getJwtToken(user);
    }
*/

   /* @PostMapping(path = "/googleauth")
    public AuthResponse googleJwt(@RequestBody GoogleToken token) throws GeneralSecurityException, IOException {
        return userService.signinViaGoogleToken(token);
    }*/


    @GetMapping(path = "/authuser")
    public UserView getApiAuthUser(@AuthenticationPrincipal User authUser)  {
        return userService.getAuthUser(authUser.getUserId());
    }

    /*@GetMapping(path = "/user/{username}/profile")
    public ProfileView getProfile(@PathVariable String username) {
        return userService.getUserProfileById(username);
    }*/


    /*@PostMapping(path = "/upload")
    public void uploadPhoto(@RequestParam("file") MultipartFile file, @AuthenticationPrincipal User authUser) {
        userService.uploadPhoto(file, authUser.getUserId());
    }*/


    @PutMapping(path = "/user", consumes={"application/json"})
    public void changeUser(@RequestBody User user,
                           @AuthenticationPrincipal User authUser) {
        userService.updateUser(user.getLastName(), user.getFirstName(), authUser.getUserId());
    }

    @PostMapping(path = "/user/send-change-password-code")
    public void sendChangePasswordCodeWithoutAuth(@RequestParam String username) {
        userService.sendCodeForChangePasswordFromPhone(username);
    }

    @GetMapping(path = "/user/verify-password-code")
    public void verifyChangePasswordCode(@RequestBody User user) {
        userService.getUserViaChangePasswordCodePhoneApi(user.getUsername(), user.getChangePasswordCode());
    }

}
