package com.ee172.team2.steven.controller;


import com.ee172.team2.steven.model.Clockin;
import com.ee172.team2.steven.model.Employee;
import com.ee172.team2.steven.service.ClockinService;
import com.ee172.team2.steven.service.EmployeeService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class ClockinController {


    @Autowired
    private ClockinService clockinService;

    private EmployeeService empService;





    @PostMapping("/clockin/")
    public ResponseEntity<?> clockIn(HttpServletRequest request) {
        HttpSession session = request.getSession();
        Employee employee = (Employee) session.getAttribute("employee");
        if (employee == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("User not logged in");
        }
        Clockin clockin = clockinService.clockIn(employee);
        return ResponseEntity.ok(clockin);
    }

    @PostMapping("/clockout/{empId}")
    public ResponseEntity<?> clockOut(HttpServletRequest request) {
        HttpSession session = request.getSession();
        Employee employee = (Employee) session.getAttribute("employee");
        if (employee == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("User not logged in");
        }
        Clockin clockout = clockinService.clockOut(employee);
        return ResponseEntity.ok(clockout);
    }


}
