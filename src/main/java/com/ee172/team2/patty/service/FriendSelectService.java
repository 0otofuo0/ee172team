package com.ee172.team2.patty.service;

import java.util.List;

import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ee172.team2.liwen.model.MemberDating;
import com.ee172.team2.patty.repository.FriendSelectRepository;
import com.ee172.team2.steven.model.Employee;

@Service
public class FriendSelectService {

	@Autowired
	private FriendSelectRepository FriendSelectDao;

	public List<MemberDating> findByHeightSalaryAndJob(Integer minHeight, Integer maxHeight, Integer minSalary,
			Integer maxSalary, String job) {
		return FriendSelectDao.findByHeightSalaryAndJob(minHeight, maxHeight, minSalary, maxSalary, job);
	}


	public List<MemberDating> findMatchingMembers(Integer minHeight, Integer maxHeight, Integer minSalary, Integer maxSalary, String jobName, String gender) {
		return FriendSelectDao.findByCriteria(minHeight, maxHeight, minSalary, maxSalary, jobName, gender);
	}

}
