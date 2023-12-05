package com.ee172.team2.steven.model;


import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Time;
import java.util.Set;
@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "scheduleSetting")
public class ScheduleSetting {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String whType;

    private Time startTime;

    private Time endTime;

    @JsonManagedReference
    @OneToMany(mappedBy = "shiftType", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<ScheduleManager> scheduleManagers;

    public static ScheduleSetting getDefault() {
        ScheduleSetting defaultSetting = new ScheduleSetting();

        return defaultSetting;
    }
    @PrePersist
    @PreUpdate
    protected void prePersistAndUpdate() {
        if (this.endTime == null||this.startTime==null) {
            // 设置默认的 endTime 值
            this.endTime =Time.valueOf("00:00:00");// 默认的结束时间
            this.startTime =Time.valueOf("00:00:00");// 默认的结束时间
        }
    }
}
