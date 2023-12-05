package com.ee172.team2.nemo.repository;

import com.ee172.team2.nemo.model.WeddingCouple;
import com.ee172.team2.nemo.model.WeddingGuest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface WeddingCoupleRepository extends JpaRepository<WeddingCouple, Integer> {

	
}
