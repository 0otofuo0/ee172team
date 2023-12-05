package com.ee172.team2.liwen.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "memberDating")
public class MemberDating {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer mDatingId;
	
	private Integer height;
	
	private Integer salary;
	
	@OneToOne
	@JoinColumn(name="memberId")
	private Member memberId;
	
	
@JsonBackReference("Job-memberDating")
	@ManyToOne
	@JoinColumn(name="jobId",referencedColumnName = "jobId")
	private Job jobId;
}
