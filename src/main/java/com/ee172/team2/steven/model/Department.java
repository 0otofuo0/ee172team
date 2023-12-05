package com.ee172.team2.steven.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.util.Objects;
import java.util.Set;
@NoArgsConstructor
@AllArgsConstructor
@Data
@Table(name="department")
@Entity
public class Department {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Integer id;

    private String depName;

    private Timestamp createTime;



    @JsonManagedReference("employee-department")
    @OneToMany(mappedBy = "department")
    private Set<Employee> employees;


    public static Department getDefault() {
       Department defaultDepartment = new Department();
        // 设置默认值
        return defaultDepartment;
    }
}
