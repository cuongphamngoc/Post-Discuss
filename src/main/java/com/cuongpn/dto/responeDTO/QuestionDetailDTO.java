package com.cuongpn.dto.responeDTO;

import lombok.*;
import org.springframework.data.domain.Page;

import java.time.LocalDateTime;
import java.util.List;
@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class QuestionDetailDTO {
    private Long id;
    private String title;
    private String slug;
    private String content;
    private LocalDateTime createdDate;
    private LocalDateTime lastModifiedDate;
    private AuthorDTO author;
    private int totalComments;
    private Page<AnswerDTO> answers;
    private AnswerDTO acceptedAnswer;
    private long upVote;
    private long downVote;
    private List<TagDTO> tags;
}
