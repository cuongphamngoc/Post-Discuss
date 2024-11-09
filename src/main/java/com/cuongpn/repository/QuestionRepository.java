package com.cuongpn.repository;

import com.cuongpn.entity.Question;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.Optional;
@Repository
public interface QuestionRepository extends JpaRepository<Question, Long> {
    @EntityGraph(value = "QuestionDetail", type = EntityGraph.EntityGraphType.LOAD)

    Optional<Question> findBySlug(String slug);
    @Query(value = "Select q from Question q where q.acceptedAnswer IS NULL")
    Page<Question> findUnsolvedQuestions(Pageable pageable);
    @EntityGraph(value = "QuestionDetail", type = EntityGraph.EntityGraphType.LOAD)

    Page<Question> findAll(Pageable pageable);
}
