package com.example.mywebquizengine.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class WorkSchedule {

    @Autowired
    private MailSender sender;

    /*@Scheduled(cron = "0 0 12 * * ?", zone = "Europe/Moscow")
    public void send() {
        try {
            sender.send("a.vlad.v@ya.ru","Подписка на WebQuizzes", "Вы подписаны на рассылку сообщений" +
                    " от WebQuizzes, это письмо приходит каждый день в 12 часов дня по МСК");
        } catch (Exception e) {
            System.out.println("Не отправлено");
        }

    }*/

}
