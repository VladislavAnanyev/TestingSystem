package com.example.mywebquizengine.Service;

import com.example.mywebquizengine.Model.Course;
import com.example.mywebquizengine.Model.Group;
import com.example.mywebquizengine.Model.Projection.CourseView;
import com.example.mywebquizengine.Model.Projection.UserViewForCourseInfo;
import com.example.mywebquizengine.Model.User;
import com.example.mywebquizengine.Repos.CourseRepository;
import com.example.mywebquizengine.Repos.GroupRepository;
import com.example.mywebquizengine.Repos.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.transaction.Transactional;
import java.util.*;

@Service
public class CourseService {

    @Autowired
    private GroupRepository groupRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MailSender mailSender;

    @Value("${hostname}")
    private String hostname;

    @Autowired
    private UserService userService;

    @Autowired
    private CourseRepository courseRepository;

    public Long createCourse(String name, Long userId) {
        Course course = new Course();
        course.setCourseName(name);
        course.setImage(hostname + "/img/default.jpg");
        User user = userService.loadUserByUserId(userId);
        course.setOwner(user);
        course.addMember(user);

        courseRepository.save(course);
        return course.getCourseId();
    }


    public List<CourseView> getAllCourses() {
        return courseRepository.findAllCourses();
    }

    public Course findCourseById(Long id) {
        Optional<Course> optionalCourse = courseRepository.findById(id);
        if (optionalCourse.isPresent()) {
            return optionalCourse.get();
        } else throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Course not found");
    }

    @Transactional
    public void addMember(Long courseId, String email, Long userId, String groupName) {
        Course course = findCourseById(courseId);
        if (course.getOwner().getUserId().equals(userId)) {
            User user = new User();
            try {
                user = userService.loadUserByEmail(email);
            } catch (ResponseStatusException e) {
                String uuid = UUID.randomUUID().toString();
                user.setEmail(email);
                user.setActivationCode(uuid);
                user.setEnabled(false);
                userRepository.save(user);

                mailSender.send(
                        email,
                        "Приглашение в ",
                        "Для регистрации перейдите по ссылке: " + hostname + "/start/" + uuid
                );
            }

            Optional<Group> optionalGroup = groupRepository.findByGroupName(groupName);
            if (optionalGroup.isPresent()) {
                user.setGroup(optionalGroup.get());
            } else {
                Group group = new Group();
                group.setGroupName(groupName);
                user.setGroup(group);
            }

            course.addMember(user);

        } else throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Вы не создатель этого курса");
    }

    public List<CourseView> getMyCourses(Long userId) {
        return courseRepository.findCourseByMembers(userId);
    }

    public List<UserViewForCourseInfo> findMembersWithInfo(Long courseId) {
        return userRepository.findMembersByCourseAndMembers(courseId);
    }

    @Transactional
    public void deleteCourse(Long id) {
        Optional<Course> optionalCourse = courseRepository.findById(id);
        if (optionalCourse.isPresent()) {
            Course course = optionalCourse.get();
            course.getMembers().forEach(course::removeMember);
            courseRepository.deleteById(id);
        } else throw new ResponseStatusException(HttpStatus.NOT_FOUND);
    }
}
