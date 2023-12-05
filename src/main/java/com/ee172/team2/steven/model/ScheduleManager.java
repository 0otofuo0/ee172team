package com.ee172.team2.steven.model;


import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name="scheduleManager")
public class ScheduleManager {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Temporal(TemporalType.TIMESTAMP)
    private Date day;


    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "shiftType", referencedColumnName = "id")
    private ScheduleSetting shiftType;

    @JsonBackReference("employee-scheduleManager")
    @ManyToOne
    @JoinColumn(name = "empId", referencedColumnName = "empId")
    private Employee employee;

    private String scheduleType;

    private String note;






}
