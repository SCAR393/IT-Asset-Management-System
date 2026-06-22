package com.example.service;

import java.util.List;

import com.example.domain.ProvisionalSurvey;

public interface ProvisionalSurveyService {

    ProvisionalSurvey saveSurvey(
            ProvisionalSurvey survey);

    List<ProvisionalSurvey> getAllSurveys();
}