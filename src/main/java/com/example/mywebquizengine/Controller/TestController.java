package com.example.mywebquizengine.Controller;

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
import java.security.Principal;

@Validated
@Controller
@Service
public class TestController {

    @Autowired
    private UserAnswerService userAnswerService;

    @Autowired
    private TestService testService;

    @GetMapping(path = "/course/{courseId}/test/create")
    public String addTest(@PathVariable Long courseId, Model model) {
        model.addAttribute("courseId", courseId);
        return "addQuiz";
    }

    @PostMapping(path = "/test/create", consumes = {"application/json"})
    public String addTest(
            @RequestBody @Valid CreateTestRequest request,
            @AuthenticationPrincipal User authUser) throws ResponseStatusException {
        testService.add(
                request.getCourseId(),
                request.getDuration(),
                request.getQuizzes(),
                request.getDescription(),
                authUser.getUserId(),
                request.getStartAt(),
                request.getFinishAt()
        );
        return "redirect:/";
    }

    @GetMapping(path = "/quizzes")
    public String getQuizzes(Model model, @RequestParam(required = false, defaultValue = "0") @Min(0) Integer page,
                             @RequestParam(required = false, defaultValue = "10") @Min(1) @Max(10) Integer pageSize,
                             @RequestParam(defaultValue = "id") String sortBy) {
        Page<Test> quizzes = testService.getAllQuizzes(page, pageSize, sortBy);
        model.addAttribute("test", quizzes.getContent());
        return "getAllQuiz";
    }

    @DeleteMapping(path = "/quizzes/{id}")
//    @PreAuthorize(value = "@testService.findTest(#id).course.owner.userId.equals(#principal.name)")
    public void deleteTest(@PathVariable Long id, @AuthenticationPrincipal User authUser) {
        testService.deleteTest(id);
        throw new ResponseStatusException(HttpStatus.OK);
    }


    @PutMapping(path = "/update/{id}", consumes = {"application/json"})
    @ResponseBody
//    @PreAuthorize(value = "@testService.findTest(#id).user.username.equals(#principal.name)")
    public void changeTest(@PathVariable Long id, @Valid @RequestBody Test test,
                           @AuthenticationPrincipal User authUser) throws ResponseStatusException {
        testService.updateTest(id, test);
    }

    @GetMapping(path = "/update/{id}")
//    @PreAuthorize(value = "@testService.findTest(#id).user.username.equals(#principal.name)")
    public String update(@PathVariable Long id, Model model, @AuthenticationPrincipal User authUser) {
        Test tempTest = testService.findTest(id);
        model.addAttribute("oldTest", tempTest);
        return "updateQuiz";
    }

    @GetMapping(path = "/quizzes/{id}/info/")
    //@PreAuthorize(value = "@testService.findTest(#id).user.username.equals(#principal.name)")
    public String getInfo(@PathVariable Long id, Model model,
                          @RequestParam(required = false, defaultValue = "0") @Min(0) Integer page,
                          @RequestParam(required = false, defaultValue = "2000") @Min(1) @Max(2000) Integer pageSize,
                          @RequestParam(defaultValue = "completed_at") String sortBy,
                          @AuthenticationPrincipal User authUser) {

        Test test = testService.findTest(id);
        model.addAttribute("quizzes", test.getQuizzes());
        model.addAttribute("chart", userAnswerService.getAnswerStats(id));
        model.addAttribute("answersOnQuiz", userAnswerService.getPageAnswersById(id, page, pageSize, sortBy).getContent());
        return "info";
    }
}
