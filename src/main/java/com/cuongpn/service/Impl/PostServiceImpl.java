package com.cuongpn.service.Impl;

import com.cuongpn.entity.Post;
import com.cuongpn.entity.User;
import com.cuongpn.exception.AppException;
import com.cuongpn.exception.ErrorCode;
import com.cuongpn.repository.PostRepository;
import com.cuongpn.repository.UserRepository;
import com.cuongpn.security.services.UserPrincipal;
import com.cuongpn.service.PostService;
import com.cuongpn.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {
    private final PostRepository postRepository;
    private final UserRepository userRepository;
    @Override
    @Transactional
    public void bookmarkPost(Long postId, UserPrincipal userPrincipal) {
        User  user = userRepository.findByEmail(userPrincipal.getEmail()).orElseThrow(()-> new AppException(ErrorCode.USER_NOT_EXISTED));
        Post post = postRepository.findPostWithUsers(postId).orElseThrow(()-> new AppException(ErrorCode.POST_NOT_EXISTED));
        if(post.getUsers().contains(user)){
            this.removeBookmark(postId,userPrincipal);
            return;
        }
        post.getUsers().add(user);
        postRepository.save(post);
    }

    @Override
    @Transactional
    public void removeBookmark(Long postId, UserPrincipal userPrincipal) {
        User  user = userRepository.findByEmail(userPrincipal.getEmail()).orElseThrow(()-> new AppException(ErrorCode.USER_NOT_EXISTED));
        Post post = postRepository.findPostWithUsers(postId).orElseThrow(()-> new AppException(ErrorCode.POST_NOT_EXISTED));
        post.getUsers().remove(user);
        postRepository.save(post);
    }
}
