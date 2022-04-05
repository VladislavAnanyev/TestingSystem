package com.example.mywebquizengine.Security;
import com.example.mywebquizengine.Service.JWTFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.mapping.GrantedAuthoritiesMapper;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserRequest;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserService;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.oidc.OidcIdToken;
import org.springframework.security.oauth2.core.oidc.OidcUserInfo;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.security.oauth2.core.oidc.user.OidcUserAuthority;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2UserAuthority;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.security.web.authentication.logout.SimpleUrlLogoutSuccessHandler;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;


import javax.sql.DataSource;
import java.util.*;


@Configuration
@EnableWebSecurity(debug=true)
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter  {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Qualifier("userService")
    @Autowired
    protected UserDetailsService userDetailsService;

    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) {
        auth.authenticationProvider(authenticationProvider());
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setPasswordEncoder(passwordEncoder);
        provider.setUserDetailsService(userDetailsService);
        return provider;
    }


    @Configuration
    @Order(1)
    public static class ApiConfigurationAdapter extends
            WebSecurityConfigurerAdapter {

        @Autowired
        private JWTFilter jwtFilter;

        @Override
        protected void configure(HttpSecurity http) throws Exception {

            http.csrf().disable();

            http.antMatcher("/api/**")

                    .authorizeRequests()
                    .antMatchers("/api/register", "/api/jwt", "/img/**",
                            "/api/quizzes", "/api/signin", "/api/googleauth", "/api/signup",
                            "/api/user/check-username", "/api/user/send-change-password-code",
                            "/api/user/verify-password-code", "/api/user/password").permitAll()


                    .anyRequest().authenticated()

                    .and()
                    .logout().logoutUrl("/api/logout").logoutSuccessHandler(new SimpleUrlLogoutSuccessHandler())
                    .permitAll()
                    .and()
                    .headers()
                    .frameOptions()
                    .sameOrigin().and()
                    .requiresChannel()
                    .anyRequest()
                    .requiresSecure().and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

            http.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

        }
    }

    @Order(2)
    @Configuration
    @ServletComponentScan("com.example.mywebquizengine.Security")
    public class FormConfigurationAdapter extends
            WebSecurityConfigurerAdapter {

        @Autowired
        DataSource dataSource;

        @Autowired
        MyAuthenticationSuccessHandler myAuthenticationSuccessHandler;

        @Autowired
        MyLogoutSuccessHandler myLogoutSuccessHandler;

        @Bean
        public String getKey() {
            return "secretkey";
        }

        @Bean
        public SessionRegistry sessionRegistry() {
            return new SessionRegistryImpl();
        }


        @Bean
        public ActiveUserStore activeUserStore(){
            return new ActiveUserStore();
        }

        @Bean
        public PersistentTokenRepository persistentTokenRepository() {
            JdbcTokenRepositoryImpl tokenRepository = new JdbcTokenRepositoryImpl();
            tokenRepository.setDataSource(dataSource);
            return tokenRepository;
        }


        @Override
        public void configure(WebSecurity web) throws Exception {
            web.debug(true).ignoring().antMatchers("/img/**", "/static/**");
        }


        @Override
        protected void configure(HttpSecurity http) throws Exception {

            http.csrf().disable();


            http

                    .authorizeRequests()
                    .antMatchers( "/googlee45a32e3d6f7edf4.html", "/signup", "/activate/*",
                            "/quizzes", "/reg",  "/androidSign", "/ws/**", "/add", "/about/**",
                            "/signin", "/checkyandex", "/h2-console/**", "/.well-known/pki-validation/**",
                            "/update/userinfo/pswrdwithoutauth", "/updatepass/**", "/testm", "/pass/**",
                            "/updatepassword/{activationCode}", "/yandex_135f209071de02b1.html",
                            "/start/{activationCode}", "/password/forget").permitAll()
                    .antMatchers("/swagger-ui/**").hasRole("ADMIN")
                    .anyRequest().authenticated()

                    .and()
                    .formLogin()
                        .loginPage("/signin")

                        .successHandler(myAuthenticationSuccessHandler)


                    .and()
                    .oauth2Login().userInfoEndpoint()
                        .oidcUserService(this.oidcUserService()).userService(this.oAuth2UserService())
                        .userAuthoritiesMapper(this.userAuthoritiesMapper()).and()
                        .defaultSuccessUrl("/loginSuccess").successHandler(myAuthenticationSuccessHandler).permitAll()/*.and().rememberMe().rememberMeParameter("remember-me")
                    //.and().key("secretkey").alwaysRemember(true).rememberMeServices(rememberMeServices())*/

                    .and()
                    .rememberMe()
                        .key("secretkey").alwaysRemember(true).userDetailsService(userDetailsService)
                        .tokenRepository(persistentTokenRepository()).authenticationSuccessHandler(myAuthenticationSuccessHandler)

                    .and()
                    .logout().logoutUrl("/logout").addLogoutHandler(new SecurityContextLogoutHandler())
                    .logoutSuccessHandler(myLogoutSuccessHandler)


                    .permitAll()
                    //.logoutSuccessHandler(myLogoutSuccessHandler)

                    .and()
                    // for h2-console correct view
                    .headers()
                    .frameOptions()
                    .sameOrigin().and()
                    .requiresChannel()
                    /*.anyRequest()
                    .requiresSecure()*/.and()

                    .sessionManagement().maximumSessions(100).sessionRegistry(sessionRegistry())
                    .and().sessionCreationPolicy(SessionCreationPolicy.NEVER).sessionFixation().none();
    }



        private OAuth2UserService<OAuth2UserRequest, OAuth2User>  oAuth2UserService() {


            final DefaultOAuth2UserService delegate = new DefaultOAuth2UserService();

            return userRequest -> {

                OAuth2User defaultOAuth2User = delegate.loadUser(userRequest);

                return new DefaultOAuth2User(defaultOAuth2User.getAuthorities(), defaultOAuth2User.getAttributes(), "login");


            };
        }


        private OAuth2UserService<OidcUserRequest, OidcUser> oidcUserService() {
            final OidcUserService delegate = new OidcUserService();

            return (userRequest) -> {

                OidcUser oidcUser = delegate.loadUser(userRequest);

                Set<GrantedAuthority> mappedAuthorities = new HashSet<>(oidcUser.getAuthorities());

                Map<String, Object> map = new HashMap<>(oidcUser.getIdToken().getClaims());
                map.put("myLogin", oidcUser.getIdToken().getEmail().replace("@gmail.com", ""));

                OidcIdToken oidcIdToken = new OidcIdToken(oidcUser.getIdToken().getTokenValue(), oidcUser.getIssuedAt(),
                        oidcUser.getExpiresAt(), map);

                oidcUser = new DefaultOidcUser(mappedAuthorities, oidcIdToken, oidcUser.getUserInfo(), "myLogin");


                return oidcUser;

            };
        }


        private GrantedAuthoritiesMapper userAuthoritiesMapper() {
            return (authorities) -> {
                Set<GrantedAuthority> mappedAuthorities = new HashSet<>();





                authorities.forEach(authority -> {
                    if (authority instanceof OidcUserAuthority) {
                        OidcUserAuthority oidcUserAuthority = (OidcUserAuthority)authority;

                        OidcIdToken idToken = oidcUserAuthority.getIdToken();
                        OidcUserInfo userInfo = oidcUserAuthority.getUserInfo();

                        // Map the claims found in idToken and/or userInfo
                        // to one or more GrantedAuthority's and add it to mappedAuthorities

                    } else if (authority instanceof OAuth2UserAuthority) {
                        OAuth2UserAuthority oauth2UserAuthority = (OAuth2UserAuthority)authority;

                        Map<String, Object> userAttributes = oauth2UserAuthority.getAttributes();

                        // Map the attributes found in userAttributes
                        // to one or more GrantedAuthority's and add it to mappedAuthorities

                    }
                });

                return mappedAuthorities;
            };
        }


    }
}
