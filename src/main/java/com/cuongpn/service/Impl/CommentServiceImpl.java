package com.cuongpn.service.Impl;

import com.cuongpn.dto.requestDTO.CreateCommentDTO;
import com.cuongpn.dto.requestDTO.UpdateCommentDTO;
import com.cuongpn.dto.responeDTO.CommentDTO;
import com.cuongpn.entity.Comment;
import com.cuongpn.entity.Post;
import com.cuongpn.exception.AppException;
import com.cuongpn.exception.ErrorCode;
import com.cuongpn.mapper.CommentMapper;
import com.cuongpn.repository.CommentRepository;
import com.cuongpn.repository.PostRepository;
import com.cuongpn.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.HashSet;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {
    private final CommentRepository commentRepository;
    private final PostRepository postRepository;
    private final CommentMapper commentMapper;

    @Override
    public CommentDTO addNewComment(Long postId, CreateCommentDTO commentDTO) {


        Post post = postRepository.findById(postId).orElseThrow(
                () -> new AppException(ErrorCode.POST_NOT_EXISTED));

        Comment comment = Comment.builder()
                .content(commentDTO.getContent())
                .post(post)
                .replies(new HashSet<>())
                .build();
        Comment parent = null;
        if (commentDTO.getParentCommentId() != null) {
            parent = commentRepository.findById(commentDTO.getParentCommentId()).orElseThrow(
                    () -> new AppException(ErrorCode.COMMENT_NOT_EXISTED));
            comment.setParent(parent);
        }
        Comment savedComment = commentRepository.save(comment);
        post.getComments().add(savedComment);
        if (parent != null) {
            parent.getReplies().add(savedComment);
        }

        return commentMapper.toCommentDTO(savedComment);
    }

    @Override
    public Page<CommentDTO> getCommentsByPostId(Long postId, Pageable pageable) {
        Page<Comment> comments = commentRepository.findByPostIdAndParentIsNull(postId, pageable);
        return comments.map(commentMapper::toCommentDTO);
    }


    @Override
    public CommentDTO updateComment(Long postId, Long commentId, UpdateCommentDTO commentDTO) {
        Post post = postRepository.findById(postId).orElseThrow(
                ()-> new AppException(ErrorCode.POST_NOT_EXISTED));
        Comment comment = commentRepository.findById(commentId).orElseThrow(
                ()-> new AppException(ErrorCode.COMMENT_NOT_EXISTED));
        comment.setContent(commentDTO.getContent());
        Comment updatedComment = commentRepository.save(comment);
        return commentMapper.toCommentDTO(updatedComment);

    }

    @Override
    public void deleteComment(Long postId, Long commentId) {
        Post post = postRepository.findById(postId).orElseThrow(
                ()-> new AppException(ErrorCode.POST_NOT_EXISTED));
        Comment comment = commentRepository.findById(commentId).orElseThrow(
                ()-> new AppException(ErrorCode.COMMENT_NOT_EXISTED));
        post.getComments().remove(comment);
        postRepository.save(post);
    }

    @Override
    public int getTotalComments(Long postId) {
        return commentRepository.countByPostId(postId);
    }

    @Override
    public Page<CommentDTO> getCommentsByParentId(Long parentId, Pageable pageable) {
        Comment parent = commentRepository.findById(parentId).orElseThrow(()-> new AppException(ErrorCode.COMMENT_NOT_EXISTED));
        Page<Comment> comments = commentRepository.findByParent_Id(parentId, pageable);
        return comments.map(commentMapper::toCommentDTO);
    }
}

