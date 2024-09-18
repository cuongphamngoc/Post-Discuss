package com.cuongpn.dto.responeDTO;

import lombok.Builder;
import lombok.Data;

import java.util.List;
@Data
@Builder
public class PaginatedArticleResponseDTO {
    private List<ArticleResponseDTO> articles;
    private long totalArticles;
    private int currentPage;
}
