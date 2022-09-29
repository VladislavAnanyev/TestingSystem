package com.example.mywebquizengine.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.converter.DefaultContentTypeResolver;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.converter.MessageConverter;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.messaging.simp.stomp.StompReactorNettyCodec;
import org.springframework.messaging.tcp.reactor.ReactorNettyTcpClient;
import org.springframework.util.MimeTypeUtils;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;
import reactor.netty.tcp.SslProvider;

import java.util.List;


@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {


    @Value("${hostname}")
    private String hostname;

    @Value("${rabbitLogin}")
    private String login;

    @Value("${rabbitPassword}")
    private String password;

/*
    Конфигурирует брокер сообщений в памяти с одним адресом с префиксом /user для отправки и получения сообщений.
    Адреса с префиксом /app предназначены для сообщений, обрабатываемых методами с аннотацией @MessageMapping
     *//*

    */
@Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        //config.enableSimpleBroker( "/topic");
        //config.setApplicationDestinationPrefixes("/app");
                    //config.setUserDestinationPrefix("/topic");
    registry.setApplicationDestinationPrefixes("/app");
    registry.enableStompBrokerRelay("/topic")
            .setRelayHost(hostname)
            .setRelayPort(61613)
            .setClientLogin(login)
            .setClientPasscode(password)
            .setSystemLogin(login)
            .setSystemPasscode(password);


    /*ReactorNettyTcpClient<byte[]> client = new ReactorNettyTcpClient<>(tcpClient -> tcpClient
            //.host("localhost")
            .port(61614)
            .secure(SslProvider.defaultClientProvider()), new StompReactorNettyCodec());

    registry.setApplicationDestinationPrefixes("/app");
    registry.enableStompBrokerRelay("/queue", "/topic")
            .setAutoStartup(true)
            .setSystemLogin("guest")
            .setSystemPasscode("guest")
            .setClientLogin("guest")
            .setClientPasscode("guest")
            .setTcpClient(client);*/

    }



/*
    Регистрирация конечной точки STOMP /ws.
    Эта конечная точка будет использоваться клиентами для подключения к STOMP-серверу.
    Здесь также включается резервный SockJS, который будет использоваться, если WebSocket будет недоступен.
     *//*

    */
@Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry
                .addEndpoint("/ws")
                .setAllowedOrigins("/*")
                .withSockJS();
    }



/*
    Конвертер JSON, который используется Spring для преобразования сообщений из/в JSON.
     *//*

    */
@Override
    public boolean configureMessageConverters(List<MessageConverter> messageConverters) {
        DefaultContentTypeResolver resolver = new DefaultContentTypeResolver();
        resolver.setDefaultMimeType(MimeTypeUtils.APPLICATION_JSON);
        MappingJackson2MessageConverter converter = new MappingJackson2MessageConverter();
        converter.setObjectMapper(new ObjectMapper());
        converter.setContentTypeResolver(resolver);
        messageConverters.add(converter);
        return false;
    }

}

