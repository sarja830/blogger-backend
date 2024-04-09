package com.blog.demo.service;
import io.minio.*;
import io.minio.errors.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.UUID;

@Slf4j
//@AllArgsConstructor
@RequiredArgsConstructor
@Service
public class ImageUploaderService {
    private final MinioClient minioClient;
    @Value("${minio.bucketName}")
    private String bucketName;
 @Value("${minio.url}")
    private String minioServerUrl;

    public String uploadFile(MultipartFile file) throws IOException, ServerException, InsufficientDataException, ErrorResponseException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException {

           InputStream inputStream = file.getInputStream();
           String contentType =  file.getContentType();
           if(!contentType.startsWith("image/")) {
               throw new IOException("Invalid file type");
           }

           String name = UUID.randomUUID().toString();
           String fileName = file.getOriginalFilename();
//                   'Content-type': 'image',};

           String extension = fileName.substring(fileName.lastIndexOf("."));
           ObjectWriteResponse  res = minioClient.putObject(PutObjectArgs.builder()
                   .bucket(bucketName)
                   .object(name+extension)
                   .stream(inputStream, inputStream.available(), -1)
                   .contentType(contentType)
                   .build());
           String url =  minioServerUrl + bucketName + "/" + res.object();
           return url;

   }
}
