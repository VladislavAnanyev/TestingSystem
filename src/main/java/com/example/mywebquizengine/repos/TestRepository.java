package com.example.mywebquizengine.repos;


import com.example.mywebquizengine.model.projection.TestView;
import com.example.mywebquizengine.model.test.Test;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface TestRepository extends CrudRepository<Test, Long>,
        PagingAndSortingRepository<Test, Long>, JpaRepository<Test, Long> {

    List<TestView> findTestsByCourse_CourseId(Long courseId);

    List<TestView> findTestsByCourse_CourseIdAndCourse_Owner_UserId(Long courseId, Long userId);

    @Transactional
    @Query(nativeQuery = true, value = "DELETE FROM TESTS WHERE TEST_ID =:id")
    @Modifying
    void nativeDeleteTestById(Long id);


}
