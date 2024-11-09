package com.cuongpn.service.Impl;

import com.cuongpn.dto.requestDTO.CreateQuestionDTO;
import com.cuongpn.dto.responeDTO.*;
import com.cuongpn.entity.Answer;
import com.cuongpn.entity.Question;
import com.cuongpn.entity.Tag;
import com.cuongpn.mapper.AnswerMapper;
import com.cuongpn.mapper.QuestionMapper;
import com.cuongpn.repository.AnswerRepository;
import com.cuongpn.repository.QuestionRepository;
import com.cuongpn.service.CommentService;
import com.cuongpn.service.QuestionService;
import com.cuongpn.service.TagService;
import com.cuongpn.service.VoteService;
import com.cuongpn.util.SlugUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class QuestionServiceImpl implements QuestionService {
    private final QuestionRepository questionRepository;
    private final VoteService voteService;
    private final CommentService commentService;
    private final QuestionMapper questionMapper;
    private final AnswerMapper answerMapper;
    private final TagService tagService;
    @Override
    public Question createNewQuestion(CreateQuestionDTO createQuestionDTO) {
        Set<Tag> tags = Optional.ofNullable(createQuestionDTO.getTags())
                .map(tagSet -> tagSet.stream()
                        .map(tagService::getTagByTagName)
                        .collect(Collectors.toSet()))
                .orElse(Collections.emptySet());

        Question question = Question.builder()
                .content(createQuestionDTO.getContent())
                .title(createQuestionDTO.getTitle())
                .slug(SlugUtil.makeSlug(createQuestionDTO.getTitle()))
                .tags(tags).build();
        return questionRepository.save(question);
    }

    @Override
    public Page<Question> findAll(Pageable pageable) {
        return questionRepository.findAll(pageable);
    }

    @Override
    public QuestionDetailDTO findQuestionDetailBySlug(String slug,Pageable pageable) {
        Question question = questionRepository.findBySlug(slug).orElseThrow();
        QuestionDetailDTO questionDetailDTO =  questionMapper.toQuestionDetailDTO(question);
        questionDetailDTO.setTotalComments(commentService.getTotalComments(question.getId()));
        questionDetailDTO.setTotalVote(voteService.getVoteCount(question.getId()));
        AnswerDTO answerDTO = answerMapper.toAnswerDTO(question.getAcceptedAnswer());
        if(answerDTO!= null ) {
            answerDTO.setTotalVote(voteService.getVoteCount(answerDTO.getId()));
            questionDetailDTO.setAcceptedAnswer(answerDTO);
        }
        return questionDetailDTO;

    }

    @Override
    public Page<QuestionDTO> findUnsolvedQuestions(Pageable pageable) {
        return questionRepository.findUnsolvedQuestions(pageable).map(questionMapper::toQuestionDTO);
    }


}
