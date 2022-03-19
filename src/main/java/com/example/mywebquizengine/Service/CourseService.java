package com.example.mywebquizengine.Service;

import com.example.mywebquizengine.Model.Course;
import com.example.mywebquizengine.Model.Projection.CourseView;
import com.example.mywebquizengine.Model.User;
import com.example.mywebquizengine.Repos.CourseRepository;
import com.example.mywebquizengine.Repos.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class CourseService {

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private MailSender mailSender;

    @Value("${hostname}")
    private String hostname;

    public void createCourse(String name, String username) {
        Course course = new Course();
        course.setName(name);
        course.setOwner(userService.loadUserByUsername(username));
        courseRepository.save(course);
    }

    public ArrayList<CourseView> getAllCourses() {
        return (ArrayList<CourseView>) courseRepository.findAllCourses();
    }

    public Course findCourseById(Long id) {
        Optional<Course> optionalCourse = courseRepository.findById(id);
        if (optionalCourse.isPresent()) {
            return optionalCourse.get();
        } else throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Course not found");
    }

    @Transactional
    public void addMember(Long courseId, String email, String name) {
        Course course = findCourseById(courseId);
        if (course.getOwner().getUsername().equals(name)) {
            User user = new User();
            try {
                user = userService.loadUserByEmail(email);
            } catch (ResponseStatusException e) {
                String uuid = UUID.randomUUID().toString();
                user.setEmail(email);
                user.setActivationCode(uuid);
                user.setStatus(false);
                user.setEnabled(false);
                user.setUsername(email);
                user.setFirstName(email);
                user.setLastName(email);
                userService.saveUser(user);

                mailSender.send(
                        email,
                        "Приглашение в ",
                        "Для регистрации перейдите по ссылке: " + hostname + "/start/" + uuid
                );
            }
            course.addMember(user);
        } else throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Вы не создатель этого курса");
    }

    public List<CourseView> getMyCourses(String name) {
        return courseRepository.findCourseByMembers(name);
    }
}
