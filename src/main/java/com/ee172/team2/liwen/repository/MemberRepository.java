package com.ee172.team2.liwen.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ee172.team2.liwen.model.Member;

public interface MemberRepository extends JpaRepository<Member, Integer>{

	Member findByMemberEmail(String memberEmail);
	
}
