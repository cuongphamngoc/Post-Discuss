package com.cuongpn.dto.responeDTO;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class SeriesDTO {
    private Long id;
    private String title;
    private String contentType;
    private String slug;
    private List<TagDTO> tags;
    private AuthorDTO author;
    private LocalDateTime createdDate;
    private LocalDateTime lastModifiedDate;
    private long totalVote;
    private long totalComments;
    private long views;
}
