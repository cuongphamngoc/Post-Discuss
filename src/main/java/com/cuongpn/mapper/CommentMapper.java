package com.cuongpn.mapper;

import com.cuongpn.dto.responeDTO.AuthorDTO;
import com.cuongpn.dto.responeDTO.CommentDTO;
import com.cuongpn.entity.Comment;
import com.cuongpn.entity.User;
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
public interface CommentMapper {

    CommentMapper INSTANCE = Mappers.getMapper(CommentMapper.class);

    @Mapping(source = "createdBy",target = "author")
    @Mapping(source = "replies",target = "repliesCount")
    CommentDTO toCommentDTO(Comment comment);
    default int commentSetToInt(Set<Comment> comments){
        return comments.size();
    }
    default Page<CommentDTO> commentSetToCommentDTOPage(Set<Comment> set){
        return null;
    }




}
