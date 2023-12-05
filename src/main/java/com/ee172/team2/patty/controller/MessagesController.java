package com.ee172.team2.patty.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.ee172.team2.patty.model.Messages;
import com.ee172.team2.patty.service.MessagesService;

@Controller
public class MessagesController {
	
	@Autowired
	private MessagesService mService;
	
	@PostMapping("/message/add")
	public String postMsg(@RequestParam(name = "inputText") String text, Model model) {

		Messages msg = new Messages();
		msg.setMessageText(text);

		mService.addMessage(msg);

//		Messages lastesMsg = mService.findLatest();

//		model.addAttribute("lastesMsg", lastesMsg);

		return "patty/messages/addMessagePage";
	}

}
