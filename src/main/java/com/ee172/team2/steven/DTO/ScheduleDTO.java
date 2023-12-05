package com.ee172.team2.steven.DTO;

import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;


@NoArgsConstructor
@AllArgsConstructor
@Data


public class ScheduleDTO {

    private Integer empId;

    @Temporal(TemporalType.TIMESTAMP)
    private Date date;

    private String shiftType;
}
