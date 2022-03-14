package com.example.mywebquizengine.Repos;

import com.example.mywebquizengine.Model.Projection.ReceivedRequestView;

import com.example.mywebquizengine.Model.Projection.SentRequestView;
import com.example.mywebquizengine.Model.Request;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

public interface RequestRepository extends CrudRepository<Request, Long>, JpaRepository<Request, Long> {

    ReceivedRequestView findRequestById(Long id);

    ArrayList<ReceivedRequestView> findAllByToUsernameAndStatus(String username, String status);

    ArrayList<SentRequestView> findAllBySenderUsernameAndStatus(String username, String status);

    @Modifying
    @Transactional
    @Query(nativeQuery = true, value = "UPDATE REQUESTS SET STATUS = :status WHERE ID = :id")
    void updateStatus(Long id, String status);
}
