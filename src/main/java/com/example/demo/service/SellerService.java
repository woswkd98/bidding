package com.example.demo.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import org.springframework.transaction.annotation.Transactional;

import com.example.demo.entity.Images;
import com.example.demo.entity.Seller;
import com.example.demo.entity.SellerHasImg;
import com.example.demo.entity.User;
import com.example.demo.repository.master.ImageRepository;
import com.example.demo.repository.master.SellerHasImgRepo;
import com.example.demo.repository.master.SellerRepository;
import com.example.demo.repository.master.UserRepository;

import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.multipart.MultipartFile;

import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class SellerService {
    
    private final SellerRepository sellerRepository;
    private final SellerHasImgRepo sellerHasImgRepo;
    private final ImageRepository imageRepository;
    private final ImageService imageService;
    private final UserRepository userRepository;

 
    @Transactional
    public Seller insertSeller(long id, String portfolio,MultipartFile profile,  MultipartFile exampleImages[],String phone, Long[] deleteImages) {
        //List<String> urls= imageService.upload(files);
        Optional<User> ops = userRepository.findById(id);
        if(!ops.isPresent()) {
            return null;
        }
        

        User user = ops.get();
        if(phone != null)
            user.setPhone(phone);
     
           
        if(profile != null) {
            Images image = null;
            if(user.getImages() == null){
                image = new Images();
                image.setUrl(imageService.upload(profile));
            }
            else {
                image = user.getImages();
                imageService.deleteImage(image.getUrl());
                user.getImages().setUrl(imageService.upload(profile));
            }
            
            imageRepository.save(image);
            user.setImages(image);
        }
 
        userRepository.save(user);
        Seller seller = sellerRepository.findByUserId(user.getId());
        if(seller == null) {
            seller = new Seller();
        }

        if(portfolio != null)
            seller.setPortfolio(portfolio);
        
        
        System.out.println("existprofile");
        insertImage(exampleImages, seller, deleteImages);
        
        
        

        seller.setUser(user);
        
        return sellerRepository.save(seller);
        
    } 
    @Transactional
    private List<String> insertImage(MultipartFile files[], Seller seller, Long[] deleteImages) {
           
        List<String> urls = new ArrayList<>();
        
       

        if(deleteImages != null) {
            List<Images> list = imageRepository.findAllById(Arrays.asList(deleteImages));
            Images image = null;
            List<SellerHasImg> shi = null;
            for(int i = 0; i < list.size(); ++i) {
                image = list.get(i);
                imageService.deleteImage(image.getUrl());
                shi =  image.getSellerHasImgs();
                sellerHasImgRepo.deleteAll(shi); 
            }

            imageRepository.deleteAll(list);
        
        } 
        
        
        if(files == null) return null;
        for(int i = 0; i < files.length; ++i) {
            Images image = new Images();
            
            image.setUrl(imageService.upload(files[i]));
            System.out.println(image.getUrl());
            imageRepository.save(image);
            SellerHasImg sellerHasImg = new SellerHasImg();
            sellerHasImg.setImages(image);
            sellerHasImg.setSeller(seller);
            
            sellerHasImgRepo.save(sellerHasImg);
           
            urls.add(image.getUrl());
        }
        
        return urls;

    }
    
    public List<Seller> findAll() {
        return sellerRepository.findAll();
    }

    public Seller getOne(long id) {
        return sellerRepository.getOne(id);
    }

    public Optional<Seller> findById(long id) {
        return sellerRepository.findById(id);
    }

    public List<Images> getImageBySellerId(long sellerId) {
        return imageRepository.getImgBySeller(sellerId);
    }

 

   
}