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
@Table(name = "transaction")
public class Transaction {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer transactionId;
	
//	@OneToOne
//	@JoinColumn(name = "orderId")
//	private Order orderId;
	
	private String transactionType;
	
	private Integer paymentMethodId;
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date transactionDate;
	
	private String status;
	
	private String responseCode;
	
	private String responseMessage;
	
}
