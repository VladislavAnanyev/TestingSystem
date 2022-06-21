package com.example.mywebquizengine.Service;

import com.example.mywebquizengine.Model.Course;
import com.example.mywebquizengine.Model.Projection.GroupView;
import com.example.mywebquizengine.Model.Projection.TestView;
import com.example.mywebquizengine.Model.Test.*;
import com.example.mywebquizengine.Model.dto.input.AddQuizRequest;
import com.example.mywebquizengine.Model.dto.input.MapAnswerQuizRequest;
import com.example.mywebquizengine.Model.dto.input.MultipleAnswerQuizRequest;
import com.example.mywebquizengine.Model.dto.input.StringAnswerQuizRequest;
import com.example.mywebquizengine.Repos.GroupRepository;
import com.example.mywebquizengine.Repos.TestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.time.LocalTime;
import java.util.*;

@Service
public class TestService {

    @Autowired
    private CourseService courseService;

    @Autowired
    private TestRepository testRepository;

    @Autowired
    private GroupRepository groupRepository;

    @Autowired
    private FileSystemStorageService fileStorageService;

    @Value("${hostname}")
    private String hostname;

    public List<TestView> findTestsInCourseByName(Long courseId, Long userId) {
        return testRepository.findTestsByCourse_CourseIdAndCourse_Owner_UserId(courseId, userId);
    }

    public Page<Test> getAllQuizzes(Integer page, Integer pageSize, String sortBy) {
        Pageable paging = PageRequest.of(page, pageSize, Sort.by(sortBy).descending());
        return testRepository.findAll(paging);
    }

    public Test findTest(Long id) {
        if (testRepository.findById(id).isPresent()) {
            return testRepository.findById(id).get();
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    public void deleteTest(Long id) {
        testRepository.deleteById(id);
    }

    public List<TestView> findTestInCourse(Long id) {
        return testRepository.findTestsByCourse_CourseId(id);
    }

    public void add(Long courseId, LocalTime duration, List<AddQuizRequest> addQuizRequests,
                    String description, Long userId, Calendar startAt, Calendar finishAt,
                    boolean displayAnswers, Integer attempts, Integer percent) {

        Course course = courseService.findCourseById(courseId);
        if (userId.equals(course.getOwner().getUserId())) {
            Test test = new Test();
            test.setStartTime(startAt);
            test.setEndTime(finishAt);
            test.setCourse(course);
            test.setDuration(duration);
            test.setDescription(description);
            test.setDisplayAnswers(displayAnswers);
            test.setAttempts(attempts);
            test.setPassingScore(percent);

            List<Quiz> quizzes = new ArrayList<>();

            for (AddQuizRequest addQuizRequest : addQuizRequests) {

                Quiz quiz;
                if (addQuizRequest instanceof MultipleAnswerQuizRequest quizRequest) {
                    quiz = new MultipleAnswerQuiz();
                    ((MultipleAnswerQuiz) quiz).setAnswer(quizRequest.getAnswer());
                    ((MultipleAnswerQuiz) quiz).setOptions(quizRequest.getOptions());
                } else if (addQuizRequest instanceof StringAnswerQuizRequest quizRequest) {
                    quiz = new StringAnswerQuiz();
                    ((StringAnswerQuiz) quiz).setAnswer(Collections.singletonList(quizRequest.getAnswer()));
                } else if (addQuizRequest instanceof MapAnswerQuizRequest quizRequest) {
                    quiz = new MapAnswerQuiz();
                    ((MapAnswerQuiz) quiz).setAnswer(quizRequest.getAnswer());
                } else throw new ResponseStatusException(HttpStatus.BAD_REQUEST);


                String uniqueFilename;
                if (addQuizRequest.getFile() != null) {
                    byte[] decodedBytes = Base64.getMimeDecoder().decode(addQuizRequest.getFile().getBytes());
                    try {
                        uniqueFilename = fileStorageService.store(
                                new ByteArrayInputStream(decodedBytes),
                                addQuizRequest.getFile().getFilename()
                        );

                        if (uniqueFilename.substring(uniqueFilename.lastIndexOf(".")).contains("jp")) {
                            quiz.setFileUrl(hostname + "/img/" + uniqueFilename);
                        } else {
                            quiz.setFileUrl(hostname + "/download/" + uniqueFilename);
                        }

                    } catch (IOException e) {
                        throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
                    }
                }

                quiz.setText(addQuizRequest.getText());
                quiz.setTitle(addQuizRequest.getTitle());
                quiz.setTest(test);

                quizzes.add(quiz);
            }

            test.setQuizzes(quizzes);
            testRepository.save(test);
        } else throw new ResponseStatusException(HttpStatus.FORBIDDEN);
    }

    public List<GroupView> getGroupsInCourse(Long courseId) {
        return groupRepository.findGroupsByCourseId(courseId);
    }
}
