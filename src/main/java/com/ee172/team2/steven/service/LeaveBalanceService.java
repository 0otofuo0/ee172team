package com.ee172.team2.steven.service;


import com.ee172.team2.steven.model.Employee;
import com.ee172.team2.steven.model.EmployeeLeaveBalance;
import com.ee172.team2.steven.model.LeaveTypes;
import com.ee172.team2.steven.repository.EmployeeLeaveBalanceRepository;
import com.ee172.team2.steven.repository.EmployeeRepository;
import com.ee172.team2.steven.repository.LeaveTypesRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.util.List;

@Service
public class LeaveBalanceService {

    @Autowired
    private EmployeeLeaveBalanceRepository employeeLeaveBalanceDAO;

    @Autowired
    private LeaveTypesRepository leaveTypesDAO;

    @Autowired
    private EmployeeRepository empDAO;

    public void initializeEmployeeLeaveBalances(Integer empId) {
        Employee employee = empDAO.findById(empId)
                .orElseThrow(() -> new EntityNotFoundException("Employee not found with id " + empId));

        List<LeaveTypes> leaveTypes = leaveTypesDAO.findAll();

        for (LeaveTypes type : leaveTypes) {
            EmployeeLeaveBalance balance = new EmployeeLeaveBalance();
            balance.setEmployee(employee);
            balance.setLeaveType(type);
            balance.setYear(LocalDate.now().getYear());
            balance.setBalanceDays(calculateInitialLeaveDays(type, employee));

            employeeLeaveBalanceDAO.save(balance);
        }
    }

    public void initializeEmployeeLeaveBalances(Employee employee) {
        // 假設 leaveTypesRepository 可以獲取所有假期類型
        List<LeaveTypes> leaveTypes = leaveTypesDAO.findAll();

        // 為每個假期類型創建一個假期餘額記錄
        for (LeaveTypes type : leaveTypes) {
            EmployeeLeaveBalance balance = new EmployeeLeaveBalance();
            balance.setEmployee(employee);
            balance.setLeaveType(type);
            balance.setYear(LocalDate.now().getYear());
            balance.setBalanceDays(calculateInitialLeaveDays(type, employee));

            employeeLeaveBalanceDAO.save(balance);
        }
    }



    private double calculateInitialLeaveDays(LeaveTypes type, Employee employee) {
        if (type.getLeaveType().equals("事假") || type.getLeaveType().equals("病假")) {
            return type.getYearlyLimit();
        }

        if (type.getLeaveType().equals("特別休假")) {
            // 將 java.util.Date 轉換為 LocalDate
            LocalDate startWorkDate = employee.getCreateTime().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            LocalDate currentDate = LocalDate.now();

            // 計算工作年限
            int yearsOfWork = Period.between(startWorkDate, currentDate).getYears();

            if (yearsOfWork <= 3) {
                return 6;
            } else {
                return 6 + (yearsOfWork - 3) * 2;
            }
        }

        return 0;
    }






}

