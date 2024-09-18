package com.cuongpn.repository;

import com.cuongpn.entity.ArticleComment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentRepository extends JpaRepository<ArticleComment,Long> {
}
