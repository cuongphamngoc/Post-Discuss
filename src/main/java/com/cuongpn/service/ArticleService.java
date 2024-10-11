package com.cuongpn.service;

import com.cuongpn.dto.requestDTO.CreateArticleDTO;
import com.cuongpn.dto.responeDTO.ArticleDTO;
import com.cuongpn.dto.responeDTO.ArticleDetailDTO;
import com.cuongpn.entity.Article;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


public interface ArticleService {
    ArticleDetailDTO saveArticle(CreateArticleDTO article);

    Page<ArticleDTO> getAll(Pageable pageable);

    ArticleDetailDTO getArticleBySlug(String slug);

    Article getArticleById(Long id);
    Page<ArticleDTO> getLatestArticleByPageAndTag(String tag, Pageable pageable);


    Page<ArticleDTO> getLatestArticleByBookmarked(String email, Pageable pageable);

    Page<ArticleDTO> getLatestArticlesByFollowing(Pageable pageable);
}
