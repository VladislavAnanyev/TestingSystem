package com.example.mywebquizengine.Security;

import com.example.mywebquizengine.Model.User;
import com.example.mywebquizengine.Repos.UserRepository;
import com.example.mywebquizengine.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;


@Component("myAuthenticationSuccessHandler")
public class MyAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

    private RequestCache requestCache = new HttpSessionRequestCache();
/*    @Autowired
    LoggedUser loggedUser;*/


    public MyAuthenticationSuccessHandler() {
        super();
        //setUseReferer(true);
    }


    @Autowired
    ActiveUserStore activeUserStore;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {



        //session.setMaxInactiveInterval(10);

        /*if (session != null) {
            LoggedUser user = new LoggedUser(authentication.getName(), loggedUser.getUsers());
            session.setAttribute("user", user);
        }
*/


       /* User user = userRepository.findById(authentication.getName()).get();
        user.setOnline("true");
        userRepository.save(user);

        HttpSession session = request.getSession(true);
        session.setMaxInactiveInterval(10);
        session.setAttribute("user", user.getUsername());
*/
        /*Enumeration<String> headerNames = request.getHeaderNames();
        while (headerNames.hasMoreElements()) {
            String key = headerNames.nextElement();
            System.out.println(key + ": " + request.getHeader(key));

        }*/



        /*if (authentication instanceof OAuth2AuthenticationToken) {
            User user = userService.castToUserFromOauth((OAuth2AuthenticationToken) authentication);

            userService.processCheckIn(user); // save if not exist (registration)
        }*/



        System.out.println("Саксес хендлер");
        HttpSession session = request.getSession(false);
        if (session != null) {

            LoggedUser user = new LoggedUser(authentication.getName(), activeUserStore);
            session.invalidate();
            session = request.getSession(true);
            session.setAttribute("user", user);
            session.setMaxInactiveInterval(60);
        } else {
            LoggedUser user = new LoggedUser(authentication.getName(), activeUserStore);
            //session = request.getSession(true);

            session = request.getSession(true);
            session.setAttribute("user", user);
            session.setMaxInactiveInterval(60);
        }



        //setUseReferer(true);
        //setDefaultTargetUrl("/profile");
        //super.onAuthenticationSuccess(request, response, authentication);




        redirectStrategy.sendRedirect(request, response, "/profile");
        }
}

