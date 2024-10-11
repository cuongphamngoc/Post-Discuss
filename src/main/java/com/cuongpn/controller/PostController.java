package com.cuongpn.controller;

import com.cuongpn.service.CommentService;
import com.cuongpn.service.TagService;
import com.cuongpn.service.VoteService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/post")
public class PostController {
    private final CommentService commentService;
    private final VoteService voteService;
    private final TagService tagService;

}
