package com.ee172.team2.liwen.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.ee172.team2.liwen.dto.SessionLoginMemberDTO;
import com.ee172.team2.liwen.model.Member;
import com.ee172.team2.liwen.service.MemberService;

import jakarta.servlet.http.HttpSession;

@Controller
public class MemberController {

	@Autowired
	private MemberService memberService;
	
	@GetMapping("/member/register")
	public String goRegisterPage() {
		return "liwen/member/memberRegisterPage";
	}
	
	@PostMapping("/member/postRegister")
	public String postRegisterForm(
			@RequestParam("memberEmail") String loginEmail, 
			@RequestParam("memberPwd") String loginPwd,
			Model model) {
		
		boolean isExist = memberService.checkMemberEmailIfExist(loginEmail);
		
		if(isExist) {
			model.addAttribute("errorMsg", "已經有此帳號密碼");
		}else {
			Member member = new Member();
			member.setMemberEmail(loginEmail);
			member.setMemberPwd(loginPwd);
			
			memberService.addMember(member);
			model.addAttribute("okMsg", "註冊成功");
		}
		
		return "liwen/member/memberRegisterPage";
	}

	@GetMapping("/member/login")
	public String goLoginPage() {
		return "liwen/member/loginPage";
	}

	@PostMapping("/member/login")
	public String postLogin(
			@RequestParam("memberEmail") String loginEmail, 
			@RequestParam("memberPwd") String loginPwd,
			HttpSession httpSession, 
			Model model) {

		Member result = memberService.checkLogin(loginEmail, loginPwd);

		if (result != null) {
			SessionLoginMemberDTO memberDTO = new SessionLoginMemberDTO();
			memberDTO.setMemberId(result.getMemberId());
			memberDTO.setMemberEmail(result.getMemberEmail());

			httpSession.setAttribute("loginMember", memberDTO);
		} else {
			model.addAttribute("loginFail", "帳號密碼錯誤");
		}

		return "liwen/member/loginPage";

	}

// 註解原因：還沒寫html index
//	@GetMapping("/member/logout")
//	public String lougout(HttpSession httpSession) {
//		httpSession.invalidate();
//		return "redirect:index";
//	}
	
	
}
