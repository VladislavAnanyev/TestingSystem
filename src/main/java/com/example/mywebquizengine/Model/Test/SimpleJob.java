package com.example.mywebquizengine.Model.Test;


import com.example.mywebquizengine.Controller.web.UserAnswerController;
import com.example.mywebquizengine.MywebquizengineApplication;
import com.example.mywebquizengine.Service.UserAnswerService;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.stereotype.Component;

import java.util.GregorianCalendar;

@Component
public class SimpleJob extends QuartzJobBean {

    @Override
    public void executeInternal(JobExecutionContext context) {

        UserAnswerController userAnswerController = MywebquizengineApplication.ctx.getBean(UserAnswerController.class);
        UserAnswerService userAnswerService = MywebquizengineApplication.ctx.getBean(UserAnswerService.class);
        JobDataMap dataMap = context.getJobDetail().getJobDataMap();
        UserTestAnswer userTestAnswer = new UserTestAnswer();
        userTestAnswer.setUserAnswerId(dataMap.getLong("answer"));
        UserTestAnswer userTestAnswer2 = userAnswerService.findByUserAnswerId(dataMap.getLong("answer"));

        if (userTestAnswer2.getCompletedAt() == null) {
            userAnswerController.sendAnswer(userTestAnswer.getUserAnswerId());
        }

        System.out.println("Задание выполнено в: " + new GregorianCalendar().getTime());
    }
}
