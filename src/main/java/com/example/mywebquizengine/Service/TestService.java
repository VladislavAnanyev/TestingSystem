package com.example.mywebquizengine.Service;

import com.example.mywebquizengine.Model.Projection.TestView;
import com.example.mywebquizengine.Model.Test.*;
import com.example.mywebquizengine.Model.User;
import com.example.mywebquizengine.Model.dto.input.AddQuizRequest;
import com.example.mywebquizengine.Model.dto.input.MapAnswerQuizRequest;
import com.example.mywebquizengine.Model.dto.input.MultipleAnswerQuizRequest;
import com.example.mywebquizengine.Model.dto.input.StringAnswerQuizRequest;
import com.example.mywebquizengine.Repos.TestRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;

//@Transactional
@Service
public class TestService {

    @Autowired
    private UserService userService;

    @Autowired
    private CourseService courseService;

    @Autowired
    private TestRepository testRepository;

    @Autowired
    private ObjectMapper objectMapper;

    public List<TestView> findTestsInCourseByName(Long courseId, Long userId) {
        //Pageable paging = PageRequest.of(page, pageSize, Sort.by(sortBy).descending());
        return testRepository.findTestsByCourse_CourseIdAndCourse_Owner_UserId(courseId, userId);
    }

/*    public List<Test> getMyQuizNoPaging(String name) {
        return testRepository.getQuizForThisNoPaging(name);
    }*/

    public Page<Test> getAllQuizzes(Integer page, Integer pageSize, String sortBy) {
        Pageable paging = PageRequest.of(page, pageSize, Sort.by(sortBy).descending());
        return testRepository.findAll(paging);
    }

    public void saveTest(Test test) {
        testRepository.save(test);
    }

    public Test findTest(Long id) {
        if (testRepository.findById(id).isPresent()) {
            return testRepository.findById(id).get();
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }
/*
    public Test findTestProxy(Integer id) {
        return testRepository.getOne(id);
    }*/

    public void deleteTest(Long id) {
        if (testRepository.findById(id).isPresent()) {
            //testRepository.nativeDeleteTestById(id);
            testRepository.deleteById(id);
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    public void updateTest(Long id, Test test) {
        Test oldTest = testRepository.findById(id).get();
        oldTest.setDescription(test.getDescription());
        oldTest.setQuizzes(test.getQuizzes());
        testRepository.save(oldTest);
    }

    public List<TestView> findTestInCourse(Long id) {
        return testRepository.findTestsByCourse_CourseId(id);
    }

    public void add(Long courseId, LocalTime duration, List<Object> addQuizRequests,
                    String description, Long userId, Calendar startAt, Calendar finishAt) {
        Test test = new Test();
        test.setStartTime(startAt);
        test.setEndTime(finishAt);
        test.setCourse(courseService.findCourseById(courseId));
        User user = userService.loadUserByUserIdProxy(userId);
        test.setDuration(duration);
        test.setDescription(description);

        List<Quiz> quizzes = new ArrayList<>();
        for (Object objectRequest : addQuizRequests) {
            AddQuizRequest addQuizRequest = objectMapper.convertValue(objectRequest, AddQuizRequest.class);
            switch (addQuizRequest.getType()) {
                case "MULTIPLE" -> {
                    MultipleAnswerQuizRequest addQuizReq = objectMapper.convertValue(objectRequest, MultipleAnswerQuizRequest.class);
                    MultipleAnswerQuiz quiz = new MultipleAnswerQuiz();
                    quiz.setAnswer(addQuizReq.getAnswer());
                    quiz.setOptions(addQuizReq.getOptions());
                    quiz.setText(addQuizReq.getText());
                    quiz.setTitle(addQuizReq.getTitle());
                    quizzes.add(quiz);
                }
                case "STRING" -> {
                    StringAnswerQuizRequest stringAnswerQuizRequest =
                            objectMapper.convertValue(objectRequest, StringAnswerQuizRequest.class);
                    StringAnswerQuiz quiz = new StringAnswerQuiz();
                   /* StringAnswer stringAnswer = new StringAnswer();
                    stringAnswer.setStringAnswer();*/
                    quiz.setAnswer(Collections.singletonList(stringAnswerQuizRequest.getAnswer()));
                    quiz.setText(stringAnswerQuizRequest.getText());
                    quiz.setTitle(stringAnswerQuizRequest.getTitle());
                    quizzes.add(quiz);
                }
                case "MAP" -> {
                    MapAnswerQuizRequest mapAnswerQuizRequest =
                            objectMapper.convertValue(objectRequest, MapAnswerQuizRequest.class);
                    MapAnswerQuiz mapAnswerQuiz = new MapAnswerQuiz();
                    mapAnswerQuiz.setAnswer(mapAnswerQuizRequest.getAnswer());
                    mapAnswerQuiz.setText(mapAnswerQuizRequest.getText());
                    mapAnswerQuiz.setTitle(mapAnswerQuizRequest.getTitle());
                    quizzes.add(mapAnswerQuiz);
                }
            }
        }

        test.setQuizzes(quizzes);
        //test.setUser(user);

        for (int i = 0; i < addQuizRequests.size(); i++) {
            test.getQuizzes().get(i).setTest(test);
        }
        saveTest(test);
    }
}
