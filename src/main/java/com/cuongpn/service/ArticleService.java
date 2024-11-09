package com.cuongpn.service;

import com.cuongpn.dto.requestDTO.CreateArticleDTO;
import com.cuongpn.dto.responeDTO.ArticleDTO;
import com.cuongpn.dto.responeDTO.ArticleDetailDTO;
import com.cuongpn.entity.Article;
import com.cuongpn.security.services.UserPrincipal;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;


public interface ArticleService {
    ArticleDetailDTO saveArticle(CreateArticleDTO article);

    Page<ArticleDTO> getAll(Pageable pageable);

    ArticleDetailDTO getArticleBySlug(String slug, UserPrincipal userPrincipal);

    Article getArticleById(Long id);

    Page<ArticleDTO> getArticlesByTag(String tag, Pageable pageable);

    Page<ArticleDTO> getLatestArticlesByEditorChoice(Pageable pageable);


    Page<ArticleDTO> getLatestArticleByBookmarked(UserPrincipal user, Pageable pageable);

    Page<ArticleDTO> getLatestArticlesByFollowing(UserPrincipal user,Pageable pageable);

    Page<ArticleDTO> getArticlesBySeries(Long seriesId, Pageable pageable);

    List<ArticleDTO> getUnassignedArticles(UserPrincipal userPrincipal);

}
