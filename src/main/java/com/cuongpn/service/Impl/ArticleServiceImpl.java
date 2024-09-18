package com.cuongpn.service.Impl;

import com.cuongpn.dto.requestDTO.ArticleRequestDTO;
import com.cuongpn.dto.responeDTO.ArticleResponseDTO;
import com.cuongpn.dto.responeDTO.PaginatedArticleResponseDTO;
import com.cuongpn.entity.Article;
import com.cuongpn.entity.Tag;
import com.cuongpn.mapper.ArticleMapper;
import com.cuongpn.repository.ArticleRepository;
import com.cuongpn.service.ArticleService;
import com.cuongpn.service.TagService;
import com.cuongpn.service.UserService;
import com.cuongpn.util.SlugUtil;
import com.cuongpn.util.SummaryUtil;
import lombok.AllArgsConstructor;
import org.jsoup.Jsoup;
import org.jsoup.safety.Safelist;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class ArticleServiceImpl implements ArticleService {
    private final ArticleRepository articleRepo;
    private final TagService tagService;
    private final ArticleMapper articleMapper;

    @Override
    public ArticleResponseDTO saveArticle(ArticleRequestDTO requestDTO) {
        Set<Tag> tags = Optional.ofNullable(requestDTO.getTags())
                .map(tagSet -> tagSet.stream()
                        .map(tagService::getTagByTagName)
                        .collect(Collectors.toSet()))
                .orElse(Collections.emptySet());
        Article article =  Article.builder()
                .title(requestDTO.getTitle())
                .slug(SlugUtil.makeSlug(requestDTO.getTitle()))
                .content(Jsoup.clean(requestDTO.getContent(), Safelist.basicWithImages()))
                .tags(tags)
                .summary(SummaryUtil.makeSummary(requestDTO.getContent()))
                .imageUrl(requestDTO.getImage())
                .build();
        Article response = articleRepo.save(article);
        return articleMapper.articleToArticleResponseDTO(response);


    }

    @Override
    public List<ArticleResponseDTO> getAll() {
        return articleRepo.findAll().stream().map(articleMapper::articleToArticleResponseDTO).toList();
    }

    @Override
    public ArticleResponseDTO getArticleBySlug(String slug) {
        Article article = articleRepo.findBySlug(slug).orElseThrow(()-> new IllegalArgumentException("Article not found with slug "+ slug));
        return articleMapper.articleToArticleResponseDTO(article);
    }

    @Override
    public Article getArticleById(Long id) {
        return articleRepo.findById(id).orElseThrow(()-> new IllegalArgumentException("Article not found with slug "+ id));

    }

    @Override
    public PaginatedArticleResponseDTO getLatestArticleByPage(int pageSize, int pageNum) {
        Pageable pageable = PageRequest.of(pageNum, pageSize, Sort.by("createdAt").descending());
        Page<Article> res =  articleRepo.findAll(pageable);
        long totalCount = res.getTotalElements();
        int currentPage = res.getNumber();
        return PaginatedArticleResponseDTO.builder()
                .articles(res.getContent().stream().map(articleMapper::articleToArticleResponseDTO).toList())
                .totalArticles(totalCount)
                .currentPage(currentPage)
                .build();

    }

    @Override
    public PaginatedArticleResponseDTO getLatestArticleByPageAndTag(String tag, int pageSize, int PageNum) {
        return null;
    }

    @Override
    public PaginatedArticleResponseDTO getLatestArticleByBookmarked(String email, int pageSize, int PageNum) {


        return null;
    }
}
