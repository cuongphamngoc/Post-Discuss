package com.cuongpn.repository;

import com.cuongpn.entity.Comment;
import jakarta.persistence.NamedAttributeNode;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;



@Repository
public interface CommentRepository extends JpaRepository<Comment,Long> {
    @EntityGraph(attributePaths ={"createdBy","replies"})
    Page<Comment> findByPostIdAndParentIsNull(Long id, Pageable pageable);

    int countByPostId(Long postId);
    @EntityGraph(attributePaths ={"createdBy","replies"})

    Page<Comment> findByParent_Id(Long parentId, Pageable pageable);
}
