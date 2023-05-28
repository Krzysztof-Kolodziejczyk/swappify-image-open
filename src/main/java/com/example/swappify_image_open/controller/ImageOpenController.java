package com.example.swappify_image_open.controller;

import com.example.swappify_image_open.annotations.AllowFullyAuthorizedUser;
import com.example.swappify_image_open.model.Item;
import com.example.swappify_image_open.service.ImageService;
import com.example.swappify_image_open.utils.Headers;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.util.List;

@Controller
@RequiredArgsConstructor
@CrossOrigin
@RequestMapping("/image")
public class ImageOpenController {

    private final ImageService service;

    @PostMapping(value = "/upload")
    @AllowFullyAuthorizedUser
    public ResponseEntity<Void> getItems(@RequestPart("file") MultipartFile file,
                                         @RequestHeader(name = Headers.AUTHORIZATION) @NonNull final String authToken,
                                         @RequestParam(name = "name") @NonNull final String name,
                                         @RequestParam(name = "price") @NonNull final BigDecimal price) {
        service.uploadImage(file, authToken, name, price);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping(value = "/all")
    @AllowFullyAuthorizedUser
    public ResponseEntity<List<Item>> getItems(@RequestHeader(name = Headers.AUTHORIZATION) @NonNull final String authToken) {
        val items = service.getAllItems(authToken);
        return ResponseEntity.ok(items);
    }

    @GetMapping(value = "/matched/{itemUuid}")
    @AllowFullyAuthorizedUser
    public ResponseEntity<List<Item>> getItems(@RequestHeader("Authorization") @NonNull String token, @PathVariable @NonNull String itemUuid) {
        val items = service.getMatchedItems(token, itemUuid);
        return ResponseEntity.ok(items);
    }

    @GetMapping(value = "/like/{itemUuid}")
    @AllowFullyAuthorizedUser
    public ResponseEntity<List<Item>> getLikedItems(@RequestHeader("Authorization") @NonNull String token, @PathVariable @NonNull String itemUuid) {
        val items = service.getLikedItems(token, itemUuid);
        return ResponseEntity.ok(items);
    }

    @GetMapping(value = "/match/{itemUuid}")
    @AllowFullyAuthorizedUser
    public ResponseEntity<List<Item>> getMatchedItems(@RequestHeader("Authorization") @NonNull String token, @PathVariable @NonNull String itemUuid) {
        val items = service.getMatchedItemsForSpecific(token, itemUuid);
        return ResponseEntity.ok(items);
    }
}
