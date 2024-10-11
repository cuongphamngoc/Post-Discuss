package com.cuongpn.mapper;

import com.cuongpn.dto.responeDTO.ArticleDTO;
import com.cuongpn.dto.responeDTO.ArticleDetailDTO;

import com.cuongpn.entity.Article;

import com.cuongpn.entity.Comment;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.Set;


@Mapper(componentModel = "spring", uses = {CommentMapper.class , VoteMapper.class , TagMapper.class})
public interface ArticleMapper {

    @Mapping(source = "createdBy",target = "author")
    @Mapping(source = "comments", target = "totalComments", qualifiedByName = "getTotalComments")

    ArticleDTO toArticleDTO(Article article);


    @Mapping(source = "createdBy",target = "author")
    ArticleDetailDTO toArticleDetailDTO(Article article);
    @Named("getTotalComments")
    default int getTotalComments(Set<Comment> comments){
        return comments.size();
    }


}
