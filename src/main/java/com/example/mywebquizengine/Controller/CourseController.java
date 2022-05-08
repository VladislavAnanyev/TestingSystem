package com.example.mywebquizengine.Controller;

import com.example.mywebquizengine.Model.Projection.TestView;
import com.example.mywebquizengine.Model.User;
import com.example.mywebquizengine.Model.dto.input.CreateCourseRequest;
import com.example.mywebquizengine.Model.AddMemberToCourseRequest;
import com.example.mywebquizengine.Service.CourseService;
import com.example.mywebquizengine.Service.TestService;
import com.example.mywebquizengine.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.security.Principal;
import java.util.List;

@Controller
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

    @GetMapping("/courses/my")
    public String getMyCourses(Model model, @AuthenticationPrincipal User authUser) {
        model.addAttribute("courses", courseService.getMyCourses(authUser.getUserId()));
        return "courses";
    }

    @PostMapping("/course")
    public String createCourse(@RequestBody CreateCourseRequest courseRequest, @AuthenticationPrincipal User authUser) {
        courseService.createCourse(courseRequest.getCourseName(), authUser.getUserId());
        return "redirect:/courses";
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

}
