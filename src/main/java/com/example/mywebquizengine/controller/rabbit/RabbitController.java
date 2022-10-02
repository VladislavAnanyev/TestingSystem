package com.example.mywebquizengine.controller.rabbit;

import com.example.mywebquizengine.model.chat.Message;
import com.example.mywebquizengine.service.MessageService;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.json.simple.parser.ParseException;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.security.Principal;

@Controller
@EnableRabbit
public class RabbitController {

    @Autowired
    private MessageService messageService;

    @Modifying
    @Transactional
    @MessageMapping("/user/{dialogId}")
    public void sendMessage(@Valid @Payload Message message,
                            @AuthenticationPrincipal Principal authUser
    ) throws JsonProcessingException, ParseException {
        messageService.sendMessage(message, "WEB");
    }

    @Modifying
    @Transactional
    @RabbitListener(queues = "incoming-messages")
    public void getMessageFromAndroid(@Valid Message message) throws JsonProcessingException, ParseException {
        messageService.sendMessage(message, "ANDROID");
    }
}
