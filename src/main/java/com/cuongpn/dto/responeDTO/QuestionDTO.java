package com.cuongpn.dto.responeDTO;

import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class QuestionDTO {
    private Long id;
    private String title;
    private String slug;
    private LocalDateTime createdDate;
    private LocalDateTime lastModifiedDate;
    private AuthorDTO author;
    private long upVote;
    private long downVote;
    private int totalComments;
    private List<TagDTO> tags;
}
