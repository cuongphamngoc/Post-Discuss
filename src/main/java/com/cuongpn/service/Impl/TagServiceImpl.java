package com.cuongpn.service.Impl;

import com.cuongpn.dto.requestDTO.CreateTagDTO;
import com.cuongpn.dto.responeDTO.ResponseData;
import com.cuongpn.dto.responeDTO.TagDTO;
import com.cuongpn.entity.Tag;
import com.cuongpn.enums.TagType;
import com.cuongpn.exception.AppException;
import com.cuongpn.exception.ErrorCode;
import com.cuongpn.mapper.TagMapper;
import com.cuongpn.repository.TagRepository;
import com.cuongpn.service.TagService;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

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
        return new ResponseData<>(HttpStatus.OK.value(), "Success",res.stream().map(tagMapper::toTagDTO).toList());
    }

    @Override
    public TagDTO createNewTag(CreateTagDTO createTagDTO) {

        tagRepository.findByName(createTagDTO.getName())
                .ifPresent(tag -> {
                    throw new AppException(ErrorCode.TAG_ALREADY_EXISTED);
                });
        Tag tag = new Tag(createTagDTO.getName(), TagType.valueOf(createTagDTO.getTagType()));
        return tagMapper.toTagDTO(tagRepository.save(tag));

    }
    private Set<Tag> getAllAdminTag(TagType tagType){
        return tagRepository.findByTagType(tagType);
    }
}
