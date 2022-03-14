package com.example.mywebquizengine.Repos;

import com.example.mywebquizengine.Model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends CrudRepository<Order, Integer>, JpaRepository<Order, Integer> {

}
