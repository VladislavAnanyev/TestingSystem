package com.example.mywebquizengine.Repos;

import com.example.mywebquizengine.Model.Test.UserQuizAnswer;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;


@Repository
public interface UserQuizAnswerRepository extends CrudRepository<UserQuizAnswer, Long>,
        PagingAndSortingRepository<UserQuizAnswer, Long> {

    String query = """
            SELECT "EMP_ID", "LAST_NAME" FROM "EMPLOYEE_TB"
            WHERE "CITY" = 'INDIANAPOLIS'
            ORDER BY "EMP_ID", "LAST_NAME";
            """;

    @Query(value = """
            SELECT QUIZ_ID,
            ROUND(((SELECT cast(COUNT(1) as FLOAT)
            FROM USER_QUIZ_ANSWERS AS B
            join "USER_TEST_ANSWERS" UTA on UTA."USER_ANSWER_ID" = B."USER_ANSWER_ID"
            join "USERS" U on UTA."USER_ID" = U."USER_ID" join "GROUPS" G on U."GROUP_ID" = G."GROUP_ID"
            WHERE B.STATUS = TRUE and G.GROUP_ID =:groupId
            AND B.QUIZ_ID = C.QUIZ_ID )/(SELECT COUNT(1) + 0.001
            FROM USER_QUIZ_ANSWERS AS B
            join USER_TEST_ANSWERS UTA on UTA.USER_ANSWER_ID = B.USER_ANSWER_ID
            join "USERS" U on UTA."USER_ID" = U."USER_ID"
            join "GROUPS" G on U."GROUP_ID" = G."GROUP_ID"
            where B.QUIZ_ID = C.QUIZ_ID and UTA.COMPLETED_AT is not null and G.GROUP_ID =:groupId)) * 100 , 1)
            FROM USER_QUIZ_ANSWERS AS C WHERE QUIZ_ID IN (:quizzes)  GROUP BY QUIZ_ID
            """, nativeQuery = true)
    List<Object[]> getAnswerStat(ArrayList<Long> quizzes, Long groupId);

    @Query(value = """
            SELECT QUIZ_ID,
            ROUND(((SELECT cast(COUNT(1) as FLOAT)
            FROM USER_QUIZ_ANSWERS AS B  WHERE STATUS = TRUE
            AND B.QUIZ_ID = C.QUIZ_ID )/(SELECT COUNT(1) + 0.001
            FROM USER_QUIZ_ANSWERS AS B
            join USER_TEST_ANSWERS UTA on UTA.USER_ANSWER_ID = B.USER_ANSWER_ID
            where B.QUIZ_ID = C.QUIZ_ID and UTA.COMPLETED_AT is not null)) * 100 , 1)
            FROM USER_QUIZ_ANSWERS AS C WHERE QUIZ_ID IN (:quizzes)  GROUP BY QUIZ_ID
            """, nativeQuery = true)
    List<Object[]> getAnswerStat(ArrayList<Long> quizzes);

    @Query(value = """
            SELECT QUIZ_ID, (SELECT AVG(DURATION)
            FROM USER_QUIZ_ANSWERS AS B
            WHERE B.QUIZ_ID = C.QUIZ_ID and B.DURATION > 0)
            FROM USER_QUIZ_ANSWERS AS C
            JOIN "USER_TEST_ANSWERS" UTA on UTA."USER_ANSWER_ID" = C."USER_ANSWER_ID"
            JOIN "USERS" U on U."USER_ID" = UTA."USER_ID"
            WHERE QUIZ_ID IN (:quizzes) AND GROUP_ID =:groupId
            GROUP BY QUIZ_ID
            """, nativeQuery = true)
    List<Object[]> getTimeAnswerStat(ArrayList<Long> quizzes, Long groupId);

    @Query(value = """
            SELECT QUIZ_ID, (SELECT AVG(DURATION)
            FROM USER_QUIZ_ANSWERS AS B
            WHERE B.QUIZ_ID = C.QUIZ_ID and B.DURATION > 0)
            FROM USER_QUIZ_ANSWERS AS C
            WHERE QUIZ_ID IN (:quizzes)
            GROUP BY QUIZ_ID
            """, nativeQuery = true)
    List<Object[]> getTimeAnswerStat(ArrayList<Long> quizzes);

    @Query(value = "SELECT COUNT(*) FROM USER_QUIZ_ANSWERS Q LEFT OUTER JOIN USER_TEST_ANSWERS T ON Q.USER_ANSWER_ID = T.USER_ANSWER_ID WHERE STATUS = TRUE AND TEST_ID = :id AND T.USER_ANSWER_ID = :answer", nativeQuery = true)
    Long getTrueAnswers(Integer id, Integer answer);

    @Query(value = "SELECT COUNT(*) FROM USER_QUIZ_ANSWERS Q LEFT OUTER JOIN USER_TEST_ANSWERS T ON Q.USER_ANSWER_ID = T.USER_ANSWER_ID WHERE TEST_ID = :id AND T.USER_ANSWER_ID = :answer", nativeQuery = true)
    Long getCountById(Integer id, Integer answer);

}

