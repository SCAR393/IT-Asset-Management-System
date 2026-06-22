package com.example.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.repository.SellerRepo;

import jakarta.transaction.Transactional;

import com.example.domain.Seller;

//SellerService.java
@Service
public class SellerService {

@Autowired
private SellerRepo sellerRepository;

public List<Seller> listAll() {
 return sellerRepository.findAll();
}

public Seller getSellerById(String SellerId) {
 return sellerRepository.findById(SellerId).orElseThrow();
}

@Transactional
public Seller save(Seller seller) {
 return sellerRepository.save(seller);
}

public Seller updateSeller(Seller seller) {
 return sellerRepository.save(seller);
}

public void deleteSeller(String SellerId) {
 sellerRepository.deleteById(SellerId);
}


public long countAllSellers() {
	return sellerRepository.count();
}

public List<Seller> searchSeller(String keyword) {
    List<Seller> sellers = sellerRepository.findAll();
    if(keyword != null && !keyword.trim().isEmpty()) {
        return sellers.stream()
                .filter(s ->
                        s.getSellerId().toLowerCase().contains(keyword.toLowerCase()) ||
                        s.getCompanyName().toLowerCase().contains(keyword.toLowerCase()))
                .toList();
    }
    return sellers;
}
}
