package com.cuongpn.repository;



import com.cuongpn.entity.Article;
import com.cuongpn.entity.Series;
import com.cuongpn.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
public interface ArticleRepository extends JpaRepository<Article,Long> {
    @EntityGraph(value = "ArticleDetail",type = EntityGraph.EntityGraphType.LOAD)
    Optional<Article> findBySlug(String slug);
    @EntityGraph(value = "ArticleDetail",type = EntityGraph.EntityGraphType.LOAD)

    @Query("SELECT a FROM Article a WHERE a.createdBy IN :followingUsers ORDER BY a.createdDate DESC")
    Page<Article> findLatestArticlesByFollowingUsers(@Param("followingUsers") Set<User> followingUsers, Pageable pageable);
    @EntityGraph(value = "ArticleDetail",type = EntityGraph.EntityGraphType.LOAD)
    Page<Article> findAll(Pageable pageable);

    Page<Article> findBySeries(Series series,Pageable pageable);

    List<Article> findByCreatedBy_IdAndSeriesIsNull(Long userId);
    @EntityGraph(value = "ArticleDetail",type = EntityGraph.EntityGraphType.LOAD)

    Page<Article> findByIsFeaturesTrue(Pageable pageable);
}
