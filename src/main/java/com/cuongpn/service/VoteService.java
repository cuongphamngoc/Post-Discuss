package com.cuongpn.service;

import com.cuongpn.dto.requestDTO.VoteRequest;
import com.cuongpn.dto.responeDTO.VoteDTO;

public interface VoteService {

    VoteDTO handleVote(Long postId,VoteRequest voteRequest);
}
