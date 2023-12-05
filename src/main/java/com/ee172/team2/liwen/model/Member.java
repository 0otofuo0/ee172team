package com.ee172.team2.liwen.model;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.Data;

@Data
@Entity
@Table(name = "member")
public class Member {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer memberId;
	
	private String memberName;
	
	private String memberEmail;
	
	private String memberPwd;
	
	private Integer memberPhone;
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date memberBirth;
	
	private String memberGender;
	
	private String memberAddress;
	
	@Lob // 告訴PO此物件要被持久化為BLOB或CLOB(字串)，以有效的存除和檢索
	@JsonIgnore // 進行 JSON 序列化時，不要序列化本屬性(序列化：轉換為JSON格式)
	private byte[] memberPhoto; 
	
	private Integer permission;
	
	private boolean enabled;
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date memberCt;

	@JsonManagedReference
	@OneToMany(mappedBy = "member", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Interest> interests;


}
