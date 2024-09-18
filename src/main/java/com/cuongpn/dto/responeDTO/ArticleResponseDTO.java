package com.cuongpn.dto.responeDTO;

import com.cuongpn.entity.Tag;
import lombok.Data;
import org.mapstruct.Mapping;

import java.time.LocalDateTime;
import java.util.Set;

@Data
public class ArticleResponseDTO {
    private Long id;

    private String title;

    private String slug;

    private String summary;

    private String content;

    private String imageUrl;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    private Long views;

    private Set<Tag> tags;

    private Author author;

    @Data
    public static class Author{
        private Long id;
        private String name;
        private String avatarUrl;
    }
}
