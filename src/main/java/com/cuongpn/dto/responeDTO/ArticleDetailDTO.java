package com.cuongpn.dto.responeDTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.domain.Page;

import java.time.LocalDateTime;
import java.util.List;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ArticleDetailDTO {
    private Long id;

    private String title;

    private String slug;

    private String summary;

    private String content;

    private String imageUrl;

    private LocalDateTime createdDate;

    private LocalDateTime lastModifiedDate;

    private Long views = 0L ;

    private List<TagDTO> tags;

    private Page<CommentDTO> comments;

    private long totalVote;

    private String userVote;

    private AuthorDTO author;


}
