package com.cuongpn.mapper;

import com.cuongpn.dto.responeDTO.*;
import com.cuongpn.entity.*;
import com.cuongpn.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;


import java.util.List;


@Mapper(componentModel = "spring", uses = {AnswerMapper.class, CommentMapper.class})
public interface QuestionMapper {

    QuestionMapper INSTANCE = Mappers.getMapper(QuestionMapper.class);

    @Mapping(source = "createdBy",target = "author")
    QuestionDTO toQuestionDTO(Question question);

    List<QuestionDTO> toQuestionDTOs(List<Question> questions);


    @Mapping(source = "createdBy", target = "author")
    QuestionDetailDTO toQuestionDetailDTO(Question question);


}
