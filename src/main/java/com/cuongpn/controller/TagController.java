package com.cuongpn.controller;

import com.cuongpn.dto.responeDTO.ResponseData;
import com.cuongpn.dto.responeDTO.TagDTO;
import com.cuongpn.service.TagService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/tag")
@AllArgsConstructor
public class TagController {
    private final TagService tagService;
    @GetMapping("/")
    public ResponseData<List<TagDTO>> getAllTag(){
        return tagService.findAll();
    }
}
