package com.ee172.team2.nemo.repository;

import com.ee172.team2.nemo.model.WeddingGuest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface WeddingGuestRepository extends JpaRepository<WeddingGuest, Integer> {

	public List<WeddingGuest> findByWeddingId(Integer id);

}
