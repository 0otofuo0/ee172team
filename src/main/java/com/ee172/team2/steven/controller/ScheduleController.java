package com.ee172.team2.steven.controller;

import com.ee172.team2.steven.handler.BusinessException;
import com.ee172.team2.steven.model.Employee;
import com.ee172.team2.steven.model.ScheduleManager;
import com.ee172.team2.steven.service.ScheduleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api/employee/shift")
public class ScheduleController {

    @Autowired
    private ScheduleService scheduleService;

    @GetMapping("/employees")
    public ResponseEntity<List<Employee>> getAllEmployees() {
        return ResponseEntity.ok(scheduleService.getAllEmployees());
    }

    @GetMapping
    public ResponseEntity<List<ScheduleManager>> getScheduleByDateRange(
            @RequestParam("start") @DateTimeFormat(pattern="yyyy-MM-dd") Date startDate,
            @RequestParam("end") @DateTimeFormat(pattern="yyyy-MM-dd") Date endDate) {
        return ResponseEntity.ok(scheduleService.getScheduleByDateRange(startDate, endDate));
    }

    @PostMapping("/addSchedule")
    public ResponseEntity<ScheduleManager> addSchedule(
            @RequestParam Integer empId,
            @RequestParam Integer shiftTypeId,
            @RequestParam @DateTimeFormat(pattern="yyyy-MM-dd") Date day) {
        try {
            ScheduleManager scheduleManager = scheduleService.addSchedule(empId, shiftTypeId, day);
            return new ResponseEntity<>(scheduleManager, HttpStatus.CREATED);
        } catch (BusinessException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSchedule(@PathVariable Integer id) {
        scheduleService.deleteSchedule(id);
        return ResponseEntity.ok().build();
    }

}
