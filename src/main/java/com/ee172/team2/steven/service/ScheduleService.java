package com.ee172.team2.steven.service;

import com.ee172.team2.steven.DTO.ScheduleDTO;
import com.ee172.team2.steven.handler.BusinessException;
import com.ee172.team2.steven.model.Employee;
import com.ee172.team2.steven.model.ScheduleManager;
import com.ee172.team2.steven.model.ScheduleSetting;
import com.ee172.team2.steven.repository.EmployeeRepository;
import com.ee172.team2.steven.repository.ScheduleManagerRepository;
import com.ee172.team2.steven.repository.ScheduleSettingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.sql.Time;
import java.time.DayOfWeek;
import java.time.Duration;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAdjusters;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service
public class ScheduleService {

    @Autowired
    private ScheduleManagerRepository scheduleManagerDAO;

    @Autowired
    private EmployeeRepository employeeDAO;

    @Autowired
    private ScheduleSettingRepository scheduleSettingDAO;


    public List<Employee> getAllEmployees() {
        return employeeDAO.findAll();
    }
        public List<ScheduleManager> getScheduleByDateRange(Date startDate, Date endDate) {
            return scheduleManagerDAO.findByDayBetween(startDate, endDate);
        }

    public ScheduleManager addSchedule(Integer empId, Integer shiftTypeId, Date day) throws BusinessException {
        Employee employee = employeeDAO.findById(empId)
                .orElseThrow(() -> new BusinessException("員工不存在"));

        ScheduleSetting shiftType = scheduleSettingDAO.findById(shiftTypeId)
                .orElseThrow(() -> new BusinessException("班次類型不存在"));

        // 檢差工時驗證
        if (isOverworking(empId, day)) {
            throw new BusinessException("安排的班次会使员工超时");
        }

        // 檢查該日排班
        if (scheduleManagerDAO.existsByEmployeeAndDay(employee, day)) {
            throw new BusinessException("該員工在此日已有排班");
        }

        ScheduleManager newSchedule = new ScheduleManager();
        newSchedule.setEmployee(employee);
        newSchedule.setShiftType(shiftType);
        newSchedule.setDay(day);

        return scheduleManagerDAO.save(newSchedule);
    }


    private boolean isOverworking(Integer employeeId, Date scheduledDay) {
        //  Date 轉換為 LocalDate
        LocalDate scheduledLocalDate = convertToLocalDateViaSqlDate(scheduledDay);

        // 獲取這周的周一與周日
        LocalDate monday = scheduledLocalDate.with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY));
        LocalDate sunday = scheduledLocalDate.with(TemporalAdjusters.nextOrSame(DayOfWeek.SUNDAY));
        Employee employee = employeeDAO.findById(employeeId)
                .orElseThrow(() -> new BusinessException("員工不存在"));
        // 查詢這周的排班
        List<ScheduleManager> weeklySchedules = scheduleManagerDAO.findByEmployeeAndDayBetween(employee, java.sql.Date.valueOf(monday), java.sql.Date.valueOf(sunday));

        // 计算总工作小时数
        long totalHours = weeklySchedules.stream()
                .mapToLong(this::calculateWorkingHours)
                .sum();

        // 檢查是否超過40小時
        return totalHours > 40;
    }

    private LocalDate convertToLocalDateViaSqlDate(Date dateToConvert) {
        return new java.sql.Date(dateToConvert.getTime()).toLocalDate();
    }


    //計算工作時長
    private long calculateWorkingHours(ScheduleManager schedule) {
        ScheduleSetting setting = schedule.getShiftType();
        Time startTime = setting.getStartTime();
        Time endTime = setting.getEndTime();

        // 計算工時
        long hours = ChronoUnit.HOURS.between(startTime.toLocalTime(), endTime.toLocalTime());

        // 如果結束時間小於開始時間，表示跨天工作，需調整計算方法
        if (endTime.before(startTime)) {
            hours = 24 - hours;
        }

        return hours;
    }


    public void deleteSchedule(Integer scheduleManagerId) {
        scheduleManagerDAO.deleteById(scheduleManagerId);
    }
}

