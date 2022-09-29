package com.example.mywebquizengine.Controller.api;

import com.example.mywebquizengine.Model.AddMemberToCourseRequest;
import com.example.mywebquizengine.Model.Projection.CourseView;
import com.example.mywebquizengine.Model.Projection.TestView;
import com.example.mywebquizengine.Model.Projection.UserViewForCourseInfo;
import com.example.mywebquizengine.Model.User;
import com.example.mywebquizengine.Model.dto.input.CreateCourseRequest;
import com.example.mywebquizengine.Service.CourseService;
import com.example.mywebquizengine.Service.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.util.List;

@RestController
@RequestMapping(path = "/api")
public class ApiCourseController {

    @Autowired
    private CourseService courseService;

    @Autowired
    private TestService testService;

    @GetMapping("/courses")
    public List<CourseView> getCourses() {
        User principal = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return courseService.getMyCourses(principal.getUserId());
    }

    @GetMapping("/course/{id}/info")
    public List<UserViewForCourseInfo> getInfoAboutCourse(@PathVariable Long id) {
        return courseService.findMembersWithInfo(id);
    }

    @GetMapping("/course/{id}/tests")
    public List<TestView> getTestsInCourse(@PathVariable Long id) {
        return testService.findTestInCourse(id);
    }

    @GetMapping("/courses/my")
    public List<CourseView> getMyCourses(@AuthenticationPrincipal User authUser) {
        return courseService.getMyCourses(authUser.getUserId());
    }

    @PostMapping("/course")
    public void createCourse(@RequestBody CreateCourseRequest courseRequest, @AuthenticationPrincipal User authUser) {
        courseService.createCourse(courseRequest.getCourseName(), authUser.getUserId());
    }

    @PostMapping("/course/{id}/members/add")
    public void addMemberToCourse(
            @PathVariable Long id,
            @RequestBody AddMemberToCourseRequest request,
            @AuthenticationPrincipal User authUser) {
        courseService.addMember(id, request.getEmail(), authUser.getUserId(), request.getGroup());
    }

    @GetMapping("/course/{id}/manage")
    public List<TestView> getMyCourses(@AuthenticationPrincipal User authUser, @PathVariable Long id,
                                       @RequestParam(required = false, defaultValue = "0") @Min(0) Integer page,
                                       @RequestParam(required = false, defaultValue = "10") @Min(1) @Max(10) Integer pageSize,
                                       @RequestParam(defaultValue = "test_id") String sortBy) {
        return testService.findTestsInCourseByName(id, authUser.getUserId());
    }


}
