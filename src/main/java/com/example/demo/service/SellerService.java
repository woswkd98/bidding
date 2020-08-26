package com.example.demo.service;

import java.util.List;

import com.example.demo.Model.Images;
import com.example.demo.Model.Seller;
import com.example.demo.Model.SellerHasImg;
import com.example.demo.repository.master.ImageRepository;
import com.example.demo.repository.master.SellerHasImgRepo;
import com.example.demo.repository.master.SellerRepository;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class SellerService {
    
    private final SellerRepository sellerRepository;
    private final SellerHasImgRepo sellerHasImgRepo;
    private final ImageRepository imageRepository;
    private final ImageService imageService;



    public Seller insertSeller(MultipartFile[] files, Seller seller) {
        List<String> urls= imageService.upload(files);
        for(int i = 0; i < urls.size(); ++i) {
            Images image = new Images();
            image.setUrl(urls.get(i));
            
            SellerHasImg sellerHasImg = new SellerHasImg();
            sellerHasImg.setImages(image);
            sellerHasImg.setSeller(seller);
            sellerHasImgRepo.save(sellerHasImg);
            imageRepository.save(image);
        }
        return sellerRepository.save(seller);
        
    } 

}