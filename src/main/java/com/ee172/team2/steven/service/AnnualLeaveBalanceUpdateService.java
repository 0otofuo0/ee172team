package com.ee172.team2.steven.service;

import com.ee172.team2.steven.model.Employee;
import com.ee172.team2.steven.model.EmployeeLeaveBalance;
import com.ee172.team2.steven.model.LeaveTypes;
import com.ee172.team2.steven.repository.EmployeeLeaveBalanceRepository;
import com.ee172.team2.steven.repository.EmployeeRepository;
import com.ee172.team2.steven.repository.LeaveTypesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.util.List;
import java.util.Optional;

@Service
@EnableScheduling
public class AnnualLeaveBalanceUpdateService {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private LeaveTypesRepository leaveTypesRepository;

    @Autowired
    private EmployeeLeaveBalanceRepository employeeLeaveBalanceRepository;

    // 每年1月1日自动更新假期余额
    @Scheduled(cron = "0 0 0 1 1 *")
    public void updateAnnualLeaveBalances() {
        List<Employee> employees = employeeRepository.findAll();

        for (Employee employee : employees) {
            updateLeaveBalancesForEmployee(employee);
        }
    }

    //為員工更新班表
    private void updateLeaveBalancesForEmployee(Employee employee) {
        List<LeaveTypes> leaveTypes = leaveTypesRepository.findAll();
        int currentYear = LocalDate.now().getYear();

        for (LeaveTypes type : leaveTypes) {
            Optional<EmployeeLeaveBalance> existingBalance = employeeLeaveBalanceRepository
                    .findByEmployeeAndLeaveTypeAndYear(employee, type, currentYear - 1);

            EmployeeLeaveBalance newBalance = existingBalance.orElse(new EmployeeLeaveBalance());
            newBalance.setEmployee(employee);
            newBalance.setLeaveType(type);
            newBalance.setYear(currentYear);
            newBalance.setBalanceDays(calculateInitialLeaveDays(type, employee));

            employeeLeaveBalanceRepository.save(newBalance);
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
