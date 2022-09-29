package com.example.mywebquizengine.Repos;

import com.example.mywebquizengine.Model.Projection.UserCommonView;
import com.example.mywebquizengine.Model.Projection.UserView;
import com.example.mywebquizengine.Model.Projection.UserViewForCourseInfo;
import com.example.mywebquizengine.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends CrudRepository<User, Long>, JpaRepository<User, Long> {

    UserCommonView findByEmail(String email);

    Optional<User> findUserEntityByEmail(String username);

    UserView findAllByEmail(String email);

    UserView findAllByUserId(Long userId);

    Optional<User> findUserByEmail(String email);

//    ProfileView findUserByUsernameOrderByUsernameAsc(String username);

    @Query(nativeQuery = true, value = """
            SELECT u.USER_ID as userId, u.EMAIL as email, u.LAST_NAME as lastName, 
            u.FIRST_NAME as firstName, u.AVATAR as avatar, CM.COURSE_ID as courseId 
            FROM COURSES JOIN COURSES_MEMBERS CM on COURSES.COURSE_ID = CM.COURSE_ID 
            join USERS U on U.USER_ID = CM.USER_ID 
            WHERE COURSES.COURSE_ID =:courseId
            """)
    List<UserViewForCourseInfo> findMembersByCourseAndMembers(Long courseId);

    @Modifying
    @Transactional
    @Query(nativeQuery = true, value = "SELECT * FROM USERS")
    void updateUserInfo(String firstname, String lastname, Long userId);

    @Modifying
    @Transactional
    @Query(value = "UPDATE USERS SET PASSWORD = :password, CHANGE_PASSWORD_CODE = :changePasswordCode WHERE EMAIL = :username", nativeQuery = true)
    void changePassword(String password, String username, String changePasswordCode);

    @Modifying
    @Transactional
    @Query(value = "UPDATE USERS SET AVATAR = :avatarName WHERE USER_ID = :userId", nativeQuery = true)
    void setAvatar(String avatarName, Long userId);


    @Query(value = "SELECT * FROM USERS WHERE ACTIVATION_CODE = :activationCode", nativeQuery = true)
    Optional<User> findByActivationCode(String activationCode);

    @Modifying
    @Transactional
    @Query(value = "UPDATE USERS SET status = true WHERE USER_ID = :userId", nativeQuery = true)
    void activateAccount(Long userId);

    /*@Modifying
    @Transactional
    @Query(value = "UPDATE USERS SET AVATAR = :avatar WHERE USERNAME = :username", nativeQuery = true)
    void updateAvatar(String username, String avatar);*/


    @Query(value = "SELECT * FROM USERS WHERE CHANGE_PASSWORD_CODE = :changePasswordCode", nativeQuery = true)
    Optional<User> findByChangePasswordCode(String changePasswordCode);


    @Modifying
    @Transactional
    @Query(value = "UPDATE USERS SET CHANGE_PASSWORD_CODE = :mes WHERE EMAIL = :username", nativeQuery = true)
    void setChangePasswordCode(String username, String mes);

    @Modifying
    @Transactional
    @Query(value = "UPDATE USERS SET ONLINE = :status WHERE USER_ID = :userId", nativeQuery = true)
    void setOnline(Long userId, String status);

    //@Modifying
    @Transactional
    @Query(value = "select ONLINE from USERS WHERE EMAIL = :username", nativeQuery = true)
    String getOnline(String username);

}
