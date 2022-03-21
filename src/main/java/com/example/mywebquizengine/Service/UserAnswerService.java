package com.example.mywebquizengine.Service;


import com.example.mywebquizengine.Model.Test.*;
import com.example.mywebquizengine.Model.User;
import com.example.mywebquizengine.Repos.UserQuizAnswerRepository;
import com.example.mywebquizengine.Repos.UserTestAnswerRepository;
import org.quartz.JobDataMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.transaction.Transactional;
import java.math.BigInteger;
import java.util.*;

@Service
public class UserAnswerService  {

    @Autowired
    private UserQuizAnswerRepository userQuizAnswerRepository;


    @Autowired
    private UserTestAnswerRepository userTestAnswerRepository;

    @Autowired
    private TestService testService;

    /*public void saveAnswer(UserQuizAnswer userQuizAnswer){
        userQuizAnswerRepository.save(userQuizAnswer);
    }*/

    //@Transactional
    public UserTestAnswer findLastUserTestAnswer(JobDataMap jobDataMap) {
        return userTestAnswerRepository.findLastUserAnswerEager(jobDataMap.getString("username")).get();
    }

    public UserTestAnswer findByUserAnswerId(Integer userAnswerId) {

        Optional<UserTestAnswer> userTestAnswer = userTestAnswerRepository.findByUserAnswerId(userAnswerId);
        if (userTestAnswer.isPresent()) {
            return userTestAnswer.get();
        } else throw new ResponseStatusException(HttpStatus.NOT_FOUND);

    }

    public UserTestAnswer findByUserAnswerIdProxy(Integer userAnswerId) {
        return userTestAnswerRepository.getOne(userAnswerId);
    }

    public void saveAnswer(UserTestAnswer userTestAnswer) {
        UserTestAnswer lastUserAnswer = userTestAnswerRepository.findById(userTestAnswer.getUserAnswerId()).get();
        lastUserAnswer.setCompletedAt(userTestAnswer.getCompletedAt());
        lastUserAnswer.setPercent(userTestAnswer.getPercent());
    }

    /*public void saveTempAnswer(UserQuizAnswer userQuizAnswer, String name, String type) {
        for (int i = 0; i < lastUserAnswer.getUserQuizAnswers().size(); i++) {
            if (type.equals("MULTIPLE")) {
            } else if (type.equals("STRING")) {
                StringUserAnswerQuiz answerQuiz = (StringUserAnswerQuiz) lastUserAnswer.getUserQuizAnswers().get(i);

                if (userQuizAnswer.getQuiz().getQuizId().equals(lastUserAnswer.getUserQuizAnswers().get(i).getQuiz().getQuizId())) {

                }
            }
        }

        //userTestAnswerRepository.save(lastUserAnswer);
        //lastUserAnswer.addUserQuizAnswer(userQuizAnswer);
    }*/

    /*public Page<UserQuizAnswer> getCompleted (String name, Integer page,
                                              Integer pageSize, String sortBy) {
        Pageable paging = PageRequest.of(page, pageSize, Sort.by(sortBy).descending());
        return userQuizAnswerRepository.getCompleteAnswersForUser(name, paging);
    }*/

    public Page<UserTestAnswer> getPageAnswersById(int id, Integer page, Integer pageSize, String sortBy) {
        Pageable paging = PageRequest.of(page, pageSize, Sort.by(sortBy).descending());
        return userTestAnswerRepository.getAnswersOnMyQuiz(id, paging);
    }

    @Transactional
    public UserTestAnswer getUserTestAnswerById(int id) {

        return userTestAnswerRepository.findById(id).get();
    }

    @Transactional
    public Map<BigInteger, Double> getAnswerStats(Integer id) {

        Test test = testService.findTest(id);
        ArrayList<Long> list = new ArrayList<>();

        for (Quiz quiz: test.getQuizzes()) {
            list.add(quiz.getQuizId());
        }

        List<Object[]> result = userQuizAnswerRepository.getAnswerStat(list);
        Map<BigInteger, Double> map = null;
        if(result != null && !result.isEmpty()){
            map = new HashMap<>();
            for (Object[] object : result) {
                map.put(((BigInteger)object[0]), (Double) object[1]);
            }
        }
        return map;

    }

    public Double getStatistics(Integer id, Integer answer) {
        return ((double) userQuizAnswerRepository.getTrueAnswers(id, answer)/(double) userQuizAnswerRepository.getCountById(id, answer)) * 100;
    }

    public ArrayList<Integer> getAnswersByTestId(Integer id) {
        return (ArrayList<Integer>) userTestAnswerRepository.getUserAnswersById(id);
    }

    public void saveStartAnswer(UserTestAnswer userTestAnswer) {
        userTestAnswerRepository.save(userTestAnswer);
    }

    public UserTestAnswer checkLastComplete(User user, String id) {

        if (userTestAnswerRepository.findLastUserTestAnswer(user.getUsername(), Integer.valueOf(id)).isPresent()) {
            return userTestAnswerRepository.findLastUserTestAnswer(user.getUsername(), Integer.valueOf(id)).get();
        } else return null;

    }

    /*public Integer getStat(Integer id) {
        return userTestAnswerRepository.getLastFalseById();
    }*/

   /* public void deleteAnswer(Integer id) {
        List<Integer> answers = userQuizAnswerRepository.getAnswerIdForQuiz(id);
        answers.forEach(answer -> userQuizAnswerRepository.deleteById(answer));
    }*/
}
