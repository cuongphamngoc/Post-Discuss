package com.cuongpn.repository;



import com.cuongpn.entity.Article;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ArticleRepository extends JpaRepository<Article,Long> {
    @EntityGraph(value = "ArticleDetail",type = EntityGraph.EntityGraphType.LOAD)
    Optional<Article> findBySlug(String slug);


}
