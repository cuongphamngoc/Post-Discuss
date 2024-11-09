package com.cuongpn.service;

import com.cuongpn.dto.requestDTO.VoteRequest;
import com.cuongpn.dto.responeDTO.VoteDTO;
import com.cuongpn.security.services.UserPrincipal;

import java.util.List;
import java.util.Map;

public interface VoteService {

    VoteDTO handleVote(Long postId,VoteRequest voteRequest);
    long getVoteCount(Long contentId);
    Map<Long,Long> getVoteCounts(List<Long> contendIds);

    String getVoteByUser(Long contentId, UserPrincipal userPrincipal);

    Map<Long, String> getVoteByUser(List<Long> contentIds, Long userId);
}
