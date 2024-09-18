package com.cuongpn.mapper;

import com.cuongpn.dto.responeDTO.ArticleResponseDTO;
import com.cuongpn.entity.Article;
import com.cuongpn.entity.Tag;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.Collections;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Mapper
public interface ArticleMapper {

    @Mapping(source = "user.userid",target = "author.id")
    @Mapping(source = "user.name",target = "author.name")
    @Mapping(source = "user.avatarUrl",target = "author.avatarUrl")
    @Mapping(source = "createdAt",target = "createdAt")
    ArticleResponseDTO articleToArticleResponseDTO(Article article);
    default Set<String> mapTagsToStrings(Set<Tag> tags) {
        if (tags == null) {
            return Collections.emptySet(); // Trả về Set rỗng nếu tags là null
        }
        return tags.stream()
                .map(Tag::getName) // Chuyển từ Tag sang String (lấy trường name)
                .collect(Collectors.toSet());
    }
}
