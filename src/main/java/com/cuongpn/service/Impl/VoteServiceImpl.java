package com.cuongpn.service.Impl;

import com.cuongpn.dto.requestDTO.VoteRequest;
import com.cuongpn.dto.responeDTO.VoteDTO;
import com.cuongpn.entity.Post;
import com.cuongpn.entity.User;
import com.cuongpn.entity.Vote;
import com.cuongpn.exception.AppException;
import com.cuongpn.exception.ErrorCode;
import com.cuongpn.mapper.VoteMapper;
import com.cuongpn.repository.PostRepository;
import com.cuongpn.repository.VoteRepository;
import com.cuongpn.service.UserService;
import com.cuongpn.service.VoteService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class VoteServiceImpl implements VoteService {
    private final VoteRepository voteRepository;
    private final PostRepository postRepository;
    private final VoteMapper voteMapper;
    private final UserService userService;
    @Override
    public VoteDTO handleVote(Long postId, VoteRequest voteRequest) {
        Post post = postRepository.findById(postId).orElseThrow(
                () -> new AppException(ErrorCode.POST_NOT_EXISTED));
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userService.getUserByMail(email);

        Optional<Vote> existingVoteOpt = voteRepository.findByCreatedByAndPost(user,post);

        if (existingVoteOpt.isPresent()) {
            Vote existingVote = existingVoteOpt.get();
            if (existingVote.getVoteType() == voteRequest.getVoteType()) {
                // Nếu vote cùng loại, xóa vote
                voteRepository.delete(existingVote);
                return null;
            } else {
                // Nếu vote khác loại, cập nhật loại vote
                existingVote.setVoteType(voteRequest.getVoteType());
                return voteMapper.toVoteDTO(voteRepository.save(existingVote));

            }
        } else {
            // Nếu vote chưa tồn tại, thêm vote mới
            Vote newVote = new Vote();
            newVote.setPost(post);
            newVote.setVoteType(voteRequest.getVoteType());
            return voteMapper.toVoteDTO(voteRepository.save(newVote));
        }

    }
}
