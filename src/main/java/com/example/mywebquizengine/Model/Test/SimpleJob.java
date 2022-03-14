package com.example.mywebquizengine.Model.Test;


import com.example.mywebquizengine.Controller.QuizController;
import com.example.mywebquizengine.MywebquizengineApplication;
import com.example.mywebquizengine.Service.UserAnswerService;

import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.quartz.*;

import org.springframework.stereotype.Component;

import java.util.GregorianCalendar;



@Component
public class SimpleJob extends QuartzJobBean {


    //@Transactional
    @Override
    public void executeInternal(JobExecutionContext context)  {

        SimpMessagingTemplate simpMessagingTemplate = MywebquizengineApplication.ctx.getBean(SimpMessagingTemplate.class);
        QuizController quizController = MywebquizengineApplication.ctx.getBean(QuizController.class);

        UserAnswerService userAnswerService = MywebquizengineApplication.ctx.getBean(UserAnswerService.class);

        JobDataMap dataMap = context.getJobDetail().getJobDataMap();

        UserTestAnswer userTestAnswer = new UserTestAnswer();

        userTestAnswer.setUserAnswerId(dataMap.getInt("answer"));

        UserTestAnswer userTestAnswer2 = userAnswerService.findByUserAnswerId(dataMap.getInt("answer"));


        if (userTestAnswer2.getCompletedAt() == null) {

            quizController.getAnswerOnTest(String.valueOf(dataMap.get("test")), userTestAnswer);


            /*simpMessagingTemplate.convertAndSend("/topic/" +
                    dataMap.getString("username") + dataMap.get("test"), "OK");*/



        }

        System.out.println("Задание выполнено в: " + new GregorianCalendar().getTime());

    }







    /*@Modifying
    @Transactional
    @MessageMapping("/user/application")
    //@SendTo("/topic/application")
    public Message sendNotification(@Payload Message message) {
        System.out.println("Абоба");
        simpMessagingTemplate.convertAndSend("/topic/application", new Message());
        return new Message();
    }*/


}
