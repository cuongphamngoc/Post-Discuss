package com.cuongpn.service.Impl;

import com.cuongpn.dto.requestDTO.CreateAnswerDTO;
import com.cuongpn.dto.responeDTO.AnswerDTO;
import com.cuongpn.entity.Answer;
import com.cuongpn.entity.Question;
import com.cuongpn.exception.AppException;
import com.cuongpn.exception.ErrorCode;
import com.cuongpn.mapper.AnswerMapper;
import com.cuongpn.repository.AnswerRepository;
import com.cuongpn.repository.QuestionRepository;
import com.cuongpn.security.services.UserPrincipal;
import com.cuongpn.service.AnswerService;
import com.cuongpn.service.VoteService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@RequiredArgsConstructor
@Slf4j
public class AnswerServiceImpl implements AnswerService {

    private final QuestionRepository questionRepository;

    private final AnswerRepository answerRepository;

    private final VoteService voteService;

    private final AnswerMapper answerMapper;
    @Override
    public AnswerDTO handleCreateAnswerRequest(Long questionId, CreateAnswerDTO createAnswerDTO) {
        Answer answer =  new Answer();
        Question question = questionRepository.findById(questionId).orElseThrow();
        answer.setQuestion(question);
        answer.setContent(createAnswerDTO.getContent());
        return answerMapper.toAnswerDTO(answerRepository.save(answer));
    }

    @Override
    public Page<AnswerDTO> handleGetAnswerRequest(Long questionId, Pageable pageable) {
        Question question = questionRepository.findById(questionId).orElseThrow(()-> new AppException(ErrorCode.QUESTION_NOT_EXISTED));
        if(question.getAcceptedAnswer() != null){
            return   answerRepository.findByQuestionAndIdNot(question,question.getAcceptedAnswer().getId(),pageable)
                    .map(
                            answer -> {
                                AnswerDTO answerDTO = answerMapper.toAnswerDTO(answer);
                                answerDTO.setTotalVote(voteService.getVoteCount(answerDTO.getId()));
                                return answerDTO;
                            }
                    );

        }
        else{
            return answerRepository.findByQuestion(question,pageable).map(
                    answer -> {
                        AnswerDTO answerDTO = answerMapper.toAnswerDTO(answer);
                        answerDTO.setTotalVote(voteService.getVoteCount(answerDTO.getId()));
                        return answerDTO;
                    }
            );
        }
    }

    @Override

    public void acceptAnswer(Long questionId, Long answerId, UserPrincipal userPrincipal) {
        if(userPrincipal == null) return;
        Question question = questionRepository.findById(questionId).orElseThrow(() ->
                new AppException(ErrorCode.QUESTION_NOT_EXISTED));
        if (!question.getCreatedBy().getEmail().equals(userPrincipal.getEmail())) {
            throw new AppException(ErrorCode.USER_NOT_QUESTION_OWNER);
        }
        Answer answer = answerRepository.findById(answerId).orElseThrow(
                () -> new AppException(ErrorCode.ANSWER_NOT_EXISTED));
        if (!question.getAnswers().contains(answer)) {
            throw new AppException(ErrorCode.ANSWER_NOT_BELONG_QUESTION);
        }
        question.setAcceptedAnswer(answer);
        questionRepository.save(question);

    }

    @Override
    public void unAcceptAnswer(Long questionId, UserPrincipal userPrincipal) {
        if(userPrincipal == null) return;
        Question question = questionRepository.findById(questionId).orElseThrow(() ->
                new AppException(ErrorCode.QUESTION_NOT_EXISTED));
        if (!question.getCreatedBy().getEmail().equals(userPrincipal.getEmail())) {
            throw new AppException(ErrorCode.USER_NOT_QUESTION_OWNER);
        }
        question.setAcceptedAnswer(null);
        questionRepository.save(question);
    }




}
