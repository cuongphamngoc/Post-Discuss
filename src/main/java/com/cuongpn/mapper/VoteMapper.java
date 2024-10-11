package com.cuongpn.mapper;

import com.cuongpn.dto.responeDTO.VoteCount;
import com.cuongpn.dto.responeDTO.VoteDTO;
import com.cuongpn.entity.Article;
import com.cuongpn.entity.Post;
import com.cuongpn.entity.Vote;
import com.cuongpn.enums.VoteType;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.Set;

@Mapper(componentModel = "spring",uses = {UserMapper.class})
public interface VoteMapper {
    @Mapping(source = "createdBy", target = "author")
    VoteDTO toVoteDTO(Vote vote);

}
