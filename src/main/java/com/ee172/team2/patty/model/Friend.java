package com.ee172.team2.patty.model;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import com.ee172.team2.liwen.model.Member;
import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.Data;

@Data
@Entity
@Table(name="friend")
public class Friend {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "friendId")
	private Integer friendId;
	
	@OneToOne
	@JoinColumn(name = "memberId1" , referencedColumnName="memberId")
	private Member memberId1;
	
	@OneToOne
	@JoinColumn(name = "memberId2" , referencedColumnName="memberId")
	private Member memberId2;
	
	@JsonFormat(pattern = "yyyy/MM/dd HH:mm:ss", timezone = "GMT+8")
	@DateTimeFormat(pattern = "yyyy/MM/dd HH:mm:ss") // 轉換前端 String 日期到 Java 端日期格式
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "time")
	private Date friendshipDate;
	
	@PrePersist
	public void onCreate() {
		if (friendshipDate == null) {
			friendshipDate = new Date();
		}
	}
}
