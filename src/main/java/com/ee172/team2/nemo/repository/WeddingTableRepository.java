package com.ee172.team2.nemo.repository;

import com.ee172.team2.nemo.model.Tableinfo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface WeddingTableRepository extends JpaRepository<Tableinfo, Integer> {
	
	public List<Tableinfo> findByWeddingId(Integer id);
	
	public void deleteByWeddingId(Integer id);
}
