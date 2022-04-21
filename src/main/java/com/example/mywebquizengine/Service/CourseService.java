package com.example.mywebquizengine.Service;

import com.example.mywebquizengine.Model.Chat.Dialog;
import com.example.mywebquizengine.Model.Chat.Message;
import com.example.mywebquizengine.Model.Chat.MessageStatus;
import com.example.mywebquizengine.Model.Course;
import com.example.mywebquizengine.Model.Projection.CourseView;
import com.example.mywebquizengine.Model.Projection.UserViewForCourseInfo;
import com.example.mywebquizengine.Model.User;
import com.example.mywebquizengine.Repos.CourseRepository;
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
    private CourseRepository courseRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private MailSender mailSender;

    @Value("${hostname}")
    private String hostname;
    @Autowired
    private MessageService messageService;

    public void createCourse(String name, Long userId) {
        Course course = new Course();
        course.setName(name);
        course.setImage("https://localhost/img/default.jpg");
        User user = userService.loadUserByUserId(userId);
        course.setOwner(user);


        Dialog dialog = new Dialog();
        dialog.addUser(user);
        dialog.setName(name);
        dialog.setImage("https://localhost/img/default.jpg");

        course.setDialog(dialog);

        Message message = new Message();
        message.setSender(user);
        message.setTimestamp(new Date());
        message.setDialog(dialog);
        message.setStatus(MessageStatus.DELIVERED);
        message.setContent(user.getFirstName() + " " + user.getLastName() + " " + "создал группу \"Базы данных \"");

        dialog.setMessages(Collections.singletonList(message));
        courseRepository.save(course);
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
    public void addMember(Long courseId, String email, Long userId) {
        Course course = findCourseById(courseId);
        if (course.getOwner().getUserId().equals(userId)) {
            User user = new User();
            try {
                user = userService.loadUserByEmail(email);
            } catch (ResponseStatusException e) {
                String uuid = UUID.randomUUID().toString();
                user.setEmail(email);
                user.setActivationCode(uuid);
                user.setStatus(false);
                user.setEnabled(false);
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
            messageService.addUsersToDialog(course.getDialog().getDialogId(), Collections.singletonList(user));

        } else throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Вы не создатель этого курса");
    }

    public List<CourseView> getMyCourses(Long userId) {
        return courseRepository.findCourseByMembers(userId);
    }

    @Autowired
    private UserRepository userRepository;

    public List<UserViewForCourseInfo> findMembersWithInfo(Long courseId) {
        return userRepository.findMembersByCourseAndMembers(courseId);
    }
}
