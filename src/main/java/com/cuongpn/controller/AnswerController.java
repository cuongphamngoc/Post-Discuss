package com.cuongpn.controller;

import com.cuongpn.dto.requestDTO.CreateAnswerDTO;
import com.cuongpn.dto.responeDTO.AnswerDTO;
import com.cuongpn.dto.responeDTO.ResponseData;
import com.cuongpn.security.services.CurrentUser;
import com.cuongpn.security.services.UserPrincipal;
import com.cuongpn.service.AnswerService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

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
                                        @PathVariable Long answerId,
                                        @CurrentUser UserPrincipal userPrincipal){
        answerService.acceptAnswer(questionId,answerId,userPrincipal);
        return new ResponseData<>(HttpStatus.OK.value(),"Accept answer success");
    }
    @PatchMapping("/unAccept")
    public ResponseData<?> unAcceptAnswer(@PathVariable Long questionId,
                                        @CurrentUser UserPrincipal userPrincipal){
        answerService.unAcceptAnswer(questionId,userPrincipal);
        return new ResponseData<>(HttpStatus.OK.value(),"UnAccept answer success");
    }


}
