package com.example.mywebquizengine.controller.api;

import com.example.mywebquizengine.model.User;
import com.example.mywebquizengine.model.dto.input.CreateTestRequest;
import com.example.mywebquizengine.model.test.Test;
import com.example.mywebquizengine.service.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.util.List;

@RestController
@RequestMapping(path = "/api")
public class ApiTestController {

    @Autowired
    private TestService testService;

    @PostMapping(path = "/test/create", consumes = {"application/json"})
    public void addTest(
            @RequestBody CreateTestRequest request,
            @AuthenticationPrincipal User authUser) throws ResponseStatusException {

        testService.add(
                request.getCourseId(),
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

    @GetMapping(path = "/quizzes")
    public List<Test> getQuizzes(@RequestParam(required = false, defaultValue = "0") @Min(0) Integer page,
                                 @RequestParam(required = false, defaultValue = "10") @Min(1) @Max(10) Integer pageSize,
                                 @RequestParam(defaultValue = "id") String sortBy) {
        return testService.getAllQuizzes(page, pageSize, sortBy).getContent();
    }

    @DeleteMapping(path = "/quizzes/{id}")
    @PreAuthorize(value = "@testService.findTest(#id).course.owner.userId.equals(#authUser.userId)")
    public void deleteTest(@PathVariable Long id, @AuthenticationPrincipal User authUser) {
        testService.deleteTest(id);
        throw new ResponseStatusException(HttpStatus.OK);
    }

    @GetMapping(path = "/update/{id}")
    @PreAuthorize(value = "@testService.findTest(#id).course.owner.userId.equals(#authUser.userId)")
    public String update(@PathVariable Long id, Model model, @AuthenticationPrincipal User authUser) {
        Test tempTest = testService.findTest(id);
        model.addAttribute("oldTest", tempTest);
        return "updateQuiz";
    }
}
