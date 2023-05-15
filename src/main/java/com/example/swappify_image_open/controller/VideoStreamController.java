package com.example.swappify_image_open.controller;

import com.example.swappify_image_open.annotations.AllowFullyAuthorizedUser;
import com.example.swappify_image_open.service.ImageService;
import com.example.swappify_image_open.utils.Headers;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;

@Controller
@RequiredArgsConstructor
public class VideoStreamController {

    private final ImageService service;

    @RequestMapping(value = "/fileupload", method = RequestMethod.POST)
    @ResponseBody
    @AllowFullyAuthorizedUser
    public ResponseEntity<Void> singleFileUpload(@RequestPart("file") MultipartFile file,
                                                 @RequestHeader(name = Headers.AUTHORIZATION) @NonNull final String authToken,
                                                 @RequestParam(name = "name") @NonNull final String name,
                                                 @RequestParam(name = "price") @NonNull final BigDecimal price) {
        service.uploadImage(file, authToken, name, price);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
