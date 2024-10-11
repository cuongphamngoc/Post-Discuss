package com.cuongpn.service.Impl;

import com.cuongpn.dto.responeDTO.FileResponseDTO;
import com.cuongpn.dto.responeDTO.ResponseData;
import com.cuongpn.service.FileService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.async.AsyncRequestBody;
import software.amazon.awssdk.services.s3.S3AsyncClient;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import java.io.IOException;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

@Service
@RequiredArgsConstructor
public class AwsS3Service implements FileService {
    private final S3AsyncClient s3AsyncClient;
    @Value("${aws.bucketName}")
    private String bucketName;
    @Value("${aws.region}")
    private String region;
    @Override
    public ResponseData<FileResponseDTO> upLoadFile(MultipartFile multipartFile) throws IOException {
        return new ResponseData<>(HttpStatus.OK.value(), "Upload file successful");
    }

    @Override
    public CompletableFuture<FileResponseDTO> uploadImage(MultipartFile multipartFile) throws IOException {
        String contentType = "image/png";
        long contentLength = multipartFile.getSize();
        String fileKey  =  System.currentTimeMillis() + "_" + multipartFile.getOriginalFilename();
        PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                .bucket(bucketName)
                .contentLength(contentLength)
                .contentType(contentType)
                .key(fileKey )
                .build();
        return s3AsyncClient.putObject(putObjectRequest, AsyncRequestBody.fromInputStream(multipartFile.getInputStream(),contentLength, Executors.newSingleThreadExecutor()))
                .thenApply(response -> {
                    String fileUrl = String.format("https://%s.s3.%s.amazonaws.com/%s", bucketName, region, fileKey);
                    return new FileResponseDTO(fileKey, fileUrl);
                });
    }

    @Override
    public void init() {

    }
}
