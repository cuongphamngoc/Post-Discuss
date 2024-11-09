package com.cuongpn.repository;

import com.cuongpn.entity.Series;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SeriesRepository extends JpaRepository<Series, Long> {
    @EntityGraph(value = "ArticleDetail",type = EntityGraph.EntityGraphType.LOAD)
    Optional<Series> findBySlug(String slug);

    List<Series> findByCreatedBy_Id(Long id);
}
