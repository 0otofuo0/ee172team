package com.ee172.team2.lpt.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ee172.team2.lpt.model.Arena;
import com.ee172.team2.lpt.repository.ArenaRepository;

@Service
public class ArenaService {

	@Autowired
	private ArenaRepository arenaDAO;

	public Arena findById(Integer id) {
		Optional<Arena> optional = arenaDAO.findById(id);

		if (optional.isPresent()) {
			return optional.get();
		}

		return null;

	}

	public List<Arena> findAll() {
		return arenaDAO.findAll();
	}

}
