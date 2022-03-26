package com.example.mywebquizengine.Controller;

import com.example.mywebquizengine.Model.Test.Test;
import com.example.mywebquizengine.Model.dto.CreateTestRequest;
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
import java.security.Principal;

@Validated
@Controller
@Service
public class TestController {

    @Autowired
    private UserAnswerService userAnswerService;

    @Autowired
    private TestService testService;

    @GetMapping(path = "/course/{id}/test/add")
    public String addTest(Model model, @PathVariable Long id) {
        model.addAttribute("courseId", id);
        return "addQuiz";
    }

    @PostMapping(path = "/quizzes", consumes = {"application/json"})
    public String addTest(@RequestBody @Valid CreateTestRequest request, @AuthenticationPrincipal Principal principal) throws ResponseStatusException {
        testService.add(
                request.getCourseId(),
                request.getDuration(),
                request.getQuizzes(),
                request.getDescription(),
                "a.vlad.v@yandex.ru"
        );
        return "redirect:/";
    }

    @GetMapping(path = "/quizzes")
    public String getQuizzes(Model model, @RequestParam(required = false, defaultValue = "0") @Min(0) Integer page,
                             @RequestParam(required = false, defaultValue = "10") @Min(1) @Max(10) Integer pageSize,
                             @RequestParam(defaultValue = "id") String sortBy) throws IOException {

        Page<Test> page1 = testService.getAllQuizzes(page, pageSize, sortBy);


        model.addAttribute("test", page1.getContent());
        return "getAllQuiz";
    }

    @DeleteMapping(path = "/quizzes/{id}")
    @PreAuthorize(value = "@testService.findTest(#id).user.username.equals(#principal.name)")
    public void deleteTest(@PathVariable Long id, @AuthenticationPrincipal Principal principal) {
        testService.deleteTest(id);
        throw new ResponseStatusException(HttpStatus.OK);
    }


    @PutMapping(path = "/update/{id}", consumes = {"application/json"})
    @ResponseBody
    @PreAuthorize(value = "@testService.findTest(#id).user.username.equals(#principal.name)")
    public void changeTest(@PathVariable Long id, @Valid @RequestBody Test test,
                           @AuthenticationPrincipal Principal principal) throws ResponseStatusException {
        testService.updateTest(id, test);
    }

    @GetMapping(path = "/update/{id}")
    @PreAuthorize(value = "@testService.findTest(#id).user.username.equals(#principal.name)")
    public String update(@PathVariable Long id, Model model, @AuthenticationPrincipal Principal principal) {
        Test tempTest = testService.findTest(id);
        model.addAttribute("oldTest", tempTest);
        return "updateQuiz";
    }

    @GetMapping(path = "/quizzes/{id}/info/")
    @PreAuthorize(value = "@testService.findTest(#id).user.username.equals(#principal.name)")
    public String getInfo(@PathVariable Long id, Model model,
                          @RequestParam(required = false, defaultValue = "0") @Min(0) Integer page,
                          @RequestParam(required = false, defaultValue = "2000") @Min(1) @Max(2000) Integer pageSize,
                          @RequestParam(defaultValue = "completed_at") String sortBy, @AuthenticationPrincipal Principal principal) {

        Test test = testService.findTest(id);
        model.addAttribute("quizzes", test.getQuizzes());
        model.addAttribute("chart", userAnswerService.getAnswerStats(id));
        model.addAttribute("answersOnQuiz", userAnswerService.getPageAnswersById(id, page, pageSize, sortBy).getContent());
        return "info";
    }


}
