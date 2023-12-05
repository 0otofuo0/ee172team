package com.ee172.team2.steven.service;

import com.ee172.team2.steven.model.Clockin;
import com.ee172.team2.steven.model.Employee;
import com.ee172.team2.steven.model.Notice;
import com.ee172.team2.steven.model.ScheduleSetting;
import com.ee172.team2.steven.repository.ClockinRepository;
import com.ee172.team2.steven.repository.EmployeeRepository;
import com.ee172.team2.steven.repository.NoticeRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Service
public class ClockinService {

    @Autowired
    private ClockinRepository clockinDAO;

    @Autowired
    private EmployeeRepository empDAO;

    @Autowired
    private NoticeRepository noticeDAO;

    @Autowired
    private NoticeService noticeService;




    //上班打卡
    public Clockin clockIn(Employee employee) {


        Clockin clockInRecord = new Clockin();
        clockInRecord.setEmployee(employee);
        clockInRecord.setClockinTime(new Timestamp(System.currentTimeMillis()));
        clockInRecord.setDay(java.sql.Date.valueOf(LocalDate.now()));


        checkLate(clockInRecord, employee);

        return clockinDAO.save(clockInRecord);
    }

    // 下班打卡
    public Clockin clockOut(Employee employee) {
        Clockin clockInRecord = clockinDAO.findLatestClockinRecordByEmployee(employee)
                .orElseThrow(() -> new EntityNotFoundException("Clockin record not found for employee id " + employee.getEmpName()));

        // 記錄下班時間
        clockInRecord.setClockoutTime(new Timestamp(System.currentTimeMillis()));



        // 執行早退檢查邏輯
        checkEarlyLeave(clockInRecord, employee);

        // 保存更新
        return clockinDAO.save(clockInRecord);
    }



    //驗證遲到

    private void checkLate(Clockin clockInRecord, Employee employee) {
        ScheduleSetting scheduleSetting = employee.getWorkTypes();
        Time shiftStartTime = scheduleSetting.getStartTime();
        LocalDateTime expectedShiftStartTime = LocalDateTime.of(LocalDate.now(), shiftStartTime.toLocalTime());

        LocalDateTime clockInTime = clockInRecord.getClockinTime().toLocalDateTime();

        //判斷是否晚30分鐘
        if (clockInTime.isAfter(expectedShiftStartTime.plusMinutes(30))) {
            clockInRecord.setLate(true);
        } else {
            clockInRecord.setLate(false);
        }
        if (clockInRecord.isLate()) {
            createNoticeForEmployee(employee, "遲到警告", "您於 " + clockInRecord.getClockinTime() + " 遲到。請確保準時上班。");
        }

    }

    //驗證早退
    private void checkEarlyLeave(Clockin clockin, Employee employee) {
        ScheduleSetting scheduleSetting = employee.getWorkTypes();
        Time shiftEndTime = scheduleSetting.getEndTime();
        LocalDateTime expectedShiftEndTime = LocalDateTime.of(LocalDate.now(), shiftEndTime.toLocalTime());

        LocalDateTime clockOutTime = clockin.getClockoutTime().toLocalDateTime();

        // 計算工作時常是否小於9小時(中間一小時休息)
        long hoursWorked = Duration.between(clockin.getClockinTime().toLocalDateTime(), clockOutTime).toHours();
        if (hoursWorked < 9) {
            clockin.setEarlyLeave(true);
        } else {
            clockin.setEarlyLeave(false);
        }
        if (clockin.isEarlyLeave()) {
            createNoticeForEmployee(employee, "早退警告", "您於 " + clockin.getClockoutTime() + " 早退。請確保完整工作時數。");
        }
    }



    //通知創建
    private void createNoticeForEmployee(Employee employee, String title, String message) {
        Notice notice = new Notice();
        notice.setEmployee(employee);
        notice.setTitle(title);
        notice.setContext(message);
        notice.setCreateTime(new Timestamp(System.currentTimeMillis()));
        noticeDAO.save(notice);
    }

    }





