package com.ee172.team2.patty.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.ee172.team2.liwen.model.MemberDating;
import com.ee172.team2.patty.repository.FriendSelectRepository;
import com.ee172.team2.patty.service.FriendSelectService;

@Controller
public class FriendSelectController {

	@Autowired
	private FriendSelectRepository FriendSelectDao;
@Autowired
	private FriendSelectService FriendSelectService;

	@GetMapping("/friendselect")
	public ResponseEntity<List<MemberDating>> findByHeightSalaryAndJob(@RequestParam Integer minHeight, @RequestParam Integer maxHeight,
																	  @RequestParam Integer minSalary, @RequestParam Integer maxSalary, @RequestParam String job) {
		List<MemberDating> friend =FriendSelectService.findByHeightSalaryAndJob(minHeight, maxHeight, minSalary, maxSalary, job);
		return  ResponseEntity.ok(friend);
	}



	@GetMapping("/friend/search")
	public ResponseEntity<List<MemberDating>> searchMembers(@RequestParam("minHeight") Integer minHeight, @RequestParam("maxHeight") Integer maxHeight, @RequestParam("minSalary") Integer minSalary, @RequestParam("maxSalary") Integer maxSalary, @RequestParam("jobName") String jobName, @RequestParam("gender") String gender) {
		List<MemberDating> members = FriendSelectService.findMatchingMembers(minHeight, maxHeight, minSalary, maxSalary, jobName, gender);
		return ResponseEntity.ok(members);
	}

}
