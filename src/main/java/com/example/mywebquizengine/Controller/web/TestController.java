package com.example.mywebquizengine.Controller.web;

import com.example.mywebquizengine.Model.Test.Test;
import com.example.mywebquizengine.Model.User;
import com.example.mywebquizengine.Model.dto.input.CreateTestRequest;
import com.example.mywebquizengine.Service.TestService;
import com.example.mywebquizengine.Service.UserAnswerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigInteger;
import java.security.Principal;
import java.util.*;

@Validated
@Controller
@Service
public class TestController {

    @Autowired
    private TestService testService;

    @GetMapping(path = "/course/{courseId}/test/create")
    public String addTest(@PathVariable Long courseId, Model model) {
        model.addAttribute("courseId", courseId);
        return "addQuiz";
    }

    @PostMapping(path = "/course/{courseId}/test/create", consumes = {"application/json"})
    @ResponseBody
    public void addTest(
            @RequestBody CreateTestRequest request, @PathVariable Long courseId,
            @AuthenticationPrincipal User authUser) throws ResponseStatusException {

        testService.add(
                courseId,
                request.getDuration(),
                request.getQuizzes(),
                request.getDescription(),
                authUser.getUserId(),
                request.getStartAt(),
                request.getFinishAt(),
                request.isDisplayAnswers(),
                request.getAttempts(),
                request.getPercent()
        );
    }


    @DeleteMapping(path = "/quizzes/{id}")
    @PreAuthorize(value = "@testService.findTest(#id).course.owner.userId.equals(#authUser.userId)")
    public void deleteTest(@PathVariable Long id, @AuthenticationPrincipal User authUser) {
        testService.deleteTest(id);
        throw new ResponseStatusException(HttpStatus.OK);
    }

}
