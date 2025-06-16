package com.example.fileupload.fileupload.controller;


import com.example.fileupload.fileupload.service.S3Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/files")
public class FileController {

    private final S3Service s3Service;

    @Autowired
    public FileController(S3Service s3Service) {
        this.s3Service = s3Service;
    }

    @PostMapping("/upload")
    public ResponseEntity<String> uploadFile(@RequestParam("file") MultipartFile file,
                                             @RequestParam(value = "path", required = false, defaultValue = "") String path) {
        try {
            String key = path.isEmpty() ? file.getOriginalFilename() : path + "/" + file.getOriginalFilename();
            s3Service.uploadFile(key, file.getInputStream(), file.getSize(), file.getContentType());
            return ResponseEntity.ok("File uploaded successfully: " + key);
        } catch (IOException e) {
            return ResponseEntity.internalServerError().body("Upload failed");
        }
    }

    @GetMapping("/list")
    public ResponseEntity<List<String>> listFiles(@RequestParam(value = "prefix", required = false, defaultValue = "") String prefix) {
        return ResponseEntity.ok(s3Service.listObjects(prefix));
    }

    @GetMapping("/download")
    public ResponseEntity<String> generateDownloadLink(@RequestParam("file") String fileKey) {
        return ResponseEntity.ok(s3Service.getPresignedUrl(fileKey));
    }
}
