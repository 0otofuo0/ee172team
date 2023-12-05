package com.ee172.team2.nemo.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity
public class WeddingTable {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer tableId;

	private String tableCount;
}
