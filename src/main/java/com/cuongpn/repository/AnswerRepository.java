package com.cuongpn.repository;

import com.cuongpn.entity.Answer;
import com.cuongpn.entity.Question;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AnswerRepository extends JpaRepository<Answer,Long> {

    @EntityGraph(value = "answerDetail")
    Page<Answer> findByQuestion_Slug(String slug, Pageable pageable);
    @EntityGraph(value = "answerDetail")
    Page<Answer> findByQuestion(Question question, Pageable pageable);
    @EntityGraph(value = "answerDetail")
    Page<Answer> findByQuestionAndIdNot(Question question, Long answerId, Pageable pageable);

    int countByQuestion_Id(Long id);
}
