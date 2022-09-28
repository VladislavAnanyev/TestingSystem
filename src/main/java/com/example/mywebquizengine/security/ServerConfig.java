package com.example.mywebquizengine.security;

import org.springframework.context.annotation.Configuration;

@Configuration
public class ServerConfig {

    /*@Bean
    public ServletListenerRegistrationBean<HttpSessionListener> listenerRegistrationBean() {
        ServletListenerRegistrationBean<HttpSessionListener> bean =
                new ServletListenerRegistrationBean<>();
        bean.setListener(new SessionEventListener());
        return bean;
    }*/

    /*@Bean
    public ServletWebServerFactory servletContainer() {
        TomcatServletWebServerFactory tomcat = new TomcatServletWebServerFactory() {
            @Override
            protected void postProcessContext(Context context) {
                SecurityConstraint securityConstraint = new SecurityConstraint();
                securityConstraint.setUserConstraint("CONFIDENTIAL");
                SecurityCollection collection = new SecurityCollection();
                collection.addPattern("/*");
                securityConstraint.addCollection(collection);
                context.addConstraint(securityConstraint);
            }
        };
        tomcat.addAdditionalTomcatConnectors(getHttpConnector());
        return tomcat;
    }*/

    /*private Connector getHttpConnector() {
        Connector connector = new Connector(TomcatServletWebServerFactory.DEFAULT_PROTOCOL);
        connector.setScheme("http");
        connector.setPort(80);
        connector.setSecure(false);
        connector.setRedirectPort(443);
        return connector;
    }*/
}