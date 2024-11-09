package com.cuongpn.controller;

import com.cuongpn.dto.requestDTO.VoteRequest;
import com.cuongpn.dto.responeDTO.ResponseData;
import com.cuongpn.dto.responeDTO.VoteDTO;
import com.cuongpn.security.services.CurrentUser;
import com.cuongpn.security.services.UserPrincipal;
import com.cuongpn.service.VoteService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/contents/{contentId}/votes")
public class VoteController {
    private final VoteService voteService;

    @PostMapping
    public ResponseData<?> handleVotes(@PathVariable("contentId") Long contentId,@RequestBody @Valid VoteRequest voteRequest){

        VoteDTO voteDTO = voteService.handleVote(contentId,voteRequest);
        return new ResponseData<>(HttpStatus.CREATED.value(), "Voted successful",voteDTO);
    }
    @GetMapping
    public ResponseData<?> handleGetVotes(@PathVariable("contentId") Long contentId ){
        return null;

    }
    @GetMapping("/is-voted")
    public ResponseData<?> checkIfCurrentUserVoted(@PathVariable("contentId") Long contentId, @CurrentUser UserPrincipal userPrincipal){
        return new ResponseData<>(HttpStatus.OK.value(), "Successful",voteService.getVoteByUser(contentId,userPrincipal));
    }


}
