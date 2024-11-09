package com.cuongpn.controller;

import com.cuongpn.dto.requestDTO.CreateArticleDTO;
import com.cuongpn.dto.responeDTO.*;
import com.cuongpn.security.services.CurrentUser;
import com.cuongpn.security.services.UserPrincipal;
import com.cuongpn.service.ArticleService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/articles")
@AllArgsConstructor
public class ArticleController {
    private final ArticleService articleService;
    @PostMapping
    public ResponseData<ArticleDetailDTO> addNewArticle(@Valid @RequestBody CreateArticleDTO requestDTO){
        return new ResponseData<>(HttpStatus.CREATED.value(), "Article created",articleService.saveArticle(requestDTO));
    }
    @GetMapping
    public ResponseData<Page<ArticleDTO>> getArticles(Pageable pageable){
        return new ResponseData<>(HttpStatus.OK.value(), "Success",articleService.getAll(pageable));
    }
    @GetMapping("/{slug}")
    public ResponseData<ArticleDetailDTO> getArticleBySlug(@PathVariable("slug") String slug, @CurrentUser UserPrincipal userPrincipal) {
        return new ResponseData<>(HttpStatus.OK.value(), "Success", articleService.getArticleBySlug(slug,userPrincipal));
    }

    @GetMapping("/newest")
    public ResponseData<Page<ArticleDTO>> getLatestArticle(Pageable pageable) {
        return new ResponseData<>(HttpStatus.OK.value(), "Success", articleService.getAll(pageable));
    }
    @GetMapping("/bookmarks")
    public ResponseData<?> getBookmarkedArticlesByUser (@CurrentUser UserPrincipal user,
                                                        Pageable pageable){
        return new ResponseData<>(HttpStatus.OK.value(), "Success", articleService.getLatestArticleByBookmarked(user,pageable));
    }

    @GetMapping("/following")
    public ResponseData<?> getArticleByFollowingUser(Pageable pageable,@CurrentUser UserPrincipal user){
        return  new ResponseData<>(HttpStatus.OK.value(), "Success",articleService.getLatestArticlesByFollowing(user,pageable));
    }

    @GetMapping("/tags/{tag}")
    public ResponseData<?> getArticleByTag(@PathVariable String tag, Pageable pageable) {
        return new ResponseData<>(HttpStatus.OK.value(), "Success", articleService.getArticlesByTag(tag, pageable));
    }

    @GetMapping("/editor-choice")
    public ResponseData<Page<ArticleDTO>> getArticleByEditorChoiceTag(Pageable pageable) {
        return new ResponseData<>(HttpStatus.OK.value(), "Success", articleService.getLatestArticlesByEditorChoice(pageable));
    }
    @GetMapping("/series/{seriesId}")
    public ResponseData<Page<ArticleDTO>> getArticlesBySeries(@PathVariable("seriesId") Long seriesId, Pageable pageable){
        return new ResponseData<>(HttpStatus.OK.value(),"Success",articleService.getArticlesBySeries(seriesId,pageable));
    }
    @GetMapping("/unassigned")
    public ResponseData<List<ArticleDTO>> getUnassignedArticles(@CurrentUser UserPrincipal userPrincipal){
        return  new ResponseData<>(HttpStatus.OK.value(), "success",articleService.getUnassignedArticles(userPrincipal));
    }




}
