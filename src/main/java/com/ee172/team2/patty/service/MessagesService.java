package com.ee172.team2.patty.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ee172.team2.patty.model.Messages;
import com.ee172.team2.patty.repository.MessagesRepository;

@Service
public class MessagesService {
	
	@Autowired
	private MessagesRepository mDao;
	
	public void addMessage(Messages msg) {
		mDao.save(msg);
	}
	
	

}
