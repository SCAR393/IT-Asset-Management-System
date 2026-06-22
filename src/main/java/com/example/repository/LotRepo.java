package com.example.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.domain.Lot;

public interface LotRepo extends JpaRepository<Lot, Long> {
	
	
}