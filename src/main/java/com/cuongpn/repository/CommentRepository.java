package com.cuongpn.repository;

import com.cuongpn.entity.Comment;
import com.cuongpn.entity.Content;
import jakarta.persistence.NamedAttributeNode;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;


@Repository
public interface CommentRepository extends JpaRepository<Comment,Long> {
    long countByParent_Id(Long Id);
    @EntityGraph(attributePaths ={"createdBy","votes"})
    Page<Comment> findByParent_Id(Long parentId, Pageable pageable);
    @Query("SELECT c.parent.id, COUNT(c) FROM Comment c WHERE c.parent.id IN :parentIds GROUP BY c.parent.id")
    List<Object[]> findReplyCountByParentIds(@Param("parentIds") List<Long> parentIds);
}
