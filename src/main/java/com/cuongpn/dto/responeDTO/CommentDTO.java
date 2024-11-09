package com.cuongpn.dto.responeDTO;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CommentDTO {

    private Long id;
    private String content;
    private AuthorDTO author;
    private LocalDateTime createdDate;
    private LocalDateTime lastModifiedDate;
    private Long repliesCount;
    private Long totalVote;
    private String userVote;

}
