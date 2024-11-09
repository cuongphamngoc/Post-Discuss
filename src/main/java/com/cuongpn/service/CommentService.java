package com.cuongpn.service;


import com.cuongpn.dto.requestDTO.CreateCommentDTO;
import com.cuongpn.dto.requestDTO.UpdateCommentDTO;
import com.cuongpn.dto.responeDTO.CommentDTO;
import com.cuongpn.security.services.UserPrincipal;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;


public interface CommentService {
    CommentDTO addNewComment(Long postId, CreateCommentDTO commentDTO);

    Page<CommentDTO> getCommentsByPostId(Long postId, Pageable pageable, UserPrincipal principal);

    CommentDTO updateComment(Long postId, Long commentId, UpdateCommentDTO commentDTO);

    void deleteComment(Long postId, Long commentId);

    long getTotalComments(Long postId);

    Map<Long,Long> getRepliesCounts(List<Long> parentIds);

}
