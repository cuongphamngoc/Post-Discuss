package com.cuongpn.service.Impl;

import com.cuongpn.dto.requestDTO.CreateArticleDTO;
import com.cuongpn.dto.responeDTO.*;
import com.cuongpn.entity.*;
import com.cuongpn.exception.AppException;
import com.cuongpn.exception.ErrorCode;
import com.cuongpn.mapper.ArticleMapper;
import com.cuongpn.repository.ArticleRepository;
import com.cuongpn.repository.SeriesRepository;
import com.cuongpn.repository.UserRepository;
import com.cuongpn.security.services.CurrentUser;
import com.cuongpn.security.services.UserPrincipal;
import com.cuongpn.service.*;
import com.cuongpn.util.SlugUtil;
import com.cuongpn.util.SummaryUtil;
import lombok.AllArgsConstructor;
import org.jsoup.Jsoup;
import org.jsoup.safety.Safelist;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class ArticleServiceImpl implements ArticleService {
    private final ArticleRepository articleRepo;
    private final TagService tagService;
    private final ArticleMapper articleMapper;
    private final UserRepository userRepository;
    private final SeriesRepository seriesRepository;
    private final VoteService voteService;

    @Override
    public ArticleDetailDTO saveArticle(CreateArticleDTO requestDTO) {
        Set<Tag> tags = Optional.ofNullable(requestDTO.getTags())
                .map(tagSet -> tagSet.stream()
                        .map(tagService::getTagByTagName)
                        .collect(Collectors.toSet()))
                .orElse(Collections.emptySet());

        Series series = Optional.ofNullable(requestDTO.getSeries()).flatMap(
            seriesRepository::findById).orElse(null);

        Article article =  Article.builder()
                .content(requestDTO.getContent())
                .title(requestDTO.getTitle())
                .slug(SlugUtil.makeSlug(requestDTO.getTitle()))
                .summary(SummaryUtil.makeSummary(requestDTO.getContent()))
                .imageUrl(requestDTO.getImageUrl())
                .series(series)
                .build();
        Safelist customSafelist = Safelist.basicWithImages()
                .addTags("h1", "h2", "h3", "h4", "h5", "h6", "pre")
                .addAttributes("h1","id")
                .addAttributes("h2","id");
        article.setContent(Jsoup.clean(requestDTO.getContent(), customSafelist));
        article.setTags(tags);
        Article response = articleRepo.save(article);
        return articleMapper.toArticleDetailDTO(response);


    }

    @Override
    public Page<ArticleDTO> getAll(Pageable pageable) {
        return articleRepo.findAll(pageable).map(
                article -> {
                    ArticleDTO articleDTO = articleMapper.toArticleDTO(article);
                    articleDTO.setContentType("ARTICLE");
                    return articleDTO;
                }
        );
    }

    @Override
    public ArticleDetailDTO getArticleBySlug(String slug, UserPrincipal userPrincipal) {
        Article article = articleRepo.findBySlug(slug).orElseThrow(()-> new AppException(ErrorCode.ARTICLE_NOT_EXISTED));

        ArticleDetailDTO articleDetailDTO =  articleMapper.toArticleDetailDTO(article);
        articleDetailDTO.setTotalVote(voteService.getVoteCount(article.getId()));
        if(userPrincipal != null){
            articleDetailDTO.setUserVote(voteService.getVoteByUser(article.getId(),userPrincipal));
        }
        return articleDetailDTO;
    }

    @Override
    public Article getArticleById(Long id) {
        return  articleRepo.findById(id).orElseThrow(()-> new AppException(ErrorCode.ARTICLE_NOT_EXISTED));

    }

    @Override
    public Page<ArticleDTO> getArticlesByTag(String tagName, Pageable pageable) {
        Tag tag = tagService.getTagByTagName(tagName);
        Set<Article> articles =  tag.getPosts().stream()
                .filter(post -> post instanceof Article)
                .map(post -> (Article) post)
                .collect(Collectors.toSet());
        List<ArticleDTO> articleDTOS = articles
                .stream()
                .sorted(Comparator.comparing(AuditableEntity::getCreatedDate,Comparator.reverseOrder()))
                .map(articleMapper::toArticleDTO)
                .skip(pageable.getOffset())
                .limit(pageable.getPageSize())
                .toList();
        return new PageImpl<>(articleDTOS,pageable,articles.size());
    }

    @Override
    public Page<ArticleDTO> getLatestArticlesByEditorChoice(Pageable pageable) {
        return articleRepo.findByIsFeaturesTrue(pageable).map(articleMapper::toArticleDTO);
    }

    @Override
    public Page<ArticleDTO> getLatestArticleByBookmarked(UserPrincipal userPrincipal, Pageable pageable) {
        User user = userRepository.findByEmail(userPrincipal.getEmail()).orElseThrow(()-> new AppException(ErrorCode.USER_NOT_EXISTED));
        List<ArticleDTO> bookmarkedArticles=  user.getBookmarks().stream()
                .filter(post -> post instanceof Article)
                .map(post -> (Article) post)
                .map(articleMapper::toArticleDTO)
                .toList()
                .stream()
                .skip(pageable.getOffset())
                .limit(pageable.getPageSize())
                .collect(Collectors.toList());
        return new PageImpl<>(bookmarkedArticles,pageable,user.getBookmarks().size());
    }

    @Override
    public Page<ArticleDTO> getLatestArticlesByFollowing(UserPrincipal userPrincipal, Pageable pageable) {
        User currentUser = userRepository.findByEmail(userPrincipal.getEmail()).orElseThrow(()-> new AppException(ErrorCode.USER_NOT_EXISTED));
        Set<User> followingUser = userRepository.findFollowingByUserId(currentUser.getId());
        return  articleRepo.findLatestArticlesByFollowingUsers(followingUser,pageable)
                .map(articleMapper::toArticleDTO);
    }

    @Override
    public Page<ArticleDTO> getArticlesBySeries(Long seriesId, Pageable pageable) {
        Series series = seriesRepository.findById(seriesId).orElseThrow(()-> new AppException(ErrorCode.POST_NOT_EXISTED));
        return articleRepo.findBySeries(series,pageable).map(articleMapper::toArticleDTO);
    }

    @Override
    public List<ArticleDTO> getUnassignedArticles(UserPrincipal userPrincipal) {
        if(userPrincipal == null) return List.of();
        List<Article> articleList = articleRepo.findByCreatedBy_IdAndSeriesIsNull(userPrincipal.getId());
        return articleList.stream().map(articleMapper::toArticleDTO).collect(Collectors.toList());

    }
}
