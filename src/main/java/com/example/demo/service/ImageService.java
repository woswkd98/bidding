package com.example.demo.service;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.example.demo.Model.Images;
import com.example.demo.common.JpaCrudServiceBase;
import com.example.demo.repository.master.ImageRepository;
import com.example.demo.repository.master.SellerHasImgRepo;
import com.example.demo.repository.master.SellerRepository;
import com.example.demo.repository.master.UserRepository;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartResolver;

import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Value;
import javax.annotation.PostConstruct;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;


@Component
public class ImageService {

    private AmazonS3 s3Client;

    @Value("${cloud.aws.credentials.accessKey}")
    private String accessKey;

    @Value("${cloud.aws.credentials.secretKey}")
    private String secretKey;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    @Value("${cloud.aws.region.static}")
    private String region;

    @PostConstruct
    public void setS3Client() {
        AWSCredentials credentials = new BasicAWSCredentials(this.accessKey, this.secretKey);

        s3Client = AmazonS3ClientBuilder.standard().withCredentials(new AWSStaticCredentialsProvider(credentials))
                .withRegion(this.region).build();
    }

    public String upload(MultipartFile files) {
        File convFile = null;
        String strID = null;
        if(s3Client == null) {
            System.out.print("asdgfasd");
            return "awetgawet";
        }

        try {
            ObjectMetadata omd = new ObjectMetadata();
            omd.setContentType(files.getContentType());
            omd.setContentLength(files.getSize());
            omd.setHeader("filename", files.getOriginalFilename());

            convFile = new File(files.getOriginalFilename());
            strID = files.getOriginalFilename();
            s3Client.putObject(new PutObjectRequest(bucket, strID, files.getInputStream(), omd));
            s3Client.setObjectAcl(bucket,strID,CannedAccessControlList.PublicRead);

            
        } catch (IOException e) {
            //TODO: handle exception
        } 
        
        return s3Client.getUrl(bucket, strID).toString();
    }

    public void deleteImage(String key) {
        s3Client.deleteObject(bucket, key);
    }

  
 
}