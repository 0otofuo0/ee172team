package com.ee172.team2.steven.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Objects;
@NoArgsConstructor
@AllArgsConstructor
@Data
@Table(name="EmployeeLeaveBalance")
@Entity
public class EmployeeLeaveBalance {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Integer id;


  // private Integer empId;
//
//
  // private Integer leaveTypeId;


    private Double balanceDays;


    private int year;


    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "leaveTypeId", referencedColumnName = "id")
    private LeaveTypes leaveType;


    @JsonBackReference("employee-employeeLeaveBalance")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "empId", referencedColumnName = "empId")
    private Employee employee;

}
