package com.example.mywebquizengine.controller.api;

import com.example.mywebquizengine.model.User;
import com.example.mywebquizengine.model.dto.input.AnswerDuration;
import com.example.mywebquizengine.model.dto.input.UserTestAnswerRequest;
import com.example.mywebquizengine.model.dto.output.SendAnswerResponse;
import com.example.mywebquizengine.model.test.Test;
import com.example.mywebquizengine.model.test.UserTestAnswer;
import com.example.mywebquizengine.service.TestService;
import com.example.mywebquizengine.service.UserAnswerService;
import com.example.mywebquizengine.service.UserService;
import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.math.BigInteger;
import java.util.*;

@RestController
@RequestMapping(path = "/api")
public class ApiUserAnswerController {

    @Autowired
    private UserAnswerService userAnswerService;

    @Autowired
    private TestService testService;

    @Autowired
    private UserService userService;

   /* @GetMapping(path = "/quizzes/{id}/info")
    @PreAuthorize(value = "@testService.findTest(#id).course.owner.userId.equals(#authUser.userId)")
    public String getInfo(@PathVariable Long id, Model model,
                          @RequestParam(required = false) Long groupId,
                          @RequestParam(required = false, defaultValue = "0") @Min(0) Integer page,
                          @RequestParam(required = false, defaultValue = "2000") @Min(1) @Max(2000) Integer pageSize,
                          @RequestParam(defaultValue = "completed_at") String sortBy,
                          @AuthenticationPrincipal User authUser) {

        Map<BigInteger, Double> answerStats = userAnswerService.getAnswerStats(id, groupId);
        if (answerStats != null) {
            double min = answerStats.values().stream().min(Double::compareTo).get();
            double max = answerStats.values().stream().max(Double::compareTo).get();

            int minQuestionIndex = 0;
            int maxQuestionIndex = 0;

            List<Double> values = new ArrayList<>(answerStats.values());
            for (int i = 0; i < values.size(); i++) {
                Double aDouble = values.get(i);
                if (aDouble.intValue() == min) {
                    minQuestionIndex = i;
                }
                if (aDouble.intValue() == max) {
                    maxQuestionIndex = i;
                }
            }

            model.addAttribute("min", minQuestionIndex + 1);
            model.addAttribute("max", maxQuestionIndex + 1);
        }
        model.addAttribute("chart", answerStats);
        model.addAttribute("answersOnQuiz", userAnswerService.getPageAnswersById(id, groupId));
        model.addAttribute("moreAnswers", userAnswerService.getAnswerStat(id, groupId));
        return "info";
    }*/

    @GetMapping(path = "/checklastanswer/{id}")
    @ResponseBody
    public Boolean checkLastAnswer(@PathVariable Long id, @AuthenticationPrincipal User authUser) {
        return userAnswerService.checkLastComplete(
                userService.loadUserByUserIdProxy(authUser.getUserId()),
                id
        ) != null;
    }

    @PostMapping(path = "/test/answer/{userTestAnswerId}/send")
    @ResponseBody
    @Transactional
    public void sendAnswer(@PathVariable Long userTestAnswerId, @AuthenticationPrincipal User user) {
        userAnswerService.checkAnswer(userTestAnswerId, user.getUserId());
    }

    @GetMapping(path = "/test/answer/{testId}/start")
    public Long passTest(@PathVariable Long testId,
                         @RequestParam(required = false, defaultValue = "false") String restore,
                         @AuthenticationPrincipal User authUser) throws SchedulerException {
        return userAnswerService.startAnswer(testId, restore, authUser.getUserId());
    }

    /*@GetMapping(value = "/test/{testId}/{userTestAnswerId}/solve")
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
            model.addAttribute("testId", test.getTestId());
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
    }*/

    @PostMapping(value = "/test/answer/update")
    public void getAnswerSession(@AuthenticationPrincipal User authUser, @Valid @RequestBody UserTestAnswerRequest request) {
        userAnswerService.updateAnswer(request.getQuizId(), request.getAnswer(), authUser.getUserId());
    }

    @PostMapping(value = "/test/answer/duration")
    public void updateDuration(@RequestBody AnswerDuration answerDuration) {
        userAnswerService.updateDuration(
                answerDuration.getAnswerSessionId(),
                answerDuration.getQuizId(),
                answerDuration.getDuration()
        );
    }

}
