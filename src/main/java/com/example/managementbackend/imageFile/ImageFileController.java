package com.example.managementbackend.imageFile;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

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


    //method for serve file
@GetMapping(value = "/profiles/{imageName}",produces =MediaType.IMAGE_JPEG_VALUE )
public void downloadImage(@PathVariable("imageName") String  imageName, HttpServletResponse response) throws IOException {
    InputStream resource = this.imageFileService.getResource(path, imageName);
    response.setContentType(MediaType.IMAGE_JPEG_VALUE);
    StreamUtils.copy(resource,response.getOutputStream());

    }
}
