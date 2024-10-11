package com.cuongpn.controller;

import com.cuongpn.dto.requestDTO.CreateArticleDTO;
import com.cuongpn.dto.responeDTO.*;
import com.cuongpn.service.ArticleService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
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
    public ResponseData<ArticleDetailDTO> getArticleBySlug(@PathVariable("slug") String slug) {
        return new ResponseData<>(HttpStatus.OK.value(), "Success", articleService.getArticleBySlug(slug));
    }

    @GetMapping("/newest")
    public ResponseData<Page<ArticleDTO>> getLatestArticle(Pageable pageable) {
        return new ResponseData<>(HttpStatus.OK.value(), "Success", articleService.getAll(pageable));
    }
    @GetMapping("/bookmarks")
    public ResponseData<?> getBookmarkedArticlesByUser (@RequestParam String user,
                                                        Pageable pageable){
        return new ResponseData<>(HttpStatus.OK.value(), "Success", articleService.getLatestArticleByBookmarked(user,pageable));
    }
    @GetMapping("/following")
    public ResponseData<?> getArticleByFollowingUser(Pageable pageable){
        return  new ResponseData<>(HttpStatus.OK.value(), "Success",articleService.getLatestArticlesByFollowing(pageable));
    }

}
