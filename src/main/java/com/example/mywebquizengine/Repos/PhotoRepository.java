package com.example.mywebquizengine.Repos;

import com.example.mywebquizengine.Model.Photo;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import javax.transaction.Transactional;
import java.util.List;

public interface PhotoRepository extends CrudRepository<Photo, Long> {

    @Query(nativeQuery = true, value = "SELECT * FROM USERS_PHOTOS WHERE USER_USERNAME =:user_username ORDER BY POSITION")
    List<Photo> findByUser_Username(String user_username);

    @Query(nativeQuery = true, value = "SELECT * FROM USERS_PHOTOS WHERE USER_USERNAME =:username AND POSITION =:pos")
    Photo findByUsernameAndPos(String username, Integer pos);

    @Modifying
    @Transactional
    @Query(nativeQuery = true, value = "UPDATE USERS_PHOTOS SET POSITION = :i WHERE ID=:id")
    void updatePosition(Long id, int i);
}
