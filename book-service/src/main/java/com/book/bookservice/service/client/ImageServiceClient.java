package com.book.bookservice.service.client;

import feign.Response;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

@FeignClient(name = "image-service", url = "http://localhost:8082")
public interface ImageServiceClient {

    @PostMapping(value = "/images/upload/{bookId}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    String uploadImage(@PathVariable("bookId") Long bookId, @RequestPart("file") MultipartFile file);

    @GetMapping("/images/download/{fileId}")
    Response downloadImage(@PathVariable("fileId") String fileId);
}