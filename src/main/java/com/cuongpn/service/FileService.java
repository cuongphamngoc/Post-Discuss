package com.cuongpn.service;

import com.cuongpn.dto.responeDTO.ResponseData;
import org.springframework.web.multipart.MultipartFile;

public interface FileService {
    public ResponseData<?> upLoadFile(MultipartFile multipartFile);
    public void init();
}
