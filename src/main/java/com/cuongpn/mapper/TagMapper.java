package com.cuongpn.mapper;

import com.cuongpn.dto.responeDTO.TagDTO;
import com.cuongpn.entity.Tag;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TagMapper {
    TagDTO tagToTagDTO(Tag tag);
}
