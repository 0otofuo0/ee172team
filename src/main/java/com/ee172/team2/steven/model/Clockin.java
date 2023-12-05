package com.ee172.team2.steven.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.sql.Date;
import java.sql.Timestamp;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Table(name="clockin")
@Entity
public class Clockin {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Integer id;

   // private Integer empId;

    @JsonBackReference("employee-clockin")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "empId", referencedColumnName = "empId")
    private Employee employee;

    @JsonFormat(pattern = "yyyy/MM/dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy/MM/dd HH:mm:ss") // 轉換前端 String 日期到 Java 端日期格式
    @Temporal(TemporalType.TIMESTAMP)
    private Timestamp clockinTime;

    @JsonFormat(pattern = "yyyy/MM/dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy/MM/dd HH:mm:ss") // 轉換前端 String 日期到 Java 端日期格式
    @Temporal(TemporalType.TIMESTAMP)
    private Timestamp clockoutTime;

    private Date day;

    private boolean isLate;
    private boolean earlyLeave;

}
