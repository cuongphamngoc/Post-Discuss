package com.cuongpn.service;

import com.cuongpn.dto.requestDTO.CreateTagDTO;
import com.cuongpn.dto.responeDTO.ResponseData;
import com.cuongpn.dto.responeDTO.TagDTO;
import com.cuongpn.entity.Tag;

import java.util.List;

public interface TagService {
    public Tag getTagByTagName(String tagName);

    public ResponseData<List<TagDTO>> findAll();

    TagDTO createNewTag(CreateTagDTO createTagDTO);
}
