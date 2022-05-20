package com.example.mywebquizengine.Controller.web;

import com.example.mywebquizengine.Model.Projection.GroupView;
import com.example.mywebquizengine.Model.Test.Quiz;
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
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.transaction.Transactional;
import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.math.BigInteger;
import java.security.Principal;
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
    public void sendAnswer(@PathVariable Long id) {
        userAnswerService.checkAnswer(id);
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
            Collections.shuffle(quizzes);
            model.addAttribute("quizzes", quizzes);

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
        model.addAttribute("timeChart", timeAnswerStats);
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
