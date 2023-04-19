package com.example.swappify_image_open.service;

import com.example.swappifyauthconnector.connector.AuthConnector;
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

import java.io.IOException;

@Service
@RequiredArgsConstructor
public class ImageService {

    private final String bucket = "swappify-image";

    private final AwsBasicCredentials credentials;

    private final AuthConnector authConnector;

    private S3Client getClient() {
        return S3Client.builder().credentialsProvider(StaticCredentialsProvider.create(credentials))
                .region(Region.EU_CENTRAL_1).httpClient(ApacheHttpClient.create()).build();
    }

    public void uploadImage(MultipartFile file, String authToken) {
        try {
            authConnector.checkAuthStatus(authToken);
        }catch (Exception e){
            e.printStackTrace();
        }
        try {
            byte[] bytes = file.getBytes();
            String name = file.getOriginalFilename();
            val s3 = getClient();
            PutObjectRequest putOb = PutObjectRequest.builder()
                    .bucket(bucket)
                    .key(name)
                    .build();
            s3.putObject(putOb, RequestBody.fromBytes(bytes));
        } catch (IOException | S3Exception e) {
            e.printStackTrace();
        }
    }

}