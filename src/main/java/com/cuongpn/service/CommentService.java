package com.cuongpn.service;


import com.cuongpn.dto.requestDTO.CreateCommentDTO;
import com.cuongpn.dto.requestDTO.UpdateCommentDTO;
import com.cuongpn.dto.responeDTO.CommentDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


public interface CommentService {
    CommentDTO addNewComment(Long postId, CreateCommentDTO commentDTO);

    Page<CommentDTO> getCommentsByPostId(Long postId, Pageable pageable);

    CommentDTO updateComment(Long postId, Long commentId, UpdateCommentDTO commentDTO);

    void deleteComment(Long postId, Long commentId);

    int getTotalComments(Long postId);

    Page<CommentDTO> getCommentsByParentId(Long parentId, Pageable pageable);
}
