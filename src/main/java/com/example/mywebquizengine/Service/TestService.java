package com.example.mywebquizengine.Service;

import com.example.mywebquizengine.Model.Test.Test;
import com.example.mywebquizengine.Repos.QuizRepository;
import com.example.mywebquizengine.Repos.TestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

//@Transactional
@Service
public class TestService {

    @Autowired
    private QuizRepository quizRepository;

    @Autowired
    private TestRepository testRepository;

    public Page<Test> getMyQuiz (String name, Integer page, Integer pageSize, String sortBy) {
        Pageable paging = PageRequest.of(page, pageSize, Sort.by(sortBy).descending());
        return testRepository.getQuizForThis(name, paging);
    }

    public List<Test> getMyQuizNoPaging (String name) {
        return testRepository.getQuizForThisNoPaging(name);
    }

    public Page<Test> getAllQuizzes(Integer page, Integer pageSize, String sortBy) {
        Pageable paging = PageRequest.of(page, pageSize, Sort.by(sortBy).descending());
        return testRepository.findAll(paging);
    }

    public void saveTest(Test test) {
        //test.getQuizzes().get(0).setTitle(test.getQuizzes().get(0).getTitle().replace("<","|"));
        //test.setTitle(test.getTitle().replace("<","|"));
        testRepository.save(test);

        //quizRepository.save(test.getQuizzes().get(0));
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
        //oldTest.setDescription(test.getDescription());

        /*for (int i = 0; i < test.getQuizzes().size(); i++) {
            test.getQuizzes().get(i).setId(oldTest.getQuizzes().get(i).getId());
        }*/

        oldTest.setDescription(test.getDescription());

        oldTest.setQuizzes(test.getQuizzes());
        //oldTest.setAnswers(test.getAnswers());

        testRepository.save(oldTest);
    }

}
