package com.cuongpn.dto.responeDTO;

import com.cuongpn.entity.Tag;
import lombok.Data;
import org.mapstruct.Mapping;
import org.springframework.data.domain.Page;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Data
public class ArticleResponseDTO {
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

    private VoteCount voteCount;

    private AuthorDTO author;

}
