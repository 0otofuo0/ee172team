package com.ee172.team2.nemo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ee172.team2.nemo.model.WeddingCouple;
import com.ee172.team2.nemo.service.WeddingCoupleService;

@Controller
public class DefaultPageController {
	@Autowired
	private WeddingCoupleService service;
	@RequestMapping("/")
	public String toDefaultPage(ModelMap model) {
		List<WeddingCouple> allCouples = service.findAll();
		model.put("list", allCouples);
		WeddingCouple weddingCouple = new WeddingCouple();
		model.put("weddingcouple", weddingCouple);
		return "nemo/weddingCouple";
	}

}
