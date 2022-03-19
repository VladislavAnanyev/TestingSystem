package com.example.mywebquizengine.Repos;


import com.example.mywebquizengine.Model.Projection.TestView;
import com.example.mywebquizengine.Model.Test.Test;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TestRepository extends CrudRepository<Test, Integer>,
        PagingAndSortingRepository<Test, Integer>, JpaRepository<Test, Integer> {

    @Query(value = "SELECT * FROM TESTS u WHERE USERNAME = :name AND COURSE_ID = :courseId", nativeQuery = true)
    Page<TestView> findMyTestsInCourse(String name, Long courseId, Pageable paging);

    @Query(value = "SELECT * FROM TESTS u WHERE USERNAME = :name", nativeQuery = true)
    List<Test> getQuizForThisNoPaging(String name);

    @Query(nativeQuery = true, value = "SELECT * FROM TESTS INNER JOIN QUIZZES ON TESTS.ID = QUIZZES.TEST_ID\n" +
            "WHERE TESTS.ID =:id")
    Optional<Test> getTestByIds(Integer id);

    List<TestView> findTestsByCourse_CourseId(Long courseId);
}
