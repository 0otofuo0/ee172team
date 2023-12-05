package com.ee172.team2.steven.model;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.mapping.List;

import java.sql.Blob;
import java.sql.Timestamp;
import java.util.Date;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name="employees")
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer  empId;

    private  String empAccount;

    private String empName;

    private String empPwd;

    private String email;

    @Temporal(TemporalType.TIMESTAMP)
    private Date createTime;

    private String gender;

    private Date birthDate;

  //  private  Integer depId;

    private String preferType;

//    private Integer position;

    private byte[] empPhoto;

    private  Integer role;

    private Integer salary;

    private  String address;

    private String phone;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "workTypes", referencedColumnName = "id")
    private  ScheduleSetting workTypes;

    @JsonManagedReference("employee-scheduleManager")
    @OneToMany(mappedBy = "employee", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<ScheduleManager> scheduleManagers;

    @JsonManagedReference("employee-notice")
    @OneToMany(mappedBy = "employee", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Notice> notices;

    @JsonManagedReference("employee-employeeLeaveBalance")
    @OneToMany(mappedBy = "employee", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<EmployeeLeaveBalance> leaveBalances;

    @JsonManagedReference("employee-apply")
    @OneToMany(mappedBy = "employee", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Apply> applications;

    @JsonBackReference("employee-department")
    @ManyToOne
    @JoinColumn(name = "depId", referencedColumnName = "id")
    private Department department;

    @JsonManagedReference("employee-clockin")
    @OneToMany(mappedBy = "employee", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Clockin> clockins;

    @JsonBackReference("employee-position")
    @ManyToOne
    @JoinColumn(name = "position", referencedColumnName = "id")
    private Position position;


    public String getMaskedPassword() {
        return empPwd.replaceAll(".", "*");
    }

    @PrePersist
    protected void onPrePersist() {
        if (this.workTypes == null) {
            this.workTypes = null;
        }
        if (this.position == null) {
            this.position = null;
        }
        if (this.department == null) {
            this.department = null;
        }
    }

}
