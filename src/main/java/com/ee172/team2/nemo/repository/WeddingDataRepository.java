package com.ee172.team2.nemo.repository;

import com.ee172.team2.nemo.model.WeddingCouple;
import com.ee172.team2.nemo.model.WeddingData;
import com.ee172.team2.nemo.model.WeddingGuest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.List;

public interface WeddingDataRepository extends JpaRepository<WeddingData, Integer> {

	List<WeddingData> findByWeddingDateBetween(Date startDate, Date endDate);

	List<WeddingData> findByWeddingMemberIdMemberId(Integer memberId);

	
}
