package com.cuongpn.service;

import com.cuongpn.dto.requestDTO.CreateAnswerDTO;
import com.cuongpn.dto.responeDTO.AnswerDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface AnswerService {

    AnswerDTO handleCreateAnswerRequest(Long questionId,CreateAnswerDTO createAnswerDTO);


    Page<AnswerDTO> handleGetAnswerRequest(Long questionId, Pageable pageable);

    void acceptAnswer(Long questionId, Long answerId);

    int getTotalAnswer(Long questionId);
}
