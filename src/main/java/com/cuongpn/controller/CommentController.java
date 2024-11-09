package com.cuongpn.controller;

import com.cuongpn.dto.requestDTO.CreateCommentDTO;
import com.cuongpn.dto.requestDTO.UpdateCommentDTO;
import com.cuongpn.dto.responeDTO.CommentDTO;
import com.cuongpn.dto.responeDTO.ResponseData;
import com.cuongpn.security.services.CurrentUser;
import com.cuongpn.security.services.UserPrincipal;
import com.cuongpn.service.CommentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/contents/{contentId}/comments")
public class CommentController {
    private final CommentService commentService;

    @PostMapping
    public ResponseData<?> handleAddComment(@PathVariable("contentId") Long contentId,
                                            @RequestBody @Valid CreateCommentDTO createCommentDTO){
        return new ResponseData<>(HttpStatus.CREATED.value(), "Add comment successful",
                commentService.addNewComment(contentId,createCommentDTO));
    }
    @GetMapping
    public ResponseData<Page<CommentDTO>> handleGetComments(@PathVariable("contentId") Long contentId, Pageable pageable, @CurrentUser UserPrincipal currentUser) {
        Page<CommentDTO> comments = commentService.getCommentsByPostId(contentId, pageable, currentUser);
        return new ResponseData<>(HttpStatus.OK.value(), "Get comments successful",comments);
    }
    @PutMapping("/{commentId}")
    public ResponseData<CommentDTO> updateComment(@PathVariable("contentId") Long contentId, @PathVariable Long commentId,
                                                  @RequestBody UpdateCommentDTO commentDTO) {
        CommentDTO updatedComment = commentService.updateComment(contentId, commentId, commentDTO);
        return new ResponseData<>(HttpStatus.OK.value(), "Update comments successful",updatedComment);
    }

    @DeleteMapping("/{commentId}")
    public ResponseData<Void> deleteComment(@PathVariable Long postId, @PathVariable Long commentId) {
        commentService.deleteComment(postId, commentId);
        return new ResponseData<>(HttpStatus.NO_CONTENT.value(), "Delete comment successful");
    }



}
