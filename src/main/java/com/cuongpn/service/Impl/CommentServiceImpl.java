package com.cuongpn.service.Impl;

import com.cuongpn.dto.requestDTO.CreateCommentDTO;
import com.cuongpn.dto.requestDTO.UpdateCommentDTO;
import com.cuongpn.dto.responeDTO.CommentDTO;
import com.cuongpn.entity.Comment;
import com.cuongpn.entity.Content;
import com.cuongpn.exception.AppException;
import com.cuongpn.exception.ErrorCode;
import com.cuongpn.mapper.CommentMapper;
import com.cuongpn.repository.CommentRepository;
import com.cuongpn.repository.ContentRepository;
import com.cuongpn.security.services.UserPrincipal;
import com.cuongpn.service.CommentService;
import com.cuongpn.service.VoteService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {
    private final CommentRepository commentRepository;
    private final ContentRepository contentRepository;
    private final CommentMapper commentMapper;
    private final VoteService voteService;

    @Override
    public CommentDTO addNewComment(Long contentId, CreateCommentDTO commentDTO) {


        Content parent = contentRepository.findById(contentId).orElseThrow(
                () -> new AppException(ErrorCode.POST_NOT_EXISTED));

        Comment comment = Comment.builder()
                .parent(parent)
                .content(commentDTO.getContent())
                .build();
        Comment savedComment = commentRepository.save(comment);
        parent.getComments().add(savedComment);
        contentRepository.save(parent);
        CommentDTO res = commentMapper.toCommentDTO(savedComment);
        res.setRepliesCount(this.getTotalComments(res.getId()));
        return res;
    }

    @Override
    public Page<CommentDTO> getCommentsByPostId(Long contentId, Pageable pageable, UserPrincipal user) {
        Page<Comment> comments = commentRepository.findByParent_Id(contentId, pageable);
        List<Long> commentIds = comments.getContent().stream()
                .map(Comment::getId)
                .toList();
        Map<Long,Long> replyCounts  = this.getRepliesCounts(commentIds);
        Map<Long,Long> voteCounts = voteService.getVoteCounts(commentIds);
        Map<Long, String> userVotes = Optional.ofNullable(user)
                .map(u -> voteService.getVoteByUser(commentIds, user.getId()))
                .orElse(Collections.emptyMap());

        return comments.map(
                comment->{
                    CommentDTO commentDTO = commentMapper.toCommentDTO(comment);
                    commentDTO.setRepliesCount(
                            replyCounts.getOrDefault(comment.getId(),0L)
                    );
                    commentDTO.setTotalVote(voteCounts.getOrDefault(comment.getId(),0L));
                    commentDTO.setUserVote(userVotes.getOrDefault(comment.getId(),null));
                    return commentDTO;
                }
        );
    }
    public Map<Long,Long> getRepliesCounts(List<Long> contendIds){
        List<Object[]> replyResultList = commentRepository.findReplyCountByParentIds(contendIds);
        return replyResultList.stream()
                .collect(Collectors.toMap(
                        row -> (Long) row[0],  // c.parent.id
                        row -> (Long) row[1]   // COUNT(c)
                ));
    }


    @Override
    public CommentDTO updateComment(Long contentId, Long commentId, UpdateCommentDTO commentDTO) {
        Content content = contentRepository.findById(contentId).orElseThrow(
                ()-> new AppException(ErrorCode.POST_NOT_EXISTED));
        Comment comment = commentRepository.findById(commentId).orElseThrow(
                ()-> new AppException(ErrorCode.COMMENT_NOT_EXISTED));
        comment.setContent(commentDTO.getContent());
        Comment updatedComment = commentRepository.save(comment);
        return commentMapper.toCommentDTO(updatedComment);

    }

    @Override
    public void deleteComment(Long postId, Long commentId) {
        Content parent = contentRepository.findById(postId).orElseThrow(
                ()-> new AppException(ErrorCode.POST_NOT_EXISTED));
        Comment comment = commentRepository.findById(commentId).orElseThrow(
                ()-> new AppException(ErrorCode.COMMENT_NOT_EXISTED));
        parent.getComments().remove(comment);
        contentRepository.save(parent);
    }

    @Override
    public long getTotalComments(Long parentId) {
        return commentRepository.countByParent_Id(parentId);
    }


}

