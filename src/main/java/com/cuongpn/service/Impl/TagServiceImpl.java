package com.cuongpn.service.Impl;

import com.cuongpn.dto.responeDTO.ResponseData;
import com.cuongpn.dto.responeDTO.TagDTO;
import com.cuongpn.entity.Tag;
import com.cuongpn.mapper.TagMapper;
import com.cuongpn.repository.TagRepository;
import com.cuongpn.service.TagService;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.social.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Data
@AllArgsConstructor
public class TagServiceImpl implements TagService {
    private final TagRepository tagRepository;

    private final TagMapper tagMapper;


    @Override
    public Tag getTagByTagName(String tagName) {
        return tagRepository.findByName(tagName).orElseThrow(() -> new RuntimeException("Tag not found by name " + tagName));

    }

    @Override
    public ResponseData<List<TagDTO>> findAll() {
        List<Tag> res =  tagRepository.findAll();
        return new ResponseData<>(HttpStatus.OK.value(), "Success",res.stream().map(tagMapper::tagToTagDTO).toList());
    }
}
