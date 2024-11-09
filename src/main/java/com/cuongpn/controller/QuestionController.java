package com.cuongpn.controller;

import com.cuongpn.dto.requestDTO.CreateQuestionDTO;
import com.cuongpn.dto.responeDTO.QuestionDTO;
import com.cuongpn.dto.responeDTO.ResponseData;
import com.cuongpn.entity.Question;
import com.cuongpn.mapper.QuestionMapper;
import com.cuongpn.service.QuestionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/discuss/questions")
public class QuestionController {
    private final QuestionService questionService;
    private final QuestionMapper questionMapper;

    @GetMapping
    public ResponseData<Page<QuestionDTO>> getAllQuestion(Pageable pageable) {
        Page<Question> page =questionService.findAll(pageable);
        return new ResponseData<>(HttpStatus.OK.value(), "Get question successful",page.map(questionMapper::toQuestionDTO));
    }
    @PostMapping
    public ResponseData<QuestionDTO> handleCreateQuestion(@RequestBody @Valid CreateQuestionDTO createQuestionDTO){
        Question question = questionService.createNewQuestion(createQuestionDTO);

        return new ResponseData<>(HttpStatus.CREATED.value(), "Question created successful", QuestionMapper.INSTANCE.toQuestionDTO(question));
    }
    @GetMapping("/{slug}")
    public ResponseData<?> getQuestionBySlug(@PathVariable("slug") String slug, Pageable pageable){
        return new ResponseData<>(HttpStatus.OK.value(), "Successful",questionService.findQuestionDetailBySlug(slug,pageable));
    }

    @GetMapping("/unsolved")
    public ResponseData<?> getUnsolvedQuestions(Pageable pageable){
        return  new ResponseData<>(HttpStatus.OK.value(), "Successful",questionService.findUnsolvedQuestions(pageable));

    }




}
