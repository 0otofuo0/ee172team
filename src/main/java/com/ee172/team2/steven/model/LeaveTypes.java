package com.ee172.team2.steven.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Objects;
import java.util.Set;
@NoArgsConstructor
@AllArgsConstructor
@Data
@Table(name="LeaveTypes")
@Entity
public class LeaveTypes {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id

    private Integer id;


    private String leaveType;


    private String description;


    private Integer yearlyLimit;

    @JsonManagedReference
    @OneToMany(mappedBy = "leaveType", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<EmployeeLeaveBalance> employeeLeaveBalances;


    @JsonManagedReference
    @OneToMany(mappedBy = "leaveType", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Apply> applications;

}
