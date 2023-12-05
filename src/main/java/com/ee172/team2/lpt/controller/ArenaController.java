package com.ee172.team2.lpt.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import com.ee172.team2.lpt.model.Arena;
import com.ee172.team2.lpt.service.ArenaService;

@Controller
public class ArenaController {

	@Autowired
	private ArenaService arenaService;

	@GetMapping("/arena")
	public String showArena(Model model) {

		List<Arena> allArena = arenaService.findAll();

		model.addAttribute("allArena", allArena);

		return "/lpt/arena/arena";
	}

}
