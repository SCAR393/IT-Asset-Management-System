package com.example.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.domain.ProvisionalSurvey;

public interface ProvisionalSurveyRepo
       extends JpaRepository<ProvisionalSurvey, Long> {

}