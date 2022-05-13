package com.example.mywebquizengine.Repos;

import com.example.mywebquizengine.Model.Projection.AnswerViewForInfo;
import com.example.mywebquizengine.Model.Projection.UserTestAnswerView;
import com.example.mywebquizengine.Model.Test.UserTestAnswer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserTestAnswerRepository extends CrudRepository<UserTestAnswer, Long>,
        PagingAndSortingRepository<UserTestAnswer, Long>, JpaRepository<UserTestAnswer, Long> {


    @Query(value = """
            select G.GROUP_NAME as groupName,  u.EMAIL, u.FIRST_NAME as firstName, u.LAST_NAME as lastName,
                   A.USER_ANSWER_ID as userAnswerId, A.COMPLETED_AT as completedAt, A.PERCENT, A.START_AT as startAt,
                   A.TEST_ID, A.USER_ID as userId
                        from  USERS u join USER_TEST_ANSWERS A on (select top 1 uta.USER_ANSWER_ID
                                                                    from USER_TEST_ANSWERS uta
                                                                     where uta.USER_ID = U.USER_ID and TEST_ID = :id order by PERCENT desc)
                                                                     = A.USER_ANSWER_ID join GROUPS G on G.GROUP_ID = u.GROUP_ID
            """, nativeQuery = true)
    List<AnswerViewForInfo> getAnswersOnMyQuiz(Long id);

    @Query(value = """
            select G.GROUP_NAME as groupName,  u.EMAIL, u.FIRST_NAME as firstName, u.LAST_NAME as lastName,
                   A.USER_ANSWER_ID as userAnswerId, A.COMPLETED_AT as completedAt, A.PERCENT, A.START_AT as startAt,
                   A.TEST_ID, A.USER_ID as userId
                        from  USERS u join USER_TEST_ANSWERS A on (select top 1 uta.USER_ANSWER_ID
                                                                    from USER_TEST_ANSWERS uta
                                                                     where uta.USER_ID = U.USER_ID and TEST_ID = :id order by PERCENT desc)
                                                                     = A.USER_ANSWER_ID join GROUPS G on G.GROUP_ID = u.GROUP_ID 
                                                                     where G.GROUP_ID =:groupId
            """, nativeQuery = true)
    List<AnswerViewForInfo> getAnswersOnMyQuiz(Long id, Long groupId);

    @Query(value = "SELECT USER_ANSWER_ID FROM USER_TEST_ANSWERS u WHERE TEST_ID = :id", nativeQuery = true)
    List<Long> getUserAnswersById(Long id);

    @Query(value = "SELECT TOP 1 * FROM USER_TEST_ANSWERS WHERE USER_ID = :userId ORDER BY START_AT DESC", nativeQuery = true)
    Optional<UserTestAnswer> findLastUserAnswer(Long userId);

    @Query(value = "SELECT TOP 1 * FROM USER_TEST_ANSWERS WHERE USER_ID = :userId ORDER BY START_AT DESC", nativeQuery = true)
    Optional<UserTestAnswer> findLastUserAnswerEager(Long userId);

    @Query(value = "SELECT TOP 1 * FROM USER_TEST_ANSWERS WHERE user_id = :userId AND TEST_ID = :test_id ORDER BY START_AT DESC", nativeQuery = true)
    Optional<UserTestAnswer> findLastUserTestAnswer(Long userId, Long test_id);

    Optional<UserTestAnswer> findByUserAnswerId(Long userAnswerId);

    @Query(nativeQuery = true, value = "SELECT COUNT(*) FROM User_Test_Answers join TESTS T on T.TEST_ID = USER_TEST_ANSWERS.TEST_ID WHERE USER_TEST_ANSWERS.PERCENT > t.passing_score AND user_id =:userId AND USER_TEST_ANSWERS.test_id =:testId")
    Long getCountOfCompleteAttempts(Long userId, Long testId);

    /*@Query(value = "SELECT TOP 1 COUNT(*) FROM USER_QUIZ_ANSWERS Q LEFT OUTER JOIN USER_TEST_ANSWERS T ON Q.USER_ANSWER_ID = T.USER_ANSWER_ID WHERE TEST_ID = 8907 AND USER_USERNAME = 'application' AND  STATUS = 'FALSE' GROUP BY T.COMPLETED_AT ORDER BY COMPLETED_AT DESC", nativeQuery = true)
    Integer getLastFalseById();*/

    @Query(nativeQuery = true, value = """
            SELECT ROUND((SELECT COUNT(*)
                               FROM (SELECT DISTINCT T.TEST_ID
                                     FROM USER_TEST_ANSWERS
                                              JOIN TESTS T on T.TEST_ID = USER_TEST_ANSWERS.TEST_ID
                                              JOIN COURSES C on C.COURSE_ID = T.COURSE_ID
                                     WHERE USER_TEST_ANSWERS.PERCENT >= T.PASSING_SCORE and c.OWNER_USER_ID =:userId) as success) / CAST (COUNT(*) + 0.001 as float) * 100)
            FROM TESTS WHERE COURSE_ID =:courseId
            """)
    Integer getPercentageOfComplete(Long courseId, Long userId);

    @Query(nativeQuery = true, value = """
            SELECT COUNT(*) FROM USER_TEST_ANSWERS WHERE TEST_ID =:testId AND USER_ID =:userId
            """)
    Integer getUserAttempts(Long testId, Long userId);

    @Query(nativeQuery = true, value = """
            SELECT FIRST_NAME as firstName, LAST_NAME as lastName, UQA.STATUS,
                   PERCENT, USER_TEST_ANSWERS.USER_ANSWER_ID as answerId
            FROM USER_TEST_ANSWERS
            JOIN USER_QUIZ_ANSWERS UQA on USER_TEST_ANSWERS.USER_ANSWER_ID = UQA.USER_ANSWER_ID
            JOIN USERS U on U.USER_ID = USER_TEST_ANSWERS.USER_ID
            WHERE PERCENT IS NOT NULL and TEST_ID =:testId

            """)
    List<UserTestAnswerView> getAnswerStat(Long testId);

    List<UserTestAnswerView> findAllByTestTestIdAndPercentIsNotNullAndUserGroupGroupId(Long testId, Long groupId);

    List<UserTestAnswerView> findAllByTestTestIdAndPercentIsNotNull(Long testId);
}
