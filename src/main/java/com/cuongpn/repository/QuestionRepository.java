package com.cuongpn.repository;

import com.cuongpn.entity.Question;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;
@Repository
public interface QuestionRepository extends JpaRepository<Question, Long> {
    @EntityGraph(value = "Question.detail", type = EntityGraph.EntityGraphType.LOAD)

    Optional<Question> findBySlug(String slug);
}
