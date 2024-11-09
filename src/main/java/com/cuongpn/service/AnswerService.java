package com.cuongpn.service;

import com.cuongpn.dto.requestDTO.CreateAnswerDTO;
import com.cuongpn.dto.responeDTO.AnswerDTO;
import com.cuongpn.security.services.UserPrincipal;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface AnswerService {

    AnswerDTO handleCreateAnswerRequest(Long questionId, CreateAnswerDTO createAnswerDTO);


    Page<AnswerDTO> handleGetAnswerRequest(Long questionId, Pageable pageable);

    void acceptAnswer(Long questionId, Long answerId, UserPrincipal userPrincipal);

    void unAcceptAnswer(Long questionId, UserPrincipal userPrincipal);
}
