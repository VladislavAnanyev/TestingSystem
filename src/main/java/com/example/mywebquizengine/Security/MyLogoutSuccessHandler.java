package com.example.mywebquizengine.Security;

import com.example.mywebquizengine.Model.User;
import com.example.mywebquizengine.Repos.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;


@Component
public class MyLogoutSuccessHandler implements LogoutSuccessHandler {


    private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

    @Autowired
    private UserRepository userRepository;

    @Override
    public void onLogoutSuccess(HttpServletRequest request,
                                HttpServletResponse response, Authentication authentication)
            throws IOException, ServletException {

        HttpSession session = request.getSession();
        System.out.println("Логаут");
        if (session != null){
            session.removeAttribute("user");
            session.invalidate();
        }


        /*HttpSession session = request.getSession();
        if (session != null) {
            session.removeAttribute("user");
        }*/
/*
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }


        User user = userRepository.findById(authentication.getName()).get();
        user.setOnline("false");
        userRepository.save(user);
        //System.out.println("ОЛОЛО2");


        redirectStrategy.sendRedirect(request, response, "/singin");*/

        redirectStrategy.sendRedirect(request, response, "/singin");

    }
}
