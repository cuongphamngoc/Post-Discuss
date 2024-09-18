package com.cuongpn.controller;

import com.cuongpn.dto.requestDTO.UploadImageRequestDTO;
import com.cuongpn.dto.responeDTO.FileResponseDTO;
import com.cuongpn.dto.responeDTO.ResponseData;
import com.cuongpn.service.FileService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;


@RestController
@AllArgsConstructor
@RequestMapping("/file")
public class FileController {
    private FileService fileService;
    @PostMapping("/upload")
    public ResponseData<FileResponseDTO> upload(@ModelAttribute  @Valid UploadImageRequestDTO request){
        return fileService.upLoadFile(request.getImage());
    }
}
