package com.example.mywebquizengine.Repos;

import com.example.mywebquizengine.Model.Projection.ProfileView;
import com.example.mywebquizengine.Model.Projection.UserCommonView;
import com.example.mywebquizengine.Model.Projection.UserView;
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
public interface UserRepository extends CrudRepository<User, String>, JpaRepository<User, String> {
    @Override
    Optional<User> findById(String s);

    UserCommonView findByEmail(String email);

    UserView findAllByEmail(String email);

    Optional<User> findUserByEmail(String email);

//    ProfileView findUserByUsernameOrderByUsernameAsc(String username);

    @Modifying
    @Transactional
    @Query(value = "UPDATE USERS SET FIRST_NAME = :firstname, LAST_NAME = :lastname WHERE USERNAME = :username", nativeQuery = true)
    void updateUserInfo(String firstname, String lastname, String username);

    @Modifying
    @Transactional
    @Query(value = "UPDATE USERS SET PASSWORD = :password, CHANGE_PASSWORD_CODE = :changePasswordCode WHERE EMAIL = :username", nativeQuery = true)
    void changePassword(String password, String username, String changePasswordCode);

    @Modifying
    @Transactional
    @Query(value = "UPDATE USERS SET AVATAR = :avatarName WHERE USERNAME = :username", nativeQuery = true)
    void setAvatar(String avatarName, String username);


    @Query(value = "SELECT * FROM USERS WHERE ACTIVATION_CODE = :activationCode",nativeQuery = true )
    Optional<User> findByActivationCode(String activationCode);

    @Modifying
    @Transactional
    @Query(value = "UPDATE USERS SET status = true WHERE USERNAME = :username", nativeQuery = true)
    void activateAccount(String username);

    /*@Modifying
    @Transactional
    @Query(value = "UPDATE USERS SET AVATAR = :avatar WHERE USERNAME = :username", nativeQuery = true)
    void updateAvatar(String username, String avatar);*/


    @Query(value = "SELECT * FROM USERS WHERE CHANGE_PASSWORD_CODE = :changePasswordCode", nativeQuery = true )
    Optional<User> findByChangePasswordCode(String changePasswordCode);


    @Modifying
    @Transactional
    @Query(value = "UPDATE USERS SET CHANGE_PASSWORD_CODE = :mes WHERE EMAIL = :username", nativeQuery = true)
    void setChangePasswordCode(String username, String mes);

    @Modifying
    @Transactional
    @Query(value = "UPDATE USERS SET ONLINE = :status WHERE USERNAME = :username", nativeQuery = true)
    void setOnline(String username, String status);

    //@Modifying
    @Transactional
    @Query(value = "select ONLINE from USERS WHERE EMAIL = :username", nativeQuery = true)
    String getOnline(String username);

}
