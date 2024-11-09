package com.cuongpn.controller;

import com.cuongpn.dto.requestDTO.CreateTagDTO;
import com.cuongpn.dto.responeDTO.ResponseData;
import com.cuongpn.dto.responeDTO.TagDTO;
import com.cuongpn.service.TagService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tag")
@AllArgsConstructor
public class TagController {
    private final TagService tagService;
    @GetMapping
    public ResponseData<List<TagDTO>> getAllTag(){
        return tagService.findAll();
    }
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseData<TagDTO> handleCreateNewTag(@RequestBody @Valid CreateTagDTO createTagDTO){
        return new ResponseData<>(HttpStatus.CREATED.value(), "Tag created successful",tagService.createNewTag(createTagDTO));
    }
}
