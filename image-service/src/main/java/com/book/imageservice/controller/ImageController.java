package com.book.imageservice.controller;

import com.book.imageservice.service.ImageService;
import com.mongodb.client.gridfs.model.GridFSFile;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.gridfs.GridFsResource;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;

@RestController
@RequestMapping("/images")
public class ImageController {

    private final ImageService imageService;

    @Autowired
    public ImageController(ImageService imageService) {
        this.imageService = imageService;
    }

    @PostMapping("/upload/{bookId}")
    public ResponseEntity<String> uploadImage(@PathVariable("bookId") Long bookId, @RequestParam("file") MultipartFile file) throws IOException {
        String fileId = imageService.uploadImage(file, bookId);
        return ResponseEntity.ok(fileId);
    }

    @GetMapping("/download/{fileId}")
    public void downloadImage(@PathVariable("fileId") String fileId, HttpServletResponse response) throws IOException {
        imageService.downloadImage(fileId, response);
    }
}

