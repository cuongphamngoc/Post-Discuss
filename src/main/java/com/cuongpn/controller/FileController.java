package com.cuongpn.controller;

import com.cuongpn.dto.requestDTO.UploadImageDTO;
import com.cuongpn.dto.responeDTO.FileResponseDTO;
import com.cuongpn.dto.responeDTO.ResponseData;
import com.cuongpn.service.FileService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.concurrent.CompletableFuture;


@RestController
@AllArgsConstructor
@RequestMapping("/file")
public class FileController {
    private final FileService fileService;
    @PostMapping("/test/upload")
    public ResponseData<FileResponseDTO> upload(@ModelAttribute  @Valid UploadImageDTO request) throws IOException {
        return fileService.upLoadFile(request.getImage());
    }
    @PostMapping("/upload")
    public ResponseData<FileResponseDTO> uploadS3(@ModelAttribute UploadImageDTO request ) throws IOException {
        CompletableFuture<FileResponseDTO> future = fileService.uploadImage(request.getImage());
        return new ResponseData<>(HttpStatus.OK.value(), "File upload success",future.join());
    }
}
