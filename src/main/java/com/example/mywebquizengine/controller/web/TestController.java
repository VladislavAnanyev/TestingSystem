package com.example.mywebquizengine.controller.web;

import com.example.mywebquizengine.model.User;
import com.example.mywebquizengine.model.dto.input.CreateTestRequest;
import com.example.mywebquizengine.service.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

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
