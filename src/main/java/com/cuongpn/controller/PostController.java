package com.cuongpn.controller;

import com.cuongpn.dto.responeDTO.ResponseData;
import com.cuongpn.security.services.CurrentUser;
import com.cuongpn.security.services.UserPrincipal;
import com.cuongpn.service.CommentService;
import com.cuongpn.service.PostService;
import com.cuongpn.service.TagService;
import com.cuongpn.service.VoteService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/post")
public class PostController {
    private final PostService postService;

    @PostMapping("/{postId}/bookmark")
    public ResponseData<?> bookmarkPost(@PathVariable Long postId, @CurrentUser UserPrincipal current) {
        postService.bookmarkPost(postId, current);
        return new ResponseData<>(HttpStatus.OK.value(), "Post bookmarked successfully");
    }
    @DeleteMapping("/{postId}/bookmarks")
    public ResponseData<?> removeBookmark(@CurrentUser UserPrincipal current, @PathVariable Long postId) {
        postService.removeBookmark( postId,current);
        return new ResponseData<>(HttpStatus.NO_CONTENT.value(), ("Bookmark removed successfully"));
    }
}
