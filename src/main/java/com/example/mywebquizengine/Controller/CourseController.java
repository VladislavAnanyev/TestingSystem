package com.example.mywebquizengine.Controller;

import com.example.mywebquizengine.Model.Projection.TestView;
import com.example.mywebquizengine.Model.User;
import com.example.mywebquizengine.Model.dto.CreateCourseRequest;
import com.example.mywebquizengine.Model.AddMemberToCourseRequest;
import com.example.mywebquizengine.Service.CourseService;
import com.example.mywebquizengine.Service.TestService;
import com.example.mywebquizengine.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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

    @Autowired
    private UserService userService;

    @GetMapping("/courses")
    public String getCourses(Model model) {
        model.addAttribute("courses", courseService.getAllCourses());
        return "courses";
    }

    @GetMapping("/course/{id}/tests")
    public String getTestsInCourse(@PathVariable Long id, Model model) {
        model.addAttribute("tests", testService.findTestInCourse(id));
        return "getAllQuiz";
    }

    @GetMapping("/courses/my")
    public String getMyCourses(Model model, @AuthenticationPrincipal Principal principal) {
        model.addAttribute("courses", courseService.getMyCourses(principal.getName()));
        return "courses";
    }

    @GetMapping("/course/create")
    public String getCreateForm() {
        return "create-course";
    }

    @PostMapping("/course")
    public String createCourse(@RequestBody CreateCourseRequest courseRequest, @AuthenticationPrincipal Principal principal) {
        courseService.createCourse(courseRequest.getCourseName(), principal.getName());
        return "redirect:/courses";
    }

    @GetMapping("/course/{id}/members/add")
    public String addMemberToCourseView(Model model, @PathVariable Long id) {
        model.addAttribute("courseId", id);
        return "add-member";
    }

    @PostMapping("/course/{id}/members/add")
    public void addMemberToCourse(
            @PathVariable Long id,
            @RequestBody AddMemberToCourseRequest request,
            @AuthenticationPrincipal Principal principal
    ) {
        courseService.addMember(id, request.getEmail(), principal.getName());
    }

    @GetMapping("/course/{id}/manage")
    public String getMyCourses(Model model, @AuthenticationPrincipal Principal principal, @PathVariable Long id,
                             @RequestParam(required = false, defaultValue = "0") @Min(0) Integer page,
                             @RequestParam(required = false, defaultValue = "10") @Min(1) @Max(10) Integer pageSize,
                             @RequestParam(defaultValue = "test_id") String sortBy) {
        List<TestView> pageObject = testService.findTestsInCourseByName(id, principal.getName());
        model.addAttribute("myquiz", pageObject);
        model.addAttribute("courseId", id);
        return "myquiz";
    }

}
