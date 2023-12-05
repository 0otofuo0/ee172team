package com.ee172.team2.liwen.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "interest")
public class Interest {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer insId;
	
	private String insName;


	@JsonBackReference
	@ManyToOne
	@JoinColumn(name = "memberId", referencedColumnName = "memberId")
	private Member member;

}
