package com.example.swappify_image_open.service;

import com.example.swappify_image_open.model.Item;
import com.example.swappifyapimodel.model.dto.ItemDTO;
import com.example.swappifyauthconnector.connector.ItemConnector;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.http.apache.ApacheHttpClient;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.model.S3Exception;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import java.io.IOException;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ImageService {

    private final String bucket = "swappify-image";

    private final AwsBasicCredentials credentials;

    private final ItemConnector itemConnector;

    private S3Client getClient() {
        return S3Client.builder().credentialsProvider(StaticCredentialsProvider.create(credentials))
                .region(Region.EU_CENTRAL_1).httpClient(ApacheHttpClient.create()).build();
    }

    public void uploadImage(final @NonNull MultipartFile file, final @NonNull String authToken, @NonNull String name, @NonNull BigDecimal price) {
        try {
            byte[] bytes = file.getBytes();
            val s3 = getClient();
            UUID uuid = UUID.randomUUID();
            PutObjectRequest putOb = PutObjectRequest.builder()
                    .bucket(bucket)
                    .key(uuid.toString())
                    .build();
            s3.putObject(putOb, RequestBody.fromBytes(bytes));
            var item = new ItemDTO(name, price, uuid.toString());
            itemConnector.addItemMetadata(authToken, item);
        } catch (IOException | S3Exception e) {
            e.printStackTrace();
        }
    }

    public List<Item> getAllItems(final @NonNull String authToken){
        var items = itemConnector.getAll(authToken);
        return getImageItemsFromS3(items);
    }

    public List<Item> getMatchedItems(final @NonNull String authToken, final @NonNull String itemUuid){
        var items = itemConnector.getMatchedItems(authToken, itemUuid);
        return getImageItemsFromS3(items);
    }

    public List<Item> getLikedItems(final @NonNull String authToken, final @NonNull String itemUuid){
        var items = itemConnector.getLikedItems(authToken, itemUuid);
        return getImageItemsFromS3(items);
    }

    public List<Item> getMatchedItemsForSpecific(final @NonNull String token,final @NonNull String itemUuid) {
        var items = itemConnector.getMatchedItemsForSpecific(token, itemUuid);
        return getImageItemsFromS3(items);
    }

    private List<Item> getImageItemsFromS3(List<ItemDTO> items) {
        val s3 = getClient();
        return items.stream().map(itemDTO -> {
            var image = s3.getObjectAsBytes(buildS3Request(itemDTO.getUuid())).asByteArray();
            return Item.builder()
                    .image(image)
                    .metaData(itemDTO)
                    .build();
        }).collect(Collectors.toList());
    }

    private GetObjectRequest buildS3Request(String key){
        return GetObjectRequest.builder()
                .bucket(bucket)
                .key(key)
                .build();
    }
}