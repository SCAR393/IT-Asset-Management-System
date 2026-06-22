package com.example.controller;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

import com.example.domain.Product;
import com.example.repository.LotRepo;
import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.example.domain.Asset;
import com.example.domain.ProvisionalSurvey;
import com.example.repository.AssetRepo;
import com.example.service.ProvisionalSurveyService;
import com.example.domain.Lot;
import com.example.repository.LotRepo;
import java.util.List;
@Controller
@RequestMapping("/provisional-survey")
public class ProvisionalSurveyController {

    @Autowired
    private ProvisionalSurveyService service;
    @Autowired
    private LotRepo lotRepo;
    @Autowired
    private AssetRepo assetRepo;

    // OPEN SIMPLE SURVEY PAGE
    @GetMapping("/create")
    public String openSurveyPage(Model model) {
        ProvisionalSurvey survey =
                new ProvisionalSurvey();
        model.addAttribute("survey", survey);
        return "provisionalSurvey";
    }

    // OPEN SURVEY WITH ASSET ID
    @GetMapping("/create/{assetId}")
    public String createForm(
            @PathVariable Long assetId,
            Model model) {
        Asset asset =
                assetRepo.findById(assetId)
                .orElseThrow();
        ProvisionalSurvey survey =
                new ProvisionalSurvey();
        survey.setAsset(asset);
        survey.setSurveyDate(LocalDate.now());
        model.addAttribute("survey", survey);
        return "provisionalSurvey";
    }

    // SAVE SURVEY
    @PostMapping("/save")
    public String saveSurvey(
            @ModelAttribute ProvisionalSurvey survey) {
        service.saveSurvey(survey);
        return "redirect:/provisional-survey/all";
    }

    // SHOW ALL SURVEYS
    
    @GetMapping("/report")
    public String generateReport(Model model) {
        model.addAttribute(
                "surveys",
                service.getAllSurveys());
        model.addAttribute(
                "lots",
                lotRepo.findAll());

        return "provisionalSurveyReport";
    }
    
    @GetMapping("/lot/{id}")
    @ResponseBody
    public List<Map<String,Object>> getLotAssets(
            @PathVariable Long id){
        Lot lot = lotRepo.findById(id)
                .orElseThrow();
        return lot.getAssets()
                .stream()
                .map(asset -> {
                    Product p = asset.getProduct();
                    Map<String,Object> row =
                            new HashMap<>();
                    row.put("assetId",
                            asset.getAssetId());
                    row.put("productId",
                            p.getProductId());
                    row.put("productName",
                            p.getProductName());
                    row.put("quantity",
                            p.getQuantity());
                    row.put("price",
                            p.getPrice());
                    row.put("purchaseDate",
                            p.getPurchaseDate());
                    row.put("warranty",
                            p.getWarranty());
                    row.put("category",
                            p.getCategoryName());
                    return row;
                })
                .toList();
    }

    }