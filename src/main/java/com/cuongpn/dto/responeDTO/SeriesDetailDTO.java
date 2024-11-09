package com.cuongpn.dto.responeDTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SeriesDetailDTO {
    private Long id;
    private String title;
    private String content;
    private String slug;
    private List<TagDTO> tags;
    private AuthorDTO author;
    private LocalDateTime createdDate;
    private LocalDateTime lastModifiedDate;
    private long totalVote;
    private long totalComments;
    private long views;
    private long totalBookmark;
    private String userVote;
}
