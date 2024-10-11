package com.cuongpn.controller;

import com.cuongpn.dto.requestDTO.CreateVoteDTO;
import com.cuongpn.dto.requestDTO.VoteRequest;
import com.cuongpn.dto.responeDTO.ResponseData;
import com.cuongpn.dto.responeDTO.VoteDTO;
import com.cuongpn.service.VoteService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/posts/{postId}/votes")
public class VoteController {
    private final VoteService voteService;

    @PostMapping
    public ResponseData<?> handleVotes(@PathVariable Long postId,@RequestBody @Valid VoteRequest voteRequest){

        VoteDTO voteDTO  =  voteService.handleVote(postId,voteRequest);
        return new ResponseData<>(HttpStatus.CREATED.value(), "Voted successful",voteDTO);
    }

}
