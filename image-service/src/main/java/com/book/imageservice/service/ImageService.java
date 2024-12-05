package com.book.imageservice.service;

import com.mongodb.client.gridfs.model.GridFSFile;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.gridfs.GridFsResource;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;

@Service
public class ImageService {
    private final GridFsTemplate gridFsTemplate;

    @Autowired
    public ImageService(GridFsTemplate gridFsTemplate) {
        this.gridFsTemplate = gridFsTemplate;
    }

    public String uploadImage(MultipartFile file, Long bookId) throws IOException {
        return gridFsTemplate.store(file.getInputStream(), file.getOriginalFilename()).toString();
    }

    public void downloadImage(String fileId, HttpServletResponse response) throws IOException {

        GridFSFile file = gridFsTemplate.findOne(Query.query(Criteria.where("_id").is(fileId)));
        if (file == null) {
            throw new RuntimeException("File not found with ID: " + fileId);
        }
        GridFsResource resource = gridFsTemplate.getResource(file);

        String filename = URLEncoder.encode(resource.getFilename(), "UTF-8");
        response.setHeader(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + filename + "\"");
        response.setContentType("application/octet-stream");

        try (InputStream inputStream = resource.getInputStream()) {
            FileCopyUtils.copy(inputStream, response.getOutputStream());
            response.flushBuffer();
        }
    }
}