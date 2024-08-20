package com.cuongpn.controller;

import com.cuongpn.dto.requestDTO.UploadImageRequest;
import com.cuongpn.dto.responeDTO.ResponseData;
import com.cuongpn.service.FileService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.web.bind.annotation.*;


@RestController
@AllArgsConstructor
@RequestMapping("/file")
public class FileController {
    private FileService fileService;
    @PostMapping("/upload")
    public ResponseData<?> upload(@ModelAttribute  @Valid UploadImageRequest request){
        return fileService.upLoadFile(request.getImage());
    }
}
