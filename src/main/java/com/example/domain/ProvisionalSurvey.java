package com.example.domain;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

@Entity
public class ProvisionalSurvey {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Asset asset;

    private String surveyorName;

    private LocalDate surveyDate;

    private String assetCondition;

    private Double estimatedRepairCost;

    private Double estimatedScrapValue;

    private String recommendation;

    @Column(length = 1000)
    private String remarks;

    // ===== GETTERS AND SETTERS =====

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Asset getAsset() {
        return asset;
    }

    public void setAsset(Asset asset) {
        this.asset = asset;
    }

    public String getSurveyorName() {
        return surveyorName;
    }

    public void setSurveyorName(String surveyorName) {
        this.surveyorName = surveyorName;
    }

    public LocalDate getSurveyDate() {
        return surveyDate;
    }

    public void setSurveyDate(LocalDate surveyDate) {
        this.surveyDate = surveyDate;
    }

    public String getAssetCondition() {
        return assetCondition;
    }

    public void setAssetCondition(String assetCondition) {
        this.assetCondition = assetCondition;
    }

    public Double getEstimatedRepairCost() {
        return estimatedRepairCost;
    }

    public void setEstimatedRepairCost(Double estimatedRepairCost) {
        this.estimatedRepairCost = estimatedRepairCost;
    }

    public Double getEstimatedScrapValue() {
        return estimatedScrapValue;
    }

    public void setEstimatedScrapValue(Double estimatedScrapValue) {
        this.estimatedScrapValue = estimatedScrapValue;
    }

    public String getRecommendation() {
        return recommendation;
    }

    public void setRecommendation(String recommendation) {
        this.recommendation = recommendation;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }
}