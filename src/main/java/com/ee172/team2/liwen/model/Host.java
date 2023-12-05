package com.ee172.team2.liwen.model;

import java.util.Date;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.Data;

@Data
@Entity
@Table(name = "host")
public class Host {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer hostId;
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date hostCt;
	
	@OneToOne
	@JoinColumn(name = "memberId")
	private Member member;
	
//	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
//	private List<Activity> activity;
	
	
}
