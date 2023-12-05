package com.ee172.team2.steven.service;

import com.ee172.team2.steven.handler.BusinessException;
import com.ee172.team2.steven.model.*;
import com.ee172.team2.steven.repository.ApplyRepository;
import com.ee172.team2.steven.repository.EmployeeLeaveBalanceRepository;
import com.ee172.team2.steven.repository.NoticeRepository;
import com.ee172.team2.steven.repository.ScheduleManagerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.Stream;

@Service
public class LeaveApplicationService {
@Autowired
    private ScheduleManagerRepository scheduleManagerRepository;
@Autowired
    private ApplyRepository applyDAO;
    @Autowired
    private EmployeeLeaveBalanceRepository employeeLeaveBalanceDAO;
    @Autowired
    private NoticeRepository noticeDAO;

    @Autowired
    NoticeService noticeService;






    public void processLeaveApplication(Apply leaveApplication, double leaveDays) {
        // 獲取員工和假期類型
        Employee employee = leaveApplication.getEmployee();
        LeaveTypes leaveType = leaveApplication.getLeaveType();

        // 查找當前的假期餘額
        EmployeeLeaveBalance balance = employeeLeaveBalanceDAO.findByEmployeeAndLeaveType(employee, leaveType);
        if (balance == null) {
            throw new BusinessException("沒有找到對應的假期餘額記錄");
        }

        // 檢查假期餘額是否足夠
        if (balance.getBalanceDays() < leaveDays) {
            // 創建並發送不足通知
            noticeService.createShortageNotice(employee, leaveType, leaveDays);
            return; // 終止進一步處理
        }

        // 更新假期餘額
        balance.setBalanceDays(balance.getBalanceDays() - leaveDays);
        employeeLeaveBalanceDAO.save(balance);

        // 保存請假申請
        leaveApplication.setCheckApply(0); // 0 表示待審核
        applyDAO.save(leaveApplication);
    }


    public void processLeaveApplicationApproval(int applicationId, boolean isApproved) {
        Apply leaveApplication = applyDAO.findById(applicationId)
                .orElseThrow(() -> new BusinessException("請假申請未找到"));

        if (isApproved) {
            // 查找當前的假期餘額
            Employee employee = leaveApplication.getEmployee();
            LeaveTypes leaveType = leaveApplication.getLeaveType();
            EmployeeLeaveBalance balance = employeeLeaveBalanceDAO.findByEmployeeAndLeaveType(employee, leaveType);

            if (balance == null) {
                throw new BusinessException("沒有找到對應的假期餘額記錄");
            }

            // 計算請假天數
            double leaveDays = calculateLeaveDays(leaveApplication.getStartTime(), leaveApplication.getEndTime());

            // 更新假期餘額
            if (balance.getBalanceDays() >= leaveDays) {
                balance.setBalanceDays(balance.getBalanceDays() - leaveDays);
                employeeLeaveBalanceDAO.save(balance);



            } else {
                noticeService.createApplicationRejectedNotice(employee,leaveApplication);

            }
        }

        // 更新申請狀態
        leaveApplication.setCheckApply(isApproved ? 1 : 2);//1代表通過 2代表未過
        applyDAO.save(leaveApplication);
    }






    public double calculateLeaveDays(Timestamp startTime, Timestamp endTime) {
        // 轉換 Timestamp 為 LocalDate
        LocalDate start = startTime.toLocalDateTime().toLocalDate();
        LocalDate end = endTime.toLocalDateTime().toLocalDate();

        long days = ChronoUnit.DAYS.between(start, end);

        // 假設每週工作 5 天，週末不計入請假天數
        long businessDays = Stream.iterate(start, date -> date.plusDays(1))
                .limit(days)
                .filter(date -> !(date.getDayOfWeek() == DayOfWeek.SATURDAY ||
                        date.getDayOfWeek() == DayOfWeek.SUNDAY))
                .count();

        return (double) businessDays;
    }





    private void createNoticeForEmployee(Employee employee, String title, String message) {
        Notice notice = new Notice();
        notice.setEmployee(employee);
        notice.setTitle(title);
        notice.setContext(message);
        notice.setCreateTime(new Timestamp(System.currentTimeMillis()));
        noticeDAO.save(notice);
    }
}