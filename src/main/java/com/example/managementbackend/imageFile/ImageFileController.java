package com.example.managementbackend.imageFile;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/file")
public class ImageFileController {

    @Autowired
    private ImageFileService imageFileService;

    @Value("${project.image}")
    private String path;

    public ImageFileController(ImageFileService imageFileService) {
        this.imageFileService = imageFileService;
    }

    //post image upload
    @PostMapping("/upload")
    public ResponseEntity<ImageFileResponse> fileUpload(@RequestParam("image")  MultipartFile image)  {
        String fileName= null;
        try {
            fileName = this.imageFileService.uploadImage(path, image);
        } catch (IOException e) {
            e.printStackTrace();
            return new ResponseEntity<>(new ImageFileResponse(null,"Image is Not Uploaded due to some Error"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(new ImageFileResponse(fileName,"Image is Uploaded"), HttpStatus.OK);
    }

}
