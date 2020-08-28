package com.example.demo.service;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import com.example.demo.Model.Images;
import com.example.demo.Model.Seller;
import com.example.demo.Model.SellerHasImg;
import com.example.demo.Model.User;
import com.example.demo.VO.SellerVo;
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

 
    @org.springframework.transaction.annotation.Transactional
    public Seller insertSeller(SellerVo vo) {
        //List<String> urls= imageService.upload(files);
      
        Seller seller = new Seller();
 
        seller.setPortfolio(vo.getPortfolio());
        System.out.print("12222222222222222222222");
        seller.setReviewCount(0L);
        User user = userRepository.findById(vo.getId()).get();
        user.getUserName();
   
        seller.setUser(user);
       // seller.setUser(user);
        /*
        for(int i = 0; i < urls.size(); ++i) {
            Images image = new Images();
            image.setUrl(urls.get(i));
            
            SellerHasImg sellerHasImg = new SellerHasImg();
            sellerHasImg.setImages(image);
            sellerHasImg.setSeller(seller);
            sellerHasImgRepo.save(sellerHasImg);
            imageRepository.save(image);
        }*/
        return sellerRepository.save(seller);
        
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
   
}