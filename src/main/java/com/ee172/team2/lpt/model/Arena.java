package com.ee172.team2.lpt.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;


@Data
@Table(name="arena")
@Entity
public class Arena {
	
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer arenaId;
	
	private String arenaName;
	
	private String arenaIntro;
	
	private byte[] arenaPhoto;

}
