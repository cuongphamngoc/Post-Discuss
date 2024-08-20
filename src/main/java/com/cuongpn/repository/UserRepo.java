package com.cuongpn.repository;

import com.cuongpn.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public interface UserRepo extends JpaRepository<User,Integer> {

    Optional<User> findByEmailAndIsVerification(String username,boolean active);

    Optional<User> findByEmail(String email);

    Optional<User> findByVerificationToken(String token);

}
