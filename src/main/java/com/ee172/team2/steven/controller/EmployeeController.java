package com.ee172.team2.steven.controller;

import com.ee172.team2.steven.DTO.*;
import com.ee172.team2.steven.handler.BusinessException;
import com.ee172.team2.steven.model.Employee;
import com.ee172.team2.steven.repository.EmployeeRepository;
import com.ee172.team2.steven.service.EmployeeService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Base64;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@RequestMapping("/api/employee")
@RestController
public class EmployeeController {

    @Autowired
    private EmployeeRepository empDAO;

    @Autowired
    private EmployeeService empService;








    @GetMapping("/all")
    public ResponseEntity<List<Employee>> getAllEmployees() {
        return ResponseEntity.ok(empService.getAllEmployees());
    }

    @GetMapping("/search")
    public ResponseEntity<List<Employee>> searchEmployees(
            @RequestParam String name,
            @RequestParam String department,
            @RequestParam String position) {
        List<Employee> employees = empService.searchEmployees(name, department, position);
        return ResponseEntity.ok(employees);
    }

//    員工詳細資料
    @GetMapping("/{empId}")
    public ResponseEntity<EmployeeListDTO> getEmployeeById(@PathVariable Integer empId) {
        try {
            EmployeeListDTO employeeListDTO = empService.getEmployeeById2(empId);
            return ResponseEntity.ok(employeeListDTO);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }


    @Transactional
    @PutMapping("/123/{id}")
    public ResponseEntity<Employee> updateEmployee(
            @PathVariable Integer id,
            @RequestBody Employee employeeDetails) {
        return ResponseEntity.ok(empService.updateEmployee(id, employeeDetails));
    }


    @GetMapping("/login/{id}")
    public ResponseEntity<Employee> findById(@PathVariable Integer id) {
        Optional<Employee> optional = empDAO.findById(id);
        if (optional.isPresent()) {
            return ResponseEntity.ok(optional.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteEmployee(@PathVariable Integer id) {
        empService.deleteEmployee(id);
        return ResponseEntity.ok().build();
    }


//    @PostMapping("/register")
//    public ResponseEntity<Employee> registerEmployee(@RequestBody Employee employee) {
//        Employee savedEmployee = empService.createNewEmployee(employee);
//        return new ResponseEntity<>(savedEmployee, HttpStatus.CREATED);
//    }

    @PostMapping("/register")
    public ResponseEntity<?> registerEmployee(@RequestBody RegisterDTO registrationDTO) {
        try {
            Employee employee = convertDTOToEmployee(registrationDTO);
            Employee savedEmployee = empService.createNewEmployee(employee);
            return new ResponseEntity<>(savedEmployee, HttpStatus.CREATED);
        } catch (BusinessException ex) {

            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(new BusinessException(ex.getMessage()));}
    }



    @GetMapping("/page")
    public Page<Employee> showEmployee(@RequestParam(name = "p", defaultValue = "1") Integer pageNumber) {
        Page<Employee> page = empService.findByPage(pageNumber);


        return page;
    }

//    員工資料
    @GetMapping("/empList2")
    public Page<EmployeeListDTO> getEmployees(@RequestParam(name = "p", defaultValue = "1") Integer pageNumber) {
        return empService.findByPage2(pageNumber);
    }




    @GetMapping("/page/all")
    public ResponseEntity<Page<Employee>> getAllEmployees(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Employee> employees = empService.getAllEmployees(pageable);
        return ResponseEntity.ok(employees);
    }

    @PutMapping("/page/{empId}")
    public ResponseEntity<Employee> updateEmployeeDetails(@PathVariable Integer empId, @RequestBody Employee employeeDetails) {
        Employee updatedEmployee = empService.updateEmployeeDetails(empId, employeeDetails);
        return ResponseEntity.ok(updatedEmployee);
    }

    @GetMapping("/page/{empId}")
    public ResponseEntity<Employee> getEmployeeDetails(@PathVariable Integer empId) {
        Employee employee = empService.getEmployeeDetails(empId);
        return ResponseEntity.ok(employee);
    }

    @GetMapping("/page/search")
    public ResponseEntity<Page<Employee>> searchEmployees(
            @RequestParam(required = false, defaultValue = "") String empName,
            @RequestParam(required = false, defaultValue = "") String departmentName,
            @RequestParam(required = false, defaultValue = "") String pos,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Page<Employee> employees = empService.searchEmployees(empName, departmentName, pos, page, size);
        return ResponseEntity.ok(employees);
    }





    @PostMapping("/login")
    public String login(HttpServletRequest request, @RequestBody LoginDTO loginDTO, Model model) {
        Employee employee = empService.login(loginDTO.getUsername(), loginDTO.getPassword());
        if (employee != null) {
            EmployeeDTO employeeDTO = convertToEmployeeDTO(employee);
            HttpSession session = request.getSession();
            session.setAttribute("employeeDTO", employeeDTO);
            return "redirect:/index";
        } else {
            model.addAttribute("loginError", "Invalid username or password");
            return "login";
        }
    }


    @PutMapping("/{empId}")
    public ResponseEntity<?> updateEmployee(@PathVariable Integer empId, @RequestBody EmployeeUpdateDTO employeeUpdateDTO) {
        if (!empId.equals(employeeUpdateDTO.getEmpId())) {
            return ResponseEntity.badRequest().body("Path variable empId doesn't match with DTO empId");
        }

        try {
            Employee updatedEmployee = empService.updateEmployee(employeeUpdateDTO);
            return ResponseEntity.ok(updatedEmployee);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }



    private Employee convertDTOToEmployee(RegisterDTO dto) {
        Employee employee = new Employee();
        // 將 DTO 字段設置到 employee 實體
        employee.setEmpAccount(dto.getUserAccount());
        employee.setEmpName(dto.getUsername());
        employee.setEmpPwd(dto.getPassword());
        employee.setEmail(dto.getEmail());
        employee.setPhone(dto.getPhone());
        employee.setGender(dto.getGender());
        employee.setAddress(dto.getAddress());
        employee.setPreferType(dto.getPreferType());
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate birthDay = LocalDate.parse(dto.getBirthdate(), formatter);
        employee.setBirthDate(java.sql.Date.valueOf(birthDay));

        byte[] decodedImage = Base64.getDecoder().decode(dto.getEmpPhoto());
        employee.setEmpPhoto(decodedImage);





        return employee;
    }






    private EmployeeDTO convertToEmployeeDTO(Employee employee) {
        EmployeeDTO employeeDTO = new EmployeeDTO();
        employeeDTO.setEmpId(employee.getEmpId());
        employeeDTO.setEmpName(employee.getEmpName());
        employeeDTO.setPosition(employee.getPosition() != null ? employee.getPosition().getEmpPos() : null);
        employeeDTO.setDepartment(employee.getDepartment() != null ? employee.getDepartment().getDepName() : null);
        employeeDTO.setRole(employee.getRole());
        return employeeDTO;
    }


}

