package com.example.mywebquizengine.Repos;

import com.example.mywebquizengine.Model.Test.Quiz;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;

@Repository
public interface QuizRepository extends CrudRepository<Quiz, Integer>, PagingAndSortingRepository<Quiz, Integer> {

    @Query(value = "SELECT * FROM QUIZZES u WHERE USER_USERNAME = :name", nativeQuery = true)
    Page<Quiz> getQuizForThis(String name, Pageable paging);

    @Modifying
    @Transactional
    @Query(value = "UPDATE QUIZZES_OPTIONS SET QUIZZES_OPTIONS.OPTIONS = :option WHERE QUIZZES_ID = :id AND QUIZZES_OPTIONS.OPTIONS = :oldOption",nativeQuery = true)
    void updateQuizOptionsById(Integer id, String option, String oldOption);

    @Modifying
    @Transactional
    @Query(value = "UPDATE QUIZZES_ANSWER SET ANSWER = :answer WHERE QUIZZES_ID = :id",nativeQuery = true)
    void updateQuizAnswerById(Integer id, ArrayList<Integer> answer);

    @Modifying
    @Transactional
    @Query(value = "UPDATE QUIZZES SET TITLE = :title, TEXT = :text WHERE ID = :id",nativeQuery = true)
    void updateQuizById(Integer id, String title, String text);



    @Modifying
    @Transactional
    @Query(value = "DELETE FROM QUIZZES_ANSWER WHERE QUIZZES_ID = :id", nativeQuery = true)
    void deleteAnswers(int id);

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM QUIZZES_OPTIONS WHERE QUIZZES_ID = :id", nativeQuery = true)
    void deleteOptions(int id);

    @Modifying
    @Query(value = "INSERT INTO QUIZZES_ANSWER(QUIZZES_ID, ANSWER) VALUES (:QUIZZES_ID, :ANSWER)", nativeQuery = true)
    @Transactional
    void insertAnswers(@Param("QUIZZES_ID") int QUIZZES_ID, @Param("ANSWER") Integer ANSWER);

    @Modifying
    @Query(value = "INSERT INTO QUIZZES_OPTIONS(QUIZZES_ID, OPTIONS) VALUES (:QUIZZES_ID, :OPTIONS)", nativeQuery = true)
    @Transactional
    void insertOptions(@Param("QUIZZES_ID") int QUIZZES_ID, @Param("OPTIONS") String OPTIONS);

    @Modifying
    @Transactional
    @Query(value = "CASCADE DELETE FROM QUIZZES WHERE QUIZZES_ID = :id", nativeQuery = true)
    void CustomDeleteById(int id);
}

