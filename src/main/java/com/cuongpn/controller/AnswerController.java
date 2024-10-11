package com.cuongpn.controller;

import com.cuongpn.dto.requestDTO.CreateAnswerDTO;
import com.cuongpn.dto.responeDTO.AnswerDTO;
import com.cuongpn.dto.responeDTO.ResponseData;
import com.cuongpn.service.AnswerService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/discuss/questions/{questionId}/answers")
public class AnswerController {
    private final AnswerService answerService;


    @PostMapping
    public ResponseData<AnswerDTO> saveNewAnswer(@PathVariable("questionId") Long questionId, @RequestBody CreateAnswerDTO createAnswerDTO){
        return new ResponseData<>(HttpStatus.CREATED.value(), "Created successful",answerService.handleCreateAnswerRequest(questionId,createAnswerDTO));
    }
    @GetMapping
    public ResponseData<Page<AnswerDTO>> getAnswersByQuestionId(@PathVariable("questionId") Long questionId, Pageable pageable){
        return new ResponseData<>(HttpStatus.OK.value(), "Get successful",answerService.handleGetAnswerRequest(questionId,pageable));

    }
    @PatchMapping("/{answerId}/accept")
    public ResponseData<?> acceptAnswer(@PathVariable Long questionId,
                                        @PathVariable Long answerId){
        answerService.acceptAnswer(questionId,answerId);
        return new ResponseData<>(HttpStatus.OK.value(),"Accept answer success");
    }


}
