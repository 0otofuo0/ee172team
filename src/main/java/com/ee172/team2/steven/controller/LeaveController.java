package com.ee172.team2.steven.controller;

import com.ee172.team2.steven.handler.BusinessException;
import com.ee172.team2.steven.model.Apply;
import com.ee172.team2.steven.model.EmployeeLeaveBalance;
import com.ee172.team2.steven.repository.ApplyRepository;
import com.ee172.team2.steven.repository.EmployeeLeaveBalanceRepository;
import com.ee172.team2.steven.service.LeaveApplicationService;
import com.ee172.team2.steven.service.LeaveBalanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class LeaveController {

    @Autowired
    private LeaveBalanceService leaveBalanceService;

    @Autowired
    private LeaveApplicationService leaveApplicationService;
@Autowired
    private EmployeeLeaveBalanceRepository leaveBalanceDAO;
@Autowired
private ApplyRepository applyDAO;



    @PostMapping("/applyLeave")
    public ResponseEntity<?> applyForLeave(@RequestBody Apply leaveApplication) {
        // 驗證請假時間
        if (leaveApplication.getStartTime().after(leaveApplication.getEndTime())) {
            return ResponseEntity.badRequest().body("請假結束時間必須晚於開始時間");
        }

        // 計算請假天數
        double leaveDays = leaveApplicationService.calculateLeaveDays(leaveApplication.getStartTime(), leaveApplication.getEndTime());

        // 檢查假期餘額
        EmployeeLeaveBalance balance = leaveBalanceDAO.findByEmployeeAndLeaveType(
                leaveApplication.getEmployee(), leaveApplication.getLeaveType());

        if (balance.getBalanceDays() < leaveDays) {
            return ResponseEntity.badRequest().body("假期餘額不足");
        }

        // 處理請假申請
        leaveApplicationService.processLeaveApplication(leaveApplication, leaveDays);

        return ResponseEntity.ok().body("請假申請已提交，等待審核");
    }


    @GetMapping("/applications/pending")
    public ResponseEntity<List<Apply>> getPendingApplications() {
        List<Apply> pendingApplications = applyDAO.findByCheckApply(0);
        return ResponseEntity.ok(pendingApplications);
    }


    @PostMapping("/applications/approve/{applicationId}")
    public ResponseEntity<String> approveLeaveApplication(@PathVariable int applicationId, @RequestBody boolean isApproved) {
        try {
            leaveApplicationService.processLeaveApplicationApproval(applicationId, isApproved);
            return ResponseEntity.ok("申請已處理");
        } catch (BusinessException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

}
