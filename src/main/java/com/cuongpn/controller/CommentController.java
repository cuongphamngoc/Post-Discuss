package com.cuongpn.controller;

import com.cuongpn.dto.requestDTO.CreateCommentDTO;
import com.cuongpn.dto.requestDTO.UpdateCommentDTO;
import com.cuongpn.dto.responeDTO.CommentDTO;
import com.cuongpn.dto.responeDTO.ResponseData;
import com.cuongpn.mapper.CommentMapper;
import com.cuongpn.service.CommentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/posts/{postId}/comments")
public class CommentController {
    private final CommentService commentService;

    @PostMapping
    public ResponseData<?> handleAddComment(@PathVariable Long postId,
                                            @RequestBody @Valid CreateCommentDTO createCommentDTO){
        return new ResponseData<>(HttpStatus.CREATED.value(), "Add comment successful",
                commentService.addNewComment(postId,createCommentDTO));
    }
    @GetMapping
    public ResponseData<Page<CommentDTO>> handleGetComments(@PathVariable Long postId, Pageable pageable ) {
        Page<CommentDTO> comments = commentService.getCommentsByPostId(postId, pageable);
        return new ResponseData<>(HttpStatus.OK.value(), "Get comments successful",comments);
    }
    @PutMapping("/{commentId}")
    public ResponseData<CommentDTO> updateComment(@PathVariable Long postId, @PathVariable Long commentId,
                                                  @RequestBody UpdateCommentDTO commentDTO) {
        CommentDTO updatedComment = commentService.updateComment(postId, commentId, commentDTO);
        return new ResponseData<>(HttpStatus.OK.value(), "Update comments successful",updatedComment);
    }

    @DeleteMapping("/{commentId}")
    public ResponseData<Void> deleteComment(@PathVariable Long postId, @PathVariable Long commentId) {
        commentService.deleteComment(postId, commentId);
        return new ResponseData<>(HttpStatus.NO_CONTENT.value(), "Delete comment successful");
    }
    @GetMapping("/{commentId}/replies")
    public ResponseData<Page<CommentDTO>> getReplies(@PathVariable("commentId") Long parentId, Pageable pageable){
        Page<CommentDTO> commentDTOPage = commentService.getCommentsByParentId(parentId,pageable);
        return new ResponseData<>(HttpStatus.OK.value(), "Get comments successful",commentDTOPage);
    }



}
