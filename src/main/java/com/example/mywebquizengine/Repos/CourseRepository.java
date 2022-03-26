package com.example.mywebquizengine.Repos;

import com.example.mywebquizengine.Model.Course;
import com.example.mywebquizengine.Model.Projection.CourseView;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CourseRepository extends CrudRepository<Course, Long> {
    @Query(nativeQuery = true, value = "SELECT course_id AS courseId, name, owner_email AS ownerEmail FROM COURSES")
    List<CourseView> findAllCourses();

    @Query(nativeQuery = true, value = "SELECT course_id AS courseId, name, owner_email AS ownerEmail FROM COURSES JOIN COURSES_MEMBERS CM on COURSES.COURSE_ID = CM.COURSES_COURSE_ID WHERE CM.MEMBERS_USERNAME = :name")
    List<CourseView> findCourseByMembers(String name);
}
