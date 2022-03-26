package com.example.mywebquizengine.Repos;

import com.example.mywebquizengine.Model.Test.UserTestAnswer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;
import java.util.Optional;

public interface UserTestAnswerRepository extends CrudRepository<UserTestAnswer, Long>,
        PagingAndSortingRepository<UserTestAnswer, Long>, JpaRepository<UserTestAnswer, Long> {


    @Query(value = "SELECT * FROM USER_TEST_ANSWERS u WHERE TEST_ID = :id", nativeQuery = true)
    Page<UserTestAnswer> getAnswersOnMyQuiz(Long id, Pageable paging);

    @Query(value = "SELECT USER_ANSWER_ID FROM USER_TEST_ANSWERS u WHERE TEST_ID = :id", nativeQuery = true)
    List<Long> getUserAnswersById(Long id);

    @Query(value = "SELECT TOP 1 * FROM USER_TEST_ANSWERS WHERE USERNAME = :username ORDER BY START_AT DESC", nativeQuery = true)
    Optional<UserTestAnswer> findLastUserAnswer(String username);

    @Query(value = "SELECT TOP 1 * FROM USER_TEST_ANSWERS WHERE USERNAME = :username ORDER BY START_AT DESC", nativeQuery = true)
    Optional<UserTestAnswer> findLastUserAnswerEager(String username);

    @Query(value = "SELECT TOP 1 * FROM USER_TEST_ANSWERS WHERE USERNAME = :username AND TEST_ID = :test_id ORDER BY START_AT DESC", nativeQuery = true)
    Optional<UserTestAnswer> findLastUserTestAnswer(String username, Long test_id);

    Optional<UserTestAnswer> findByUserAnswerId(Long userAnswerId);

    @Query(nativeQuery = true, value = "SELECT COUNT(*) FROM User_Test_Answers WHERE percent > 80 AND username =:username AND test_id =:testId")
    Long getCountOfCompleteAttempts(String username, Long testId);

    /*@Query(value = "SELECT TOP 1 COUNT(*) FROM USER_QUIZ_ANSWERS Q LEFT OUTER JOIN USER_TEST_ANSWERS T ON Q.USER_ANSWER_ID = T.USER_ANSWER_ID WHERE TEST_ID = 8907 AND USER_USERNAME = 'application' AND  STATUS = 'FALSE' GROUP BY T.COMPLETED_AT ORDER BY COMPLETED_AT DESC", nativeQuery = true)
    Integer getLastFalseById();*/

    @Query(nativeQuery = true, value = """
            SELECT ROUND((SELECT COUNT(*)
                               FROM (SELECT DISTINCT T.TEST_ID
                                     FROM USER_TEST_ANSWERS
                                              JOIN TESTS T on T.TEST_ID = USER_TEST_ANSWERS.TEST_ID
                                              JOIN COURSES C on C.COURSE_ID = T.COURSE_ID
                                     WHERE PERCENT >= 80)) / CAST (COUNT(*) + 0.001 as float) * 100, 0)
            FROM TESTS WHERE COURSE_ID =:courseId
            """)
    Integer getPercentageOfComplete(Long courseId);

    @Query(nativeQuery = true, value = """
            SELECT COUNT(*) FROM USER_TEST_ANSWERS WHERE TEST_ID =:testId AND USERNAME =:username
            """)
    Integer getUserAttempts(Long testId, String username);
}
