package com.cuongpn.dto.requestDTO;


import lombok.*;

import lombok.experimental.SuperBuilder;

import java.util.List;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreateQuestionDTO {

    private String title;
    private String content;
    private List<String> tags;
}
