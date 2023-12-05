package com.ee172.team2.liwen.model;

import java.util.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.Data;

@Data
@Entity
@Table(name = "transactionLog")
public class transactionLog {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer logId;
	
	@OneToOne
	@JoinColumn(name = "transactionId")
	private Transaction transactionId;
	
	private String logMessage;
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date logTimestamp;
	
//	@OneToOne
//	@JoinColumn(name = "reserveId")
//	private Reserve reserveId;
//	
//	@OneToOne
//	@JoinColumn(name = "orderId")
//	private Order orderId;
}
