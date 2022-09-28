package com.example.mywebquizengine.security;

import com.example.mywebquizengine.service.JWTFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.security.web.authentication.logout.SimpleUrlLogoutSuccessHandler;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;

import javax.sql.DataSource;


@Configuration
@EnableWebSecurity(debug = true)
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Qualifier("userService")
    @Autowired
    protected UserDetailsService userDetailsService;
    @Autowired
    private PasswordEncoder passwordEncoder;

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
        public ActiveUserStore activeUserStore() {
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
                    .antMatchers("/googlee45a32e3d6f7edf4.html", "/signup", "/signup/admin", "/activate/*",
                            "/quizzes", "/reg", "/androidSign", "/ws/**", "/add", "/about/**",
                            "/signin", "/checkyandex", "/h2-console/**", "/.well-known/pki-validation/**",
                            "/update/userinfo/pswrdwithoutauth", "/updatepass/**", "/testm", "/pass/**",
                            "/updatepassword/{activationCode}", "/yandex_135f209071de02b1.html",
                            "/start/{activationCode}", "/password/forget", "/videos/**", "/video/**").permitAll()
                    .antMatchers("/swagger-ui/**").hasRole("ADMIN")
                    .anyRequest().authenticated()

                    .and()
                    .formLogin()
                    .loginPage("/signin")

                    .successHandler(myAuthenticationSuccessHandler)

                    .and()
                    .rememberMe()
                    .key("secretkey").alwaysRemember(true).userDetailsService(userDetailsService)
                    .tokenRepository(persistentTokenRepository()).authenticationSuccessHandler(myAuthenticationSuccessHandler)

                    .and()
                    .logout().logoutUrl("/logout").addLogoutHandler(new SecurityContextLogoutHandler())
                    .logoutSuccessHandler(myLogoutSuccessHandler)
                    .permitAll()
                    .and()
                    .headers()
                    .frameOptions()
                    .sameOrigin().and()
                    .requiresChannel()
                    .and()
                    .sessionManagement().maximumSessions(100).sessionRegistry(sessionRegistry())
                    .and().sessionCreationPolicy(SessionCreationPolicy.NEVER).sessionFixation().none();
        }


    }
}
