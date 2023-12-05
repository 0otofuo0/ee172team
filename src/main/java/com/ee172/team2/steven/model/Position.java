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
@Table(name="position")
@Entity
public class Position {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private int id;

    @Column(name = "empPos")
    private String empPos;

    @Column(name = "createTime")
    private Timestamp createTime;


    @JsonManagedReference("employee-position")
    @OneToMany(mappedBy = "position")
    private Set<Employee> employees;

    public static Position getDefault() {
        Position defaultPosition = new Position();
        // 设置默认值
        return defaultPosition;
    }
}
