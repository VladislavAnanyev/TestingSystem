package com.example.mywebquizengine.Service;

import com.example.mywebquizengine.Model.Test.Quiz;
import com.example.mywebquizengine.Repos.QuizRepository;
import com.example.mywebquizengine.Repos.TestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;


@Service
public class QuizService   {

    @Autowired
    private QuizRepository quizRepository;

    @Autowired
    private TestRepository testRepository;

    public void saveQuiz(List<Quiz> quizList) {
       // test.getQuizzes().get(0).setTitle(test.getQuizzes().get(0).getTitle().replace("<","|"));
        //test.setTitle(test.getTitle().replace("<","|"));
        //testRepository.save(test);
        for (int i = 0; i < quizList.size(); i++) {
            quizList.get(i).setTitle(quizList.get(i).getTitle().replace("<","|"));
            quizList.get(i).setText(quizList.get(i).getText().replace("<","|"));
            quizRepository.save(quizList.get(i));
        }

    }


    public void deleteQuiz(Long id) {
        if (quizRepository.findById(id).isPresent()) {
            quizRepository.deleteById(id);
            throw new ResponseStatusException(HttpStatus.NO_CONTENT);
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    public void updateQuiz(int id, String title, String text, ArrayList<String> options, ArrayList<Integer> answers) {

        quizRepository.updateQuizById(id, title, text);
        quizRepository.deleteAnswers(id);
        quizRepository.deleteOptions(id);
        options.forEach(option -> quizRepository.insertOptions(id, option));
        answers.forEach(answer -> quizRepository.insertAnswers(id, answer));
    }

    public Quiz findQuiz(Long id) {
        if (quizRepository.findById(id).isPresent()){
            return quizRepository.findById(id).get();
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    public Page<Quiz> getAllQuizzes(Integer page, Integer pageSize, String sortBy) {
        Pageable paging = PageRequest.of(page, pageSize, Sort.by(sortBy));
        return quizRepository.findAll(paging);
    }

    public Page<Quiz> getMyQuiz (String name, Integer page, Integer pageSize, String sortBy) {
        Pageable paging = PageRequest.of(page, pageSize, Sort.by(sortBy).descending());
        return quizRepository.getQuizForThis(name, paging);
    }

    public Quiz findQuizManually(Long quizId) {
        return quizRepository.find(quizId);
    }

    public Quiz findQuizProxy(Long quizId) {
        return quizRepository.getOne(quizId);
    }

    public String getTypeOfQuiz(Long quizId) {
        return quizRepository.findType(quizId);
    }
}
