package com.example.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.domain.ProvisionalSurvey;
import com.example.repository.ProvisionalSurveyRepo;
import com.example.service.ProvisionalSurveyService;

@Service
public class ProvisionalSurveyServiceImpl
       implements ProvisionalSurveyService {

    @Autowired
    private ProvisionalSurveyRepo repository;

    @Override
    public ProvisionalSurvey saveSurvey(
            ProvisionalSurvey survey) {

        return repository.save(survey);
    }

    @Override
    public List<ProvisionalSurvey> getAllSurveys() {

        return repository.findAll();
    }
}