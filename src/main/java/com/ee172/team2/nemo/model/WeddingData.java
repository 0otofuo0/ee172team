package com.ee172.team2.nemo.model;

import java.util.Date;

import com.ee172.team2.liwen.model.Member;
import com.ee172.team2.lpt.model.Arena;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "weddingData")
public class WeddingData {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "weddingId")
    private Integer weddingId;

    @Column(name = "weddingDate")
    private Date weddingDate;

    
    //培桐的場地ID
    @ManyToOne
    @JoinColumn(name = "arenaId",referencedColumnName = "arenaId")
    private Arena arenaId;

    //莉雯的會員ID
    @OneToOne
    @JoinColumn(name = "memberId",referencedColumnName = "memberId")
    private Member weddingMemberId;

    //培桐活動的價格
    @Column(name = "activityPrice")
    private Integer activityPrice;

    // Constructors, getters, and setters
}

