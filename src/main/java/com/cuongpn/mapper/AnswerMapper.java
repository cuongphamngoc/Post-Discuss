package com.cuongpn.mapper;

import com.cuongpn.dto.responeDTO.AnswerDTO;

import com.cuongpn.entity.*;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;
import org.springframework.data.domain.Page;

import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface AnswerMapper {

    AnswerMapper INSTANCE = Mappers.getMapper(AnswerMapper.class);


    @Mapping(source = "createdBy",target = "author")
    @Mapping(source = "comments", target = "totalComments", qualifiedByName = "getTotalComments")

    AnswerDTO toAnswerDTO(Answer answer);

    @Named("getTotalComments")
    default int getTotalComments(Set<Comment> comments){
        return comments.size();
    }
    List<AnswerDTO> toAnswerDTOs(List<Answer> answerList);



    default List<AnswerDTO> toAnswerDTOs(Set<Answer> answers) {
        return answers.stream()
                .sorted(Comparator.comparing(Answer::getCreatedDate))
                .map(AnswerMapper.INSTANCE::toAnswerDTO)
                .collect(Collectors.toList());
    }
    default Page<AnswerDTO> answerSetToAnswerDTOPage(Set<Answer> set){
        return null;
    }
}
