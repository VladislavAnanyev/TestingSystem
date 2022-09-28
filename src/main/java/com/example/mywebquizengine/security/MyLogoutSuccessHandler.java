package com.example.mywebquizengine.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.logout.CookieClearingLogoutHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;


@Component
public class MyLogoutSuccessHandler implements LogoutSuccessHandler {

    private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

    @Override
    public void onLogoutSuccess(HttpServletRequest request,
                                HttpServletResponse response, Authentication authentication)
            throws IOException {

        HttpSession session = request.getSession();
        System.out.println("Логаут");
        if (session != null) {
            session.removeAttribute("user");
            session.invalidate();
        }

        CookieClearingLogoutHandler cookieClearingLogoutHandler =
                new CookieClearingLogoutHandler("remember-me", "JSESSIONID");

        cookieClearingLogoutHandler.logout(request, response, authentication);

        redirectStrategy.sendRedirect(request, response, "/signin");

    }
}
