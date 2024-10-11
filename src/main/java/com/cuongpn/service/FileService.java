package com.cuongpn.service;

import com.cuongpn.dto.responeDTO.FileResponseDTO;
import com.cuongpn.dto.responeDTO.ResponseData;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.concurrent.CompletableFuture;

public interface FileService {
    ResponseData<FileResponseDTO> upLoadFile(MultipartFile multipartFile) throws IOException;
    CompletableFuture<FileResponseDTO> uploadImage(MultipartFile multipartFile) throws IOException;


    void init();
}
