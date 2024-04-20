package com.blog.demo.controller;

import com.blog.demo.service.ImageUploaderService;
import io.minio.GetObjectArgs;
import io.minio.GetPresignedObjectUrlArgs;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import io.minio.errors.*;
import io.minio.http.Method;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/api/images")
@AllArgsConstructor
@Slf4j
public class FileController {

    private final ImageUploaderService imageUploaderService;

    @PostMapping("")
    public ResponseEntity<Object> uploadFile(@RequestParam("file") MultipartFile file) {
        String result;
        try {
            result = imageUploaderService.uploadFile(file);
        } catch (IOException e) {
            log.error("Failed to upload file: " + e.getMessage());
            log.error("Failed to upload file: " + e);
            return (ResponseEntity<Object>) ResponseEntity.internalServerError();
        } catch (Exception e) {
            log.error("some exception occured in uploading file: " + e);
            return (ResponseEntity<Object>) ResponseEntity.internalServerError();
        }
        return ResponseEntity.ok(result);
    }

//    @GetMapping("/download/{fileName}")
//    public ResponseEntity<InputStreamResource> downloadFile(@PathVariable("fileName") String fileName) {
//        try {
//            InputStream stream = minioClient.getObject(
//                    GetObjectArgs.builder()
//                            .bucket(bucketName)
//                            .object(fileName)
//                            .build()
//            );
//
//            HttpHeaders headers = new HttpHeaders();
//            headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + fileName);
//
//            return ResponseEntity.ok()
//                    .headers(headers)
//                    .contentType(MediaType.APPLICATION_OCTET_STREAM)
//                    .body(new InputStreamResource(stream));
//        } catch (Exception e) {
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
//        }
//    }

//    @GetMapping("/temporary-url/{fileName}")
//    public ResponseEntity<String> getTemporaryFileUrl(@PathVariable("fileName") String fileName) {
//        try {
//            String url = getTempUrl(bucketName, fileName);
//            return ResponseEntity.ok(url);
//        } catch (Exception e) {
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to generate temporary URL for file.");
//        }
//    }

//    private String getTempUrl(String bucketName, String objectName) throws IOException, NoSuchAlgorithmException, InvalidKeyException, MinioException {
//        Map<String, String> reqParams = new HashMap<>();
//        reqParams.put("response-content-type", "application/json");
//        String url = minioClient.getPresignedObjectUrl(
//                GetPresignedObjectUrlArgs.builder()
//                        .method(Method.GET)
//                        .bucket(bucketName)
//                        .object(objectName)
//                        .expiry(2, TimeUnit.HOURS)
//                        .extraQueryParams(reqParams)
//                        .build());
//        System.out.println(url);
//        return url;
//    }
}