package com.example.mywebquizengine.Repos;

import com.example.mywebquizengine.Model.Test.Test;
import com.example.mywebquizengine.Model.Test.UserTestAnswer;
import com.example.mywebquizengine.Model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import javax.persistence.criteria.CriteriaBuilder;
import java.util.Calendar;
import java.util.List;
import java.util.Optional;

public interface UserTestAnswerRepository extends CrudRepository<UserTestAnswer, Integer>,
        PagingAndSortingRepository<UserTestAnswer,Integer>, JpaRepository<UserTestAnswer, Integer> {


    @Query(value = "SELECT * FROM USER_TEST_ANSWERS u WHERE TEST_ID = :id", nativeQuery = true)
    Page<UserTestAnswer> getAnswersOnMyQuiz(int id, Pageable paging);

    @Query(value = "SELECT USER_ANSWER_ID FROM USER_TEST_ANSWERS u WHERE TEST_ID = :id", nativeQuery = true)
    List<Integer> getUserAnswersById(int id);

    @Query(value = "SELECT TOP 1 * FROM USER_TEST_ANSWERS WHERE USERNAME = :username ORDER BY START_AT DESC", nativeQuery = true)
    Optional<UserTestAnswer> findLastUserAnswer(String username);

    @Query(value = "SELECT TOP 1 * FROM USER_TEST_ANSWERS WHERE USERNAME = :username ORDER BY START_AT DESC", nativeQuery = true)
    Optional<UserTestAnswer> findLastUserAnswerEager(String username);

    @Query(value = "SELECT TOP 1 * FROM USER_TEST_ANSWERS WHERE USERNAME = :username AND TEST_ID = :test_id ORDER BY START_AT DESC", nativeQuery = true)
    Optional<UserTestAnswer> findLastUserTestAnswer(String username, Integer test_id);

    Optional<UserTestAnswer> findByUserAnswerId(int userAnswerId);



    /*@Query(value = "SELECT TOP 1 COUNT(*) FROM USER_QUIZ_ANSWERS Q LEFT OUTER JOIN USER_TEST_ANSWERS T ON Q.USER_ANSWER_ID = T.USER_ANSWER_ID WHERE TEST_ID = 8907 AND USER_USERNAME = 'application' AND  STATUS = 'FALSE' GROUP BY T.COMPLETED_AT ORDER BY COMPLETED_AT DESC", nativeQuery = true)
    Integer getLastFalseById();*/


}
