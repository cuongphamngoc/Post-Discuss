package com.cuongpn.controller;

import com.cuongpn.dto.requestDTO.ArticleRequestDTO;
import com.cuongpn.dto.requestDTO.PageRequestDTO;
import com.cuongpn.dto.responeDTO.ArticleResponseDTO;
import com.cuongpn.dto.responeDTO.PaginatedArticleResponseDTO;
import com.cuongpn.dto.responeDTO.ResponseData;
import com.cuongpn.security.services.CurrentUser;
import com.cuongpn.security.services.UserPrincipal;
import com.cuongpn.service.ArticleService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/article")
@AllArgsConstructor
public class ArticleController {
    private final ArticleService articleService;
    @PostMapping("/")
    public ResponseData<ArticleResponseDTO> addNewArticle(@Valid @RequestBody ArticleRequestDTO requestDTO){
        return new ResponseData<>(HttpStatus.CREATED.value(), "Article created",articleService.saveArticle(requestDTO));
    }
    @GetMapping("/")
    public ResponseData<List<ArticleResponseDTO>> getArticles(){
        return new ResponseData<>(HttpStatus.OK.value(), "Success",articleService.getAll());
    }
    @GetMapping("/{slug}")
    public ResponseData<ArticleResponseDTO> getArticleBySlug(@PathVariable("slug") String slug) {
        return new ResponseData<>(HttpStatus.OK.value(), "Success", articleService.getArticleBySlug(slug));
    }

    @GetMapping("/newest")
    public ResponseData<PaginatedArticleResponseDTO> getLatestArticle(
            @RequestParam(defaultValue = "10") int pageSize,
            @RequestParam(defaultValue = "0") int pageNum) {
        return new ResponseData<>(HttpStatus.OK.value(), "Success", articleService.getLatestArticleByPage(pageSize, pageNum));
    }
    @GetMapping("/bookmarks")
    public ResponseData<?> getBookmarkedArticlesByUser (@RequestParam String user,
                                                        @RequestParam(defaultValue = "10") int pageSize,
                                                        @RequestParam(defaultValue = "0") int pageNum){
        return new ResponseData<>(HttpStatus.OK.value(), "Success", articleService.getLatestArticleByBookmarked(user,pageSize, pageNum));
    }

}
