package com.cuongpn.service.Impl;

import com.cuongpn.dto.responeDTO.ResponseData;
import com.cuongpn.exception.StorageException;
import com.cuongpn.service.FileService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Objects;

@Service

public class FileUploadService implements FileService {
    private final Path rootLocation;
    private final String baseUrl = "http://localhost:8080/public/images/";

    public FileUploadService(){
        this.rootLocation = Paths.get("/upload/images/");
        init();
    }
    @Override
    public ResponseData<?> upLoadFile(MultipartFile multipartFile) {
        try{
            if(multipartFile.isEmpty()){
                throw new StorageException("Failed to store empty file.");
            }
            Path destinationFile = rootLocation.resolve(
                            Objects.requireNonNull(multipartFile.getOriginalFilename()))
                    .normalize().toAbsolutePath();
            if(!destinationFile.getParent().equals(rootLocation.toAbsolutePath())){
                throw new StorageException(
                        "Cannot store file outside current directory.");
            }
            try (InputStream inputStream = multipartFile.getInputStream()){
                Files.copy(inputStream,destinationFile, StandardCopyOption.REPLACE_EXISTING);
            }
            String fileUrl = baseUrl + destinationFile.getFileName().toString();
            return new ResponseData<>(HttpStatus.CREATED.value(), "File upload success",fileUrl);

        }
        catch (IOException e){
            throw  new StorageException("Failed to store file.", e);
        }
    }
    @Override
    public void init() {
        try {
            Files.createDirectories(rootLocation);
        }
        catch (IOException e) {
            throw new StorageException("Could not initialize storage", e);
        }
    }
}
