package com.ee172.team2.nemo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Service
public class MailService {

	@Autowired
	JavaMailSender javaMailSender;

	public void sendMail(String from, String to, String cc, String subject, String text) {

		SimpleMailMessage mailMessage = new SimpleMailMessage();
		mailMessage.setFrom(from);
		mailMessage.setTo(to);
		mailMessage.setCc(cc);
		mailMessage.setSubject(subject);
		mailMessage.setText(text);
	
		javaMailSender.send(mailMessage);
	}

}
