package com.cuongpn.service;

import com.cuongpn.security.services.UserPrincipal;
import org.springframework.stereotype.Service;

@Service
public interface PostService {
    void bookmarkPost(Long postId, UserPrincipal userPrincipal);
    void removeBookmark(Long postId, UserPrincipal userPrincipal);
}
