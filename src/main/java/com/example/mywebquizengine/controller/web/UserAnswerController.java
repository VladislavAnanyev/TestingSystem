package com.example.mywebquizengine.controller.web;

import com.example.mywebquizengine.model.projection.GroupView;
import com.example.mywebquizengine.model.test.Quiz;
import com.example.mywebquizengine.model.test.Test;
import com.example.mywebquizengine.model.test.UserTestAnswer;
import com.example.mywebquizengine.model.User;
import com.example.mywebquizengine.model.dto.input.AnswerDuration;
import com.example.mywebquizengine.model.dto.input.UserTestAnswerRequest;
import com.example.mywebquizengine.model.dto.output.SendAnswerResponse;
import com.example.mywebquizengine.service.TestService;
import com.example.mywebquizengine.service.UserAnswerService;
import com.example.mywebquizengine.service.UserService;
import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.math.BigInteger;
import java.util.*;

@Controller
@Validated
public class UserAnswerController {

    @Autowired
    private TestService testService;

    @Autowired
    private UserAnswerService userAnswerService;

    @Autowired
    private UserService userService;

    @GetMapping(path = "/test/{id}/answer/check")
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

    @PostMapping(path = "/test/answer/{id}/send")
    @ResponseBody
    @Transactional
    public void sendAnswer(@PathVariable Long id, @AuthenticationPrincipal User authUser) {
        userAnswerService.checkAnswer(id, authUser.getUserId());
    }

    @PostMapping(path = "/test/answer/{id}/start")
    @ResponseBody
    public Long passTest(@PathVariable Long id,
                           @RequestParam(required = false, defaultValue = "false") String restore,
                           @AuthenticationPrincipal User authUser) throws SchedulerException {
        return userAnswerService.startAnswer(id, restore, authUser.getUserId());
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
            model.addAttribute("testId", test.getTestId());
            return "result";
        } else {
            model.addAttribute("lastAnswer", userTestAnswer);
            model.addAttribute("test_id", test);
            List<Quiz> quizzes = test.getQuizzes();
            //Collections.shuffle(quizzes);
            model.addAttribute("quizzes", quizzes);

            if (test.getDuration() != null) {
                Calendar userTestAnswerStartAt = userTestAnswer.getStartAt();
                Calendar userTestAnswerFinishAt = new GregorianCalendar();
                userTestAnswerFinishAt.setTime(userTestAnswerStartAt.getTime());
                userTestAnswerFinishAt = UserAnswerService.getEndTime(test);
                model.addAttribute("timeout", userTestAnswerFinishAt.getTime());
            }

            return "answer";
        }
    }

    @PostMapping(value = "/test/answer/update")
    public void updateAnswer(@AuthenticationPrincipal User authUser, @Valid @RequestBody UserTestAnswerRequest request) {
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

    @GetMapping(path = "/test/{id}/info")
    @PreAuthorize(value = "@testService.findTest(#id).course.owner.userId.equals(#authUser.userId)")
    public String getInfo(@PathVariable Long id, Model model,
                          @RequestParam(required = false) Long groupId,
                          @AuthenticationPrincipal User authUser) {

        Test test = testService.findTest(id);
        List<GroupView> groupsInCourse = testService.getGroupsInCourse(test.getCourse().getCourseId());
        model.addAttribute("groups", groupsInCourse);
        model.addAttribute("test_id", test);
        model.addAttribute("quizzes", test.getQuizzes());
        Map<BigInteger, Double> answerStats = userAnswerService.getAnswerStats(id, groupId);
        Map<BigInteger, Double> timeAnswerStats = userAnswerService.getTimeAnswerStats(id, groupId);
        if (answerStats != null) {
            double min = answerStats.values().stream().min(Double::compareTo).get();
            double max = answerStats.values().stream().max(Double::compareTo).get();

            int minQuestionIndex = 0;
            int maxQuestionIndex = 0;

            List<Double> values = new ArrayList<>(answerStats.values());
            for (int i = 0; i < values.size(); i++) {
                Double aDouble = values.get(i);
                if (aDouble == min) {
                    minQuestionIndex = i;
                }
                if (aDouble == max) {
                    maxQuestionIndex = i;
                }
            }


            model.addAttribute("minQuiz", test.getQuizzes().get(minQuestionIndex));
            model.addAttribute("maxQuiz", test.getQuizzes().get(maxQuestionIndex));

            model.addAttribute("min", minQuestionIndex + 1);
            model.addAttribute("max", maxQuestionIndex + 1);

            model.addAttribute("chart", answerStats);
            model.addAttribute("answersOnQuiz", userAnswerService.getPageAnswersById(id, groupId));
            model.addAttribute("moreAnswers", userAnswerService.getAnswerStat(id, groupId));
            model.addAttribute("timeChart", timeAnswerStats);
            if (timeAnswerStats != null) {
                List<Double> timeValues = new ArrayList<>(timeAnswerStats.values());
                model.addAttribute("timeMin", timeValues.get(minQuestionIndex));
                model.addAttribute("timeMax", timeValues.get(maxQuestionIndex));
            }
        }
        if (groupId != null) {
            for (GroupView groupView : groupsInCourse) {
                if (groupView.getGroupId().equals(groupId)) {
                    model.addAttribute("selectedGroup", groupView);
                }
            }
        }
        return "info";
    }

}
