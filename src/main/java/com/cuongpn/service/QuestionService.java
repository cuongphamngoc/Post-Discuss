package com.cuongpn.service;

import com.cuongpn.dto.requestDTO.CreateQuestionDTO;
import com.cuongpn.dto.responeDTO.QuestionDTO;
import com.cuongpn.dto.responeDTO.QuestionDetailDTO;
import com.cuongpn.entity.Question;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface QuestionService {
    Question createNewQuestion(CreateQuestionDTO createQuestionDTO);
    Page<Question> findAll(Pageable pageable);

    QuestionDetailDTO findQuestionDetailBySlug(String slug,Pageable pageable);

    Page<QuestionDTO> findUnsolvedQuestions(Pageable pageable);
}
