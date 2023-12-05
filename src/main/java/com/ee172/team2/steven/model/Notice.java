package com.ee172.team2.steven.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.util.Objects;
@NoArgsConstructor
@AllArgsConstructor
@Data
@Table(name="notice")
@Entity
public class Notice {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Integer id;


 //   private Integer empId;
//

    private String context;


    private Timestamp createTime;


    private String title;


    @JsonBackReference("employee-notice")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "empId", referencedColumnName = "empId")
    private Employee employee;

}
