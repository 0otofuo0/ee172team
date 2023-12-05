package com.ee172.team2.steven.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="apply")
@Data
public class Apply {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Integer id;

//
//    private int applyType;


    private String context;


    private Timestamp startTime;


    private Timestamp endTime;


    private Timestamp createTime;


    private Integer checkApply;


    private String applyResult;


    private Timestamp checkTime;

    //private  Integer applyType;

   // private Integer empId;

@JsonBackReference("employee-apply")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "empId", referencedColumnName = "empId")
    private Employee employee;

    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "applyType", referencedColumnName = "id")
    private LeaveTypes leaveType;


}
