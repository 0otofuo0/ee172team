package com.ee172.team2.nemo.service;

import java.util.List;
import java.util.Optional;

import com.ee172.team2.nemo.model.Tableinfo;

public interface WeddingTableService {

	public List<Tableinfo> findAll();

	public Optional<Tableinfo> findById(Integer id);

	public List<Tableinfo> save(List<Tableinfo> prices);

	public List<Tableinfo> findByWeddingId(Integer weddingId);

}
