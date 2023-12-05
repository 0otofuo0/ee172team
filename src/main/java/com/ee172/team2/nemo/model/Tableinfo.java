package com.ee172.team2.nemo.model;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "weddingtable")
public class Tableinfo {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer tableId;

	private String tablename;
	
	private String tableno;

	private Integer weddingId;

	// Constructors, getters, and setters
}
