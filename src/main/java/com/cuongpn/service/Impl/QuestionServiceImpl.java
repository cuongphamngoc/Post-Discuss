package com.cuongpn.service.Impl;

import com.cuongpn.dto.requestDTO.CreateQuestionDTO;
import com.cuongpn.dto.responeDTO.*;
import com.cuongpn.entity.Answer;
import com.cuongpn.entity.Question;
import com.cuongpn.mapper.AnswerMapper;
import com.cuongpn.mapper.QuestionMapper;
import com.cuongpn.repository.AnswerRepository;
import com.cuongpn.repository.QuestionRepository;
import com.cuongpn.service.CommentService;
import com.cuongpn.service.QuestionService;
import com.cuongpn.util.SlugUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class QuestionServiceImpl implements QuestionService {
    private final QuestionRepository questionRepository;
    private final AnswerRepository answerRepository;
    private final CommentService commentService;
    private final QuestionMapper questionMapper;
    private final AnswerMapper answerMapper;
    @Override
    public Question createNewQuestion(CreateQuestionDTO createQuestionDTO) {
        Question question = new Question();
        question.setTitle(createQuestionDTO.getTitle());
        question.setSlug(SlugUtil.makeSlug(createQuestionDTO.getTitle()));
        question.setContent(createQuestionDTO.getContent());
        return questionRepository.save(question);
    }

    @Override
    public Page<Question> findAll(Pageable pageable) {
        return questionRepository.findAll(pageable);
    }

    @Override
    public QuestionDetailDTO findQuestionDetailBySlug(String slug,Pageable pageable) {
        Question question = questionRepository.findBySlug(slug).orElseThrow();
        Page<Answer> answerPage = answerRepository.findByQuestion_Slug(slug,pageable);

        QuestionDetailDTO questionDetailDTO =  questionMapper.toQuestionDetailDTO(question);
        questionDetailDTO.setAnswers(answerPage.map(answerMapper::toAnswerDTO));
        questionDetailDTO.setAcceptedAnswer(answerMapper.toAnswerDTO(question.getAcceptedAnswer()));
        questionDetailDTO.setTotalComments(commentService.getTotalComments(question.getId()));

        return questionDetailDTO;

    }


}
