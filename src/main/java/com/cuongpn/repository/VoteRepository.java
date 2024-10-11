package com.cuongpn.repository;

import com.cuongpn.entity.Post;
import com.cuongpn.entity.User;
import com.cuongpn.entity.Vote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface VoteRepository extends JpaRepository<Vote,Long> {

    Optional<Vote> findByCreatedByAndPost(User user, Post post);
}
