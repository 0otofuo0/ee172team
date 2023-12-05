package com.ee172.team2.nemo.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ee172.team2.nemo.model.Tableinfo;
import com.ee172.team2.nemo.model.WeddingGuest;
import com.ee172.team2.nemo.service.WeddingGuestService;
import com.ee172.team2.nemo.service.WeddingTableService;

@Controller
@RequestMapping("/weddingTable")
public class WeddingTableController {

	@Autowired
	private WeddingTableService weddingTableService;

	@Autowired
	private WeddingGuestService weddingGuestService;

	@RequestMapping("/") //
	public String mainPage(ModelMap model, @RequestParam Integer id) {
		List<Tableinfo> tableinfos = weddingTableService.findByWeddingId(id);
		List<WeddingGuest> guests = weddingGuestService.findByWeddingId(id);
		model.addAttribute("weddingId", id);
		model.addAttribute("tableCount", tableinfos.size());
		model.addAttribute("list", tableinfos);
		model.addAttribute("guests", guests);
		return "nemo/weddingTable";
	}

	@ResponseBody
	@PostMapping("/save") // 處理POST請求，創建新的weddingCouple
	public String save(@RequestBody List<String> tableNames) {
		List<Tableinfo> tableinfos = new ArrayList<Tableinfo>();
		tableNames.forEach(tb -> {
			Tableinfo iTableinfo = new Tableinfo();
			iTableinfo.setWeddingId(Integer.parseInt(tb.split(",")[0]));
			iTableinfo.setTableno(tb.split(",")[1]);
			iTableinfo.setTablename(tb.split(",")[2]);
			tableinfos.add(iTableinfo);
		});
		weddingTableService.save(tableinfos);
		return "true";
	}

	@ResponseBody
	@PostMapping("/saveGroup") // 處理POST請求，創建新的weddingCouple
	public String saveGroup(@RequestBody Map<String, String> param) {
		WeddingGuest wGuest = new WeddingGuest();
		wGuest.setGuestId(Integer.valueOf(param.get("guestId")));

		WeddingGuest guest = weddingGuestService.findById(wGuest.getGuestId()).get();
		guest.setTableNumber(param.get("tableNumber"));
		weddingGuestService.save(guest);
		return "true";
	}

}
