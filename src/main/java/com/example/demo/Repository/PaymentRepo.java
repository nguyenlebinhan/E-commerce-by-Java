package com.example.demo.Repository;

import com.example.demo.Entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentRepo extends JpaRepository<Payment,Long> {

}
