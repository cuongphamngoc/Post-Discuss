package com.cuongpn.repository;

import com.cuongpn.entity.Post;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PostRepository extends JpaRepository<Post,Long> {


    @EntityGraph(attributePaths = {"users"})
    @Query("SELECT p FROM Post p WHERE p.id = :postId")
    Optional<Post> findPostWithUsers(@Param("postId") Long postId);

}
