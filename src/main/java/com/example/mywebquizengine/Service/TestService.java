package com.example.mywebquizengine.Service;

import com.example.mywebquizengine.Model.Projection.TestView;
import com.example.mywebquizengine.Model.Test.MultipleAnswerQuiz;
import com.example.mywebquizengine.Model.Test.Quiz;
import com.example.mywebquizengine.Model.Test.StringAnswerQuiz;
import com.example.mywebquizengine.Model.Test.Test;
import com.example.mywebquizengine.Model.User;
import com.example.mywebquizengine.Model.dto.AddQuizRequest;
import com.example.mywebquizengine.Model.dto.MultipleAnswerQuizRequest;
import com.example.mywebquizengine.Model.dto.StringAnswerQuizRequest;
import com.example.mywebquizengine.Repos.QuizRepository;
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

    public Page<TestView> findTestsInCourseByName(String name, Long courseId, Integer page, Integer pageSize, String sortBy) {
        Pageable paging = PageRequest.of(page, pageSize, Sort.by(sortBy).descending());
        return testRepository.findMyTestsInCourse(name, courseId, paging);
    }

    public List<Test> getMyQuizNoPaging (String name) {
        return testRepository.getQuizForThisNoPaging(name);
    }

    public Page<Test> getAllQuizzes(Integer page, Integer pageSize, String sortBy) {
        Pageable paging = PageRequest.of(page, pageSize, Sort.by(sortBy).descending());
        return testRepository.findAll(paging);
    }

    public void saveTest(Test test) {
        testRepository.save(test);
    }

    public Test findTest(Integer id) {
        if (testRepository.findById(id).isPresent()){
            return testRepository.findById(id).get();
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    public Test findTestProxy(Integer id) {
        return testRepository.getOne(id);
    }

    public void deleteTest(int id) {
        if (testRepository.findById(id).isPresent()) {
            testRepository.deleteById(id);
            throw new ResponseStatusException(HttpStatus.NO_CONTENT);
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    public void updateTest(Integer id, Test test) {
        Test oldTest = testRepository.findById(id).get();
        oldTest.setDescription(test.getDescription());
        oldTest.setQuizzes(test.getQuizzes());
        testRepository.save(oldTest);
    }

    public List<TestView> findTestInCourse(Long id) {
        return testRepository.findTestsByCourse_CourseId(id);
    }

    public void add(Long courseId, LocalTime duration, List<Object> addQuizRequests,
                    String description, String name) {
        //try {
            Test test = new Test();
            test.setCourse(courseService.findCourseById(courseId));
            User user = userService.loadUserByUsernameProxy(name);
            test.setDuration(duration);
            test.setDescription(description);

            List<Quiz> quizzes = new ArrayList<>();
            for (Object objectRequest : addQuizRequests) {
                AddQuizRequest addQuizRequest =  objectMapper.convertValue(objectRequest, AddQuizRequest.class);
                if (addQuizRequest.getType().equals("MULTIPLE")) {
                    MultipleAnswerQuizRequest addQuizReq = objectMapper.convertValue(objectRequest, MultipleAnswerQuizRequest.class);
                    MultipleAnswerQuiz quiz = new MultipleAnswerQuiz();
                    quiz.setAnswer(addQuizReq.getAnswer());
                    quiz.setOptions(addQuizReq.getOptions());
                    quiz.setText(addQuizReq.getText());
                    quiz.setTitle(addQuizReq.getTitle());
                    quiz.setType("MULTIPLE");
                    quizzes.add(quiz);
                } else if (addQuizRequest.getType().equals("STRING")) {
                    StringAnswerQuizRequest stringAnswerQuizRequest = objectMapper.convertValue(objectRequest, StringAnswerQuizRequest.class);
                    StringAnswerQuiz quiz = new StringAnswerQuiz();
                    quiz.setAnswer(stringAnswerQuizRequest.getAnswer());
                    quiz.setText(stringAnswerQuizRequest.getText());
                    quiz.setTitle(stringAnswerQuizRequest.getTitle());
                    quiz.setType("STRING");
                    quizzes.add(quiz);
                } else if (addQuizRequest.getType().equals("MAP")) {}

            }

            test.setQuizzes(quizzes);
            test.setUser(user);

            for (int i = 0; i < addQuizRequests.size(); i++) {
                test.getQuizzes().get(i).setTest(test);
            }
            saveTest(test);

        /*} catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }*/
    }
}
