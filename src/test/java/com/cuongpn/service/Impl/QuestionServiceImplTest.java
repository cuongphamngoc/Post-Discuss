package com.cuongpn.service.Impl;

import com.cuongpn.dto.requestDTO.CreateQuestionDTO;
import com.cuongpn.entity.Question;
import com.cuongpn.repository.QuestionRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashSet;
import java.util.List;

import static org.mockito.Mockito.*;


import static org.junit.jupiter.api.Assertions.*;
@ExtendWith(MockitoExtension.class)


class QuestionServiceImplTest {

    @Mock
    private QuestionRepository questionRepository;

    @InjectMocks
    private QuestionServiceImpl questionService;

    @Test
    public void shouldReturnSuccessWhenRequestValid(){
        //given
        CreateQuestionDTO createQuestionDTO = new CreateQuestionDTO("Abc","zxcz", List.of());

        Question response = Question.builder()
                .title("Abc")
                .slug("abc")
                .answers(new HashSet<>())
                .build();
        //when
        when(questionRepository.save(any(Question.class))).thenReturn(response);

        //then
        Question question = questionService.createNewQuestion(createQuestionDTO);

        assertEquals("Abc",question.getTitle());
        assertEquals("abc",question.getSlug());
        verify(questionRepository,times(1)).save(question);


    }
}