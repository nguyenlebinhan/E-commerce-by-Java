package com.example.demo.Repository;

import com.example.demo.Entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;

public interface OrderRepo extends JpaRepository<Order,Long> {

    @Query("Select o FROM Order o WHERE o.email = ?1 AND o.id = ?2")
    Order findOrderByEmailAndOrderId(String email,Long cardId);

    List<Order>findALlByEmail(String emailId);
}
