package com.example.mywebquizengine.Controller;

import com.example.mywebquizengine.Model.Test.Test;
import com.example.mywebquizengine.Model.Test.UserTestAnswer;
import com.example.mywebquizengine.Model.User;
import com.example.mywebquizengine.Model.dto.input.AnswerDuration;
import com.example.mywebquizengine.Model.dto.input.UserTestAnswerRequest;
import com.example.mywebquizengine.Model.dto.output.SendAnswerResponse;
import com.example.mywebquizengine.Service.TestService;
import com.example.mywebquizengine.Service.UserAnswerService;
import com.example.mywebquizengine.Service.UserService;
import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.security.Principal;
import java.util.Calendar;
import java.util.GregorianCalendar;

@Controller
@Validated
public class UserAnswerController {

    @Autowired
    private TestService testService;

    @Autowired
    private UserAnswerService userAnswerService;

    @Autowired
    private UserService userService;

    @GetMapping(path = "/checklastanswer/{id}")
    @ResponseBody
    public Boolean checkLastAnswer(@PathVariable Long id, @AuthenticationPrincipal User authUser) {
        UserTestAnswer userTestAnswer = userAnswerService.checkLastComplete(
                userService.loadUserByUserIdProxy(authUser.getUserId()),
                id
        );

        if (userTestAnswer != null) {
            return userTestAnswer.getCompletedAt() == null;
        } else {
            return false;
        }
    }

    @PostMapping(path = "/test/answer/{userTestAnswerId}/send")
    @ResponseBody
    @Transactional
    public void sendAnswer(@PathVariable Long userTestAnswerId) {
        userAnswerService.checkAnswer(userTestAnswerId);
    }

    @GetMapping(path = "/test/answer/{testId}/start")
    public String passTest(@PathVariable Long testId,
                           @RequestParam(required = false, defaultValue = "false") String restore,
                           @AuthenticationPrincipal User authUser) throws SchedulerException {
        return userAnswerService.startAnswer(testId, restore, authUser.getUserId());
    }

    @GetMapping(value = "/test/{testId}/{userTestAnswerId}/solve")
    public String startAnswer(@PathVariable Long testId, @PathVariable Long userTestAnswerId, Model model) {
        UserTestAnswer userTestAnswer = userAnswerService.findByUserAnswerId(userTestAnswerId);
        Test test = testService.findTest(testId);

        if (userTestAnswer.getCompletedAt() != null) {
            SendAnswerResponse sendAnswerResponse = new SendAnswerResponse();
            sendAnswerResponse.setPercent(userTestAnswer.getPercent());
            if (test.isDisplayAnswers()) {
                model.addAttribute("quizAnswers", userTestAnswer.getUserQuizAnswers());
            }
            model.addAttribute("sendAnswerResponse", sendAnswerResponse);

            return "result";
        } else {
            model.addAttribute("lastAnswer", userTestAnswer);
            model.addAttribute("test_id", test);

            if (test.getDuration() != null) {
                Calendar userTestAnswerStartAt = userTestAnswer.getStartAt();
                Calendar userTestAnswerFinishAt = new GregorianCalendar();
                userTestAnswerFinishAt.setTime(userTestAnswerStartAt.getTime());
                userTestAnswerFinishAt.set(Calendar.SECOND, userTestAnswerFinishAt.get(Calendar.SECOND) + test.getDuration().getSecond());
                userTestAnswerFinishAt.set(Calendar.MINUTE, userTestAnswerFinishAt.get(Calendar.MINUTE) + test.getDuration().getMinute());
                userTestAnswerFinishAt.set(Calendar.HOUR, userTestAnswerFinishAt.get(Calendar.HOUR) + test.getDuration().getHour());

                if (test.getEndTime() != null) {
                    Calendar nowTimePlusDuration = new GregorianCalendar();
                    nowTimePlusDuration.add(Calendar.SECOND, test.getDuration().getSecond());
                    nowTimePlusDuration.add(Calendar.MINUTE, test.getDuration().getMinute());
                    nowTimePlusDuration.add(Calendar.HOUR, test.getDuration().getHour());

                    if (nowTimePlusDuration.after(test.getEndTime())) {
                        userTestAnswerFinishAt = test.getEndTime();
                    }
                }
                model.addAttribute("timeout", userTestAnswerFinishAt.getTime());
            }

            return "answer";
        }
    }

    @PostMapping(value = "/test/answer/update")
    public void getAnswerSession(@AuthenticationPrincipal User authUser, @Valid @RequestBody UserTestAnswerRequest request) {
        userAnswerService.updateAnswer(request.getQuizId(), request.getAnswer(), authUser.getUserId());
        throw new ResponseStatusException(HttpStatus.OK);
    }

    @PostMapping(value = "/test/answer/duration")
    public void updateDuration(@RequestBody AnswerDuration answerDuration) {
        userAnswerService.updateDuration(
                answerDuration.getAnswerSessionId(),
                answerDuration.getQuizId(),
                answerDuration.getDuration()
        );
        throw new ResponseStatusException(HttpStatus.OK);
    }

}
