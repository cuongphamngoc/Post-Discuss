package com.cuongpn.mapper;

import com.cuongpn.dto.responeDTO.TagDTO;
import com.cuongpn.entity.Tag;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface TagMapper {

    TagMapper INSTANCE = Mappers.getMapper(TagMapper.class);
    TagDTO toTagDTO(Tag tag);

    List<TagDTO> toTagDTOs(Set<Tag> tagSet);

    default List<String> toTagName(Set<Tag> tagSet) {
        return tagSet.stream()
                .map(Tag::getName)
                .collect(Collectors.toList());
    }
}
