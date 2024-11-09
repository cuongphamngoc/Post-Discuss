package com.cuongpn.dto.responeDTO;

import lombok.*;

import java.time.LocalDateTime;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AnswerDTO {

    private Long id;
    private String content;
    private LocalDateTime createdDate;
    private LocalDateTime lastModifiedDate;
    private AuthorDTO author;
    private int totalComments;
    private long totalVote;



}
