package com.ee172.team2.nemo.model;



import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;



@Data
@Entity
@Table(name = "weddingGuest")
public class WeddingGuest {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "guestId")
	private Integer guestId;
	
	@Column(name = "weddingId")
	private Integer weddingId;
	
	@Column(name = "guestName")
	private String guestName;
	
	@Column(name = "email")
	private String email;
	
	@Column(name = "phonenumber")
	private String phonenumber;
	
	@Column(name = "relationship")
	private String relationship;
	
	@Column(name = "transportation")
	private String transportation;
	
	@Column(name = "cakeRequest")
	private Integer cakeRequest;
	
	@Column(name = "dietary")
	private String dietary;
	
	@Column(name = "money")
	private String money;
	
	@Column(name = "tableNumber")
	private String tableNumber;

	@Column(name = "qrCode")
	private byte[] qrCode;
	
//	@Column(name = "isArrived")
//    private Boolean isArrived;

	// Constructors, getters, and setters
}
