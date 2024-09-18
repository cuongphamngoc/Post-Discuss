package com.cuongpn.service;

import com.cuongpn.dto.requestDTO.ArticleRequestDTO;
import com.cuongpn.dto.requestDTO.PageRequestDTO;
import com.cuongpn.dto.responeDTO.ArticleResponseDTO;
import com.cuongpn.dto.responeDTO.PaginatedArticleResponseDTO;
import com.cuongpn.entity.Article;
import com.cuongpn.security.services.CurrentUser;
import com.cuongpn.security.services.UserPrincipal;

import java.util.List;


public interface ArticleService {
    public ArticleResponseDTO saveArticle(ArticleRequestDTO article);

    public List<ArticleResponseDTO> getAll();

    public ArticleResponseDTO getArticleBySlug(String slug);

    public Article getArticleById(Long id);


    public PaginatedArticleResponseDTO getLatestArticleByPage(int pageSize, int pageNum);

    public PaginatedArticleResponseDTO getLatestArticleByPageAndTag(String tag, int pageSize, int PageNum);

    public PaginatedArticleResponseDTO getLatestArticleByBookmarked(String email,int pageSize, int PageNum);
}
