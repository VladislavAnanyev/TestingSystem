package com.example.mywebquizengine.security;

import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.unit.DataSize;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.servlet.MultipartConfigElement;

@Configuration
public class MvcConfig implements WebMvcConfigurer {

    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/login").setViewName("singin");
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/static/**", "/img/**", "/video/**",
                                                "/.well-known/pki-validation/**" , "yandex_135f209071de02b1.html",
                                                "googlee45a32e3d6f7edf4.html")
                .addResourceLocations("classpath:/static/", "file:" + System.getProperty("user.dir") + "/img/",
                                                            "file:" + System.getProperty("user.dir") + "/video/",
                                                            "file:" + System.getProperty("user.dir") + "/.well-known/pki-validation/" ,
                                                            "file:" + System.getProperty("user.dir") +  "/yandex_135f209071de02b1.html",
                                                            "file:" + System.getProperty("user.dir") +  "/googlee45a32e3d6f7edf4.html");

        registry.addResourceHandler("swagger-ui.html")
                .addResourceLocations("classpath:/META-INF/resources/");

        registry.addResourceHandler("/webjars/**")
                .addResourceLocations("classpath:/META-INF/resources/webjars/");
        // for Windows
        /*.addResourceLocations("classpath:/static/", "file:C:\\Users\\avlad\\IdeaProjects\\WebQuiz\\img\\",
                "file:C:\\Users\\avlad\\IdeaProjects\\WebQuiz\\video\\", "file:C:\\Users\\avlad\\IdeaProjects\\WebQuiz\\.well-known\\pki-validation\\");*/
    }

    @Bean
    MultipartConfigElement multipartConfigElement() {
        MultipartConfigFactory factory = new MultipartConfigFactory();
        factory.setMaxFileSize(DataSize.ofKilobytes(502400));
        factory.setMaxRequestSize(DataSize.ofKilobytes(502400));
        return factory.createMultipartConfig();
    }

    /*@Bean
    public static ConfigureRedisAction configureRedisAction() {
        return ConfigureRedisAction.NO_OP;
    }*/

}
