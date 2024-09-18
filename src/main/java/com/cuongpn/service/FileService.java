package com.cuongpn.service;

import com.cuongpn.dto.responeDTO.FileResponseDTO;
import com.cuongpn.dto.responeDTO.ResponseData;
import org.springframework.web.multipart.MultipartFile;

public interface FileService {
    public ResponseData<FileResponseDTO> upLoadFile(MultipartFile multipartFile);
    public void init();
}
