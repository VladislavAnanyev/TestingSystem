package com.example.mywebquizengine.controller.web;

import com.example.mywebquizengine.model.projection.TestView;
import com.example.mywebquizengine.model.User;
import com.example.mywebquizengine.model.dto.input.CreateCourseRequest;
import com.example.mywebquizengine.model.AddMemberToCourseRequest;
import com.example.mywebquizengine.model.dto.output.CreateCourseResponse;
import com.example.mywebquizengine.service.CourseService;
import com.example.mywebquizengine.service.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.util.List;

@Controller
@Validated
public class CourseController {

    @Autowired
    private CourseService courseService;

    @Autowired
    private TestService testService;

    @GetMapping("/courses")
    public String getCourses(Model model) {
        User principal = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        model.addAttribute("courses", courseService.getMyCourses(principal.getUserId()));
        return "courses";
    }

    @GetMapping("/course/{id}/info")
    public String getInfoAboutCourse(@PathVariable Long id, Model model) {
        model.addAttribute("members", courseService.findMembersWithInfo(id));
        return "courseinfo";
    }

    @GetMapping("/course/{id}/tests")
    public String getTestsInCourse(@PathVariable Long id, Model model) {
        model.addAttribute("tests", testService.findTestInCourse(id));
        return "getAllQuiz";
    }

    @PostMapping("/course")
    @ResponseBody
    public CreateCourseResponse createCourse(@Valid @RequestBody CreateCourseRequest courseRequest,
                                             @AuthenticationPrincipal User authUser) {
        //authUser - аутентифицированный пользователь, определяется по cookie

        //создание курса и получение его идентификатора
        Long courseId = courseService.createCourse(courseRequest.getCourseName(), authUser.getUserId());

        //подготовка и возврат ответа
        CreateCourseResponse response = new CreateCourseResponse();
        response.setCourseId(courseId);
        return response;
    }

    @PostMapping("/course/{id}/members/add")
    public void addMemberToCourse(
            @PathVariable Long id,
            @RequestBody AddMemberToCourseRequest request,
            @AuthenticationPrincipal User authUser) {
        courseService.addMember(id, request.getEmail(), authUser.getUserId(), request.getGroup());
        throw new ResponseStatusException(HttpStatus.OK);
    }
    
    @GetMapping("/course/{id}/manage")
    public String getMyCourses(Model model, @AuthenticationPrincipal User authUser, @PathVariable Long id,
                               @RequestParam(required = false, defaultValue = "0") @Min(0) Integer page,
                               @RequestParam(required = false, defaultValue = "10") @Min(1) @Max(10) Integer pageSize,
                               @RequestParam(defaultValue = "test_id") String sortBy) {
        List<TestView> pageObject = testService.findTestsInCourseByName(id, authUser.getUserId());
        model.addAttribute("myquiz", pageObject);
        model.addAttribute("courseId", id);
        return "myquiz";
    }

    @DeleteMapping("/course/{id}")
    @ResponseBody
    public void deleteCourse(@PathVariable Long id) {
        courseService.deleteCourse(id);
    }

}
