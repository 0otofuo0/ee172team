package com.ee172.team2.liwen.model;

import com.ee172.team2.steven.model.ScheduleManager;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;

import java.util.Set;

@Data
@Entity
@Table(name = "job")
public class Job {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer jobId;
	
	private String jobName;



	@JsonManagedReference("Job-memberDating")
	@OneToMany(mappedBy = "jobId", cascade = CascadeType.ALL)
	private Set<MemberDating> memberDating;


}
