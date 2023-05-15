package com.example.swappify_image_open.service;

import com.example.swappifyapimodel.model.dto.ItemDTO;
import com.example.swappifyauthconnector.connector.AuthConnector;
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
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.model.S3Exception;

import java.math.BigDecimal;
import java.util.UUID;

import java.io.IOException;

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

}