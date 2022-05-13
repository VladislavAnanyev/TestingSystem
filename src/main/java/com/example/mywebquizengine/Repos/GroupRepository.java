package com.example.mywebquizengine.Repos;

import com.example.mywebquizengine.Model.Group;
import com.example.mywebquizengine.Model.Projection.GroupView;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface GroupRepository extends CrudRepository<Group, Long> {

    Optional<Group> findByGroupName(String groupName);

    @Query(nativeQuery = true, value = """
                SELECT DISTINCT GROUPS.GROUP_ID as groupId, GROUP_NAME as groupName
                FROM GROUPS JOIN USERS U on GROUPS.GROUP_ID = U.GROUP_ID
                            join "COURSES_MEMBERS" CM on U."USER_ID" = CM."USER_ID"
                WHERE COURSE_ID =:courseId
            """)
    List<GroupView> findGroupsByCourseId(Long courseId);

}
