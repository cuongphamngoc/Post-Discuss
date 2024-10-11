package com.cuongpn.service.Impl;

import com.cuongpn.dto.requestDTO.CreateArticleDTO;
import com.cuongpn.dto.responeDTO.*;
import com.cuongpn.entity.Article;
import com.cuongpn.entity.Tag;
import com.cuongpn.exception.AppException;
import com.cuongpn.exception.ErrorCode;
import com.cuongpn.mapper.ArticleMapper;
import com.cuongpn.repository.ArticleRepository;
import com.cuongpn.service.ArticleService;
import com.cuongpn.service.CommentService;
import com.cuongpn.service.TagService;
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
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class ArticleServiceImpl implements ArticleService {
    private final ArticleRepository articleRepo;
    private final TagService tagService;
    private final ArticleMapper articleMapper;
    private final CommentService commentService;

    @Override
    public ArticleDetailDTO saveArticle(CreateArticleDTO requestDTO) {
        Set<Tag> tags = Optional.ofNullable(requestDTO.getTags())
                .map(tagSet -> tagSet.stream()
                        .map(tagService::getTagByTagName)
                        .collect(Collectors.toSet()))
                .orElse(Collections.emptySet());
        Article article =  Article.builder()
                .title(requestDTO.getTitle())
                .slug(SlugUtil.makeSlug(requestDTO.getTitle()))
                .summary(SummaryUtil.makeSummary(requestDTO.getContent()))
                .imageUrl(requestDTO.getImage())
                .build();
        Safelist customSafelist = Safelist.basicWithImages()
                .addTags("h1", "h2", "h3", "h4", "h5", "h6", "pre");
        article.setContent(Jsoup.clean(requestDTO.getContent(), customSafelist));
        article.setTags(tags);
        Article response = articleRepo.save(article);
        return articleMapper.toArticleDetailDTO(response);


    }

    @Override
    public Page<ArticleDTO> getAll(Pageable pageable) {
        return articleRepo.findAll(pageable).map(articleMapper::toArticleDTO);
    }

    @Override
    public ArticleDetailDTO getArticleBySlug(String slug) {
        Article article = articleRepo.findBySlug(slug).orElseThrow(()-> new AppException(ErrorCode.ARTICLE_NOT_EXISTED));
        Pageable pageable = PageRequest.of(0,10, Sort.Direction.ASC,"createdDate");
        Page<CommentDTO>  commentDTOS = commentService.getCommentsByPostId(article.getId(),pageable);
        ArticleDetailDTO articleDetailDTO = articleMapper.toArticleDetailDTO(article);
        articleDetailDTO.setComments(commentDTOS);

        return articleDetailDTO;
    }

    @Override
    public Article getArticleById(Long id) {
        return  articleRepo.findById(id).orElseThrow(()-> new AppException(ErrorCode.ARTICLE_NOT_EXISTED));

    }

    @Override
    public Page<ArticleDTO> getLatestArticleByPageAndTag(String tag, Pageable pageable) {
        return null;
    }

    @Override
    public Page<ArticleDTO> getLatestArticleByBookmarked(String email, Pageable pageable) {


        return null;
    }

    @Override
    public Page<ArticleDTO> getLatestArticlesByFollowing(Pageable pageable) {
        return null;
    }
}
