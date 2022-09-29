package com.example.mywebquizengine.repos;

import com.example.mywebquizengine.model.Course;
import com.example.mywebquizengine.model.projection.CourseView;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CourseRepository extends CrudRepository<Course, Long> {
    @Query(nativeQuery = true,
            value = "SELECT course_id AS courseId, COURSE_NAME as name, IMAGE, owner_user_id AS ownerUserId FROM COURSES")
    List<CourseView> findAllCourses();

    @Query(nativeQuery = true, value = """
            SELECT COURSES.course_id AS courseId, COURSE_NAME as name, image, owner_user_id AS ownerUserId
            FROM COURSES JOIN COURSES_MEMBERS CM on COURSES.COURSE_ID = CM.COURSE_ID
            WHERE CM.USER_ID = :userId
            """)
    List<CourseView> findCourseByMembers(Long userId);

}
