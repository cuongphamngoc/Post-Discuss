package com.cuongpn.repository;

import com.cuongpn.entity.User;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.Set;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {

    Optional<User> findByEmailAndIsVerification(String username,boolean active);
    @EntityGraph(attributePaths = {"roles"})
    Optional<User> findByEmail(String email);

    Optional<User> findByVerificationToken(String token);

    @Query("SELECT u.following FROM User u WHERE u.id = :userId")
    Set<User> findFollowingByUserId(@Param("userId") Long userId);
    @EntityGraph(attributePaths = {"roles"})
    Optional<User> findByProviderAndProviderId(String provider, String providerId);

}
