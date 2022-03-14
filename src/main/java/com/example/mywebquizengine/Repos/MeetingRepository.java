package com.example.mywebquizengine.Repos;

import com.example.mywebquizengine.Model.Geo.Meeting;

import com.example.mywebquizengine.Model.Projection.MeetingView;
import com.example.mywebquizengine.Model.Projection.MeetingViewCustomQuery;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface MeetingRepository extends CrudRepository<Meeting, Long> {

    @Query(value = """
            SELECT *
            FROM MEETINGS
            WHERE (FIRST_USER_USERNAME = :firstUsername\s
              AND SECOND_USER_USERNAME = :secondUsername or FIRST_USER_USERNAME = :secondUsername\s
                AND SECOND_USER_USERNAME = :firstUsername) and TIME BETWEEN timestampadd(DAY, 0,:date)
                 AND timestampadd(DAY, 1,:date)""", nativeQuery = true)
    List<Meeting> getMeetings(String firstUsername, String secondUsername, String date);



    @Query(value = """
            SELECT MEETINGS.id, lat,lng,time,f.username as first_username,up.URL as first_avatar, 
            F.first_name as first_firstName,F.last_name as first_lastName,S.USERNAME as second_username,
            UP2.url as second_avatar,S.FIRST_NAME as second_firstName,S.LAST_NAME  as second_lastName
                 FROM MEETINGS
                          LEFT OUTER JOIN USERS F on MEETINGS.FIRST_USER_USERNAME = F.USERNAME
                          LEFT OUTER JOIN
                              (SELECT *
                               FROM USERS_PHOTOS
                                        JOIN (SELECT min(id) AS idMin
                                              FROM USERS_PHOTOS
                                              GROUP BY USER_USERNAME) ON ID = idMin) AS UP on F.USERNAME = UP.USER_USERNAME
                          LEFT OUTER JOIN USERS S ON MEETINGS.SECOND_USER_USERNAME = S.USERNAME
                          LEFT OUTER JOIN
                              (SELECT *
                               FROM USERS_PHOTOS
                                        JOIN (SELECT min(id) AS idMin
                                              FROM USERS_PHOTOS
                                              GROUP BY USER_USERNAME) ON ID = idMin) AS UP2 on S.USERNAME = UP.USER_USERNAME
                 WHERE (FIRST_USER_USERNAME = :username
                            AND SECOND_USER_USERNAME != :username or FIRST_USER_USERNAME != :username
                            AND SECOND_USER_USERNAME = :username)
                   and TIME like concat(:date, '%')""", nativeQuery = true)
    List<MeetingViewCustomQuery> getMyMeetings(String username, String date);

    MeetingView findMeetingById(Long id);

/*    MeetingForApiViewCustomQuery getMeetingById(Long id);*/

}
