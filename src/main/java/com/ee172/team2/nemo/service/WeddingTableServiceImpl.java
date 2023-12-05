package com.ee172.team2.nemo.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ee172.team2.nemo.model.Tableinfo;
import com.ee172.team2.nemo.model.WeddingData;
import com.ee172.team2.nemo.repository.WeddingTableRepository;

@Service
public class WeddingTableServiceImpl implements WeddingTableService {

	@Autowired
	private WeddingTableRepository weddingTableRespository;

	@Override
	public List<Tableinfo> findAll() {
		return weddingTableRespository.findAll();
	}

	@Override
	public Optional<Tableinfo> findById(Integer id) {
		return weddingTableRespository.findById(id);
	}

	@Override
	@Transactional
	public List<Tableinfo> save(List<Tableinfo> tableinfos) {
		weddingTableRespository
				.deleteByWeddingId(tableinfos.get(0).getWeddingId());
		return weddingTableRespository.saveAll(tableinfos);
	}

	@Override
	public List<Tableinfo> findByWeddingId(Integer weddingId) {
		return weddingTableRespository.findByWeddingId(weddingId);
	}

}
