package com.example.swappify_image_open.model;

import com.example.swappifyapimodel.model.dto.ItemDTO;
import lombok.Builder;
import lombok.Value;

@Builder
@Value
public class Item {
    byte[] image;
    ItemDTO metaData;
}
