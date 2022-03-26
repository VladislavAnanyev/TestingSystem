package com.example.mywebquizengine.Repos;


import com.example.mywebquizengine.Model.Projection.TestView;
import com.example.mywebquizengine.Model.Test.Test;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Repository
public interface TestRepository extends CrudRepository<Test, Long>,
        PagingAndSortingRepository<Test, Long>, JpaRepository<Test, Long> {

    @Query(value = "SELECT TEST_ID as testId, DESCRIPTION, DURATION, COURSE_ID as courseId, USERNAME FROM TESTS u WHERE USERNAME = :name AND COURSE_ID = :courseId", nativeQuery = true)
    List<TestView> findMyTestsInCourse(String name, Long courseId);

    @Query(value = "SELECT * FROM TESTS u WHERE USERNAME = :name", nativeQuery = true)
    List<Test> getQuizForThisNoPaging(String name);

    @Query(nativeQuery = true, value = "SELECT * FROM TESTS INNER JOIN QUIZZES ON TESTS.ID = QUIZZES.TEST_ID\n" +
            "WHERE TESTS.ID =:id")
    Optional<Test> getTestByIds(Integer id);

    List<TestView> findTestsByCourse_CourseId(Long courseId);

    List<TestView> findTestsByCourse_CourseIdAndCourse_Owner_Email(Long courseId, String name);

    @Transactional
    @Query(nativeQuery = true, value = "DELETE FROM TESTS WHERE TEST_ID =:id")
    @Modifying
    void nativeDeleteTestById(Long id);


}
