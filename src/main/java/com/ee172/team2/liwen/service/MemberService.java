package com.ee172.team2.liwen.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.ee172.team2.liwen.model.Member;
import com.ee172.team2.liwen.repository.MemberRepository;

@Service
public class MemberService {

	@Autowired
	private MemberRepository memberDAO;

	@Autowired
	private PasswordEncoder pwdEncoder;

	public Member addMember(Member member) {
		member.setMemberPwd(pwdEncoder.encode(member.getMemberPwd()));
		return memberDAO.save(member);
	}
	
	public boolean checkMemberEmailIfExist (String memberEmail) {
		Member dbMember = memberDAO.findByMemberEmail(memberEmail);
		
		if(dbMember != null) {
			return true;
		}else {
			return false;
		}
	}

	public Member checkLogin(String memberEmail, String memberPwd) {
		
		Member dbMember = memberDAO.findByMemberEmail(memberEmail);
		
		if(dbMember != null) {
			if(pwdEncoder.matches(memberPwd, dbMember.getMemberPwd())) {
				return dbMember;
			}
		}
		return null;
		
	}
	
}
