package com.microservices.bits.assignment.authservice.userRepo;

import com.microservices.bits.assignment.authservice.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepo extends JpaRepository<User,String> {
    Optional<User> findById(String mobileno);
}
