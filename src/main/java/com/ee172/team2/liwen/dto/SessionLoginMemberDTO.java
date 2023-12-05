package com.ee172.team2.liwen.dto;

import java.util.Date;

import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.Data;

@Data
public class SessionLoginMemberDTO {

	private Integer memberId;

	private String memberName;

	private String memberEmail;
//
//	private String memberPwd;
//
//	private Integer memberPhone;

	private Integer memberAge;

	private String memberGender;

//	private String memberAddress;

	private String memberPhoto;

//	private Integer permission;
//
//	private boolean enabled;
//
//	@Temporal(TemporalType.TIMESTAMP)
//	private Date memberCt;
//
//	private Integer mInterestId;
}
