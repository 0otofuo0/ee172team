package com.ee172.team2.patty.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ee172.team2.liwen.model.MemberDating;

public interface FriendSelectRepository extends JpaRepository<MemberDating, Integer> {

	@Query("SELECT m FROM MemberDating m WHERE (m.height BETWEEN :minHeight AND :maxHeight) "
			+ "AND (m.salary BETWEEN :minSalary AND :maxSalary) AND m.jobId = :job")
	
	List<MemberDating> findByHeightSalaryAndJob(@Param("minHeight") Integer minHeight,
			@Param("maxHeight") Integer maxHeight, @Param("minSalary") Integer minSalary,
			@Param("maxSalary") Integer maxSalary, @Param("job") String job);



	@Query("SELECT md FROM MemberDating md WHERE md.height BETWEEN :minHeight AND :maxHeight AND md.salary BETWEEN :minSalary AND :maxSalary AND md.jobId.jobName = :jobName AND md.memberId.memberGender = :gender")
	List<MemberDating> findByCriteria(@Param("minHeight") Integer minHeight, @Param("maxHeight") Integer maxHeight, @Param("minSalary") Integer minSalary, @Param("maxSalary") Integer maxSalary, @Param("jobName") String jobName, @Param("gender") String gender);


}
