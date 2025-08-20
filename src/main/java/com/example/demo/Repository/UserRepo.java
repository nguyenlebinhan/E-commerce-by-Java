package com.example.demo.Repository;

import com.example.demo.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepo extends JpaRepository<User,Long>{

    @Query("Select u From User u JOIN FETCH u.addresses a where a.addressId=?1")
    List<User> findByAddress(Long addressId);

    Optional<User>findByEmail(String email);





}
