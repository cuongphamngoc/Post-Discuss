package com.cuongpn.service.Impl;

import com.cuongpn.dto.requestDTO.VoteRequest;
import com.cuongpn.dto.responeDTO.VoteDTO;
import com.cuongpn.entity.Content;
import com.cuongpn.entity.User;
import com.cuongpn.entity.Vote;
import com.cuongpn.enums.VoteType;
import com.cuongpn.exception.AppException;
import com.cuongpn.exception.ErrorCode;
import com.cuongpn.mapper.VoteMapper;
import com.cuongpn.repository.ContentRepository;
import com.cuongpn.repository.VoteRepository;
import com.cuongpn.security.services.UserPrincipal;
import com.cuongpn.service.UserService;
import com.cuongpn.service.VoteService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class VoteServiceImpl implements VoteService {
    private final VoteRepository voteRepository;
    private final VoteMapper voteMapper;
    private final UserService userService;
    private final ContentRepository contentRepository;
    @Override
    public VoteDTO handleVote(Long contentId, VoteRequest voteRequest) {
        Content content = contentRepository.findById(contentId).orElseThrow(
                () -> new AppException(ErrorCode.POST_NOT_EXISTED));
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userService.getUserByMail(email);

        Optional<Vote> existingVoteOpt = voteRepository.findByCreatedByAndContent(user,content);

        if (existingVoteOpt.isPresent()) {
            Vote existingVote = existingVoteOpt.get();
            if (existingVote.getVoteType() == voteRequest.getVoteType()) {
                // Nếu vote cùng loại, xóa vote
                voteRepository.delete(existingVote);
                return new VoteDTO();
            } else {
                // Nếu vote khác loại, cập nhật loại vote
                existingVote.setVoteType(voteRequest.getVoteType());
                return voteMapper.toVoteDTO(voteRepository.save(existingVote));

            }
        } else {
            // Nếu vote chưa tồn tại, thêm vote mới
            Vote newVote = new Vote();
            newVote.setContent(content);
            newVote.setVoteType(voteRequest.getVoteType());
            return voteMapper.toVoteDTO(voteRepository.save(newVote));
        }

    }

    @Override
    public long getVoteCount(Long contentId) {
        List<Vote> list =  voteRepository.findByContent_Id(contentId);
        long voteUp = list.stream().filter(vote -> vote.getVoteType().equals(VoteType.UP)).toList().size();
        long voteDown = list.stream().filter(vote -> vote.getVoteType().equals(VoteType.DOWN)).toList().size();
        return voteUp - voteDown;
    }

    @Override
    public Map<Long, Long> getVoteCounts(List<Long> contendIds) {
        List<Object[]> voteResultList = voteRepository.findVoteCountByContentIds(contendIds);
        return voteResultList.stream()
                .collect(Collectors.toMap(
                        row -> (Long) row[0],  // v.comment.id
                        row -> (Long) row[1] - (Long) row[2]
                ));
    }

    @Override
    public String getVoteByUser(Long contentId, UserPrincipal userPrincipal) {
        if(userPrincipal == null) return "";
        System.out.println(userPrincipal.getId());
        return voteRepository.findByContent_IdAndCreatedBy_Email(contentId,userPrincipal.getEmail()).map(
                vote -> vote.getVoteType().name()
        ).orElse("");
    }
    @Override
    public Map<Long, String> getVoteByUser(List<Long> contentIds, Long userId){
        List<Object[]> results = voteRepository.findVotedCommentIdsAndTypeByUser(contentIds,userId);
        return results.stream().collect(Collectors.toMap(
                row-> (Long) row[0],
                row -> row[1].toString()
        ));
    }
}
