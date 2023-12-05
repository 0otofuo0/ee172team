package com.ee172.team2.steven.service;

import com.ee172.team2.steven.DTO.EmployeeDTO;
import com.ee172.team2.steven.DTO.EmployeeListDTO;
import com.ee172.team2.steven.DTO.EmployeeUpdateDTO;
import com.ee172.team2.steven.DTO.RegisterDTO;
import com.ee172.team2.steven.handler.BusinessException;
import com.ee172.team2.steven.model.*;
import com.ee172.team2.steven.repository.*;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.criteria.Predicate;
import jakarta.transaction.Transactional;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.server.ResponseStatusException;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.logging.SimpleFormatter;


@Service
public class EmployeeService {

    @Autowired
    private EmployeeRepository empDAO;

    @Autowired
    private ScheduleSettingRepository schDAO;

    @Autowired
    private EmployeeLeaveBalanceRepository employeeLeaveBalanceDAO;

    @Autowired
    private LeaveTypesRepository leaveTypesDAO;

    @Autowired
    private DepartmentRepository departmentDAO;

    @Autowired
    private PositionRepository positionDAO;




    public List<Employee> getAllEmployees() {
        List<Employee> employees = empDAO.findAll();
        for (Employee employee : employees) {
            employee.setEmpPwd(employee.getMaskedPassword());
        }
        return employees;
    }

    public List<Employee> searchEmployees(String name, String department, String position) {
        return empDAO.findByEmpNameContainingOrDepartmentContainingOrPositionContaining(name, department, position);
    }

    public Employee getEmployeeById(Integer id) {
        Employee employee = empDAO.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Employee not found"));
        employee.setEmpPwd(employee.getMaskedPassword());
        return employee;
    }

    public Employee updateEmployee(Integer id, Employee employeeDetails ) {
        Employee existingEmployee = empDAO.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Employee not found"));

        //if (employeeDetails.getEmpName() != null)
        existingEmployee.setEmpName(employeeDetails.getEmpName());
        existingEmployee.setDepartment(employeeDetails.getDepartment());
        existingEmployee.setPosition(employeeDetails.getPosition());
       existingEmployee.setGender(employeeDetails.getGender());
       existingEmployee.setPreferType(employeeDetails.getPreferType());
       existingEmployee.setBirthDate(employeeDetails.getBirthDate());
        existingEmployee.setRole(employeeDetails.getRole());
       existingEmployee.setSalary(employeeDetails.getSalary());
       existingEmployee.setEmpPhoto(employeeDetails.getEmpPhoto());
       existingEmployee.setAddress(employeeDetails.getAddress());
       existingEmployee.setPhone(employeeDetails.getPhone());



        return empDAO.save(existingEmployee);
    }

    @Transactional
    public void deleteEmployee(Integer empId) {
        empDAO.findById(empId)
                .orElseThrow(() -> new EntityNotFoundException("Employee not found with id " + empId));
        empDAO.deleteById(empId);
    }

    public Employee findById(Integer id) {
        Optional<Employee> optional = empDAO.findById(id);

        if(optional.isPresent()) {
            return optional.get();
        }

        return null;

    }


    public Page<Employee> searchEmployees(String empName, String departmentName, String pos, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);

        // 构建动态查询条件
        Specification<Employee> spec = (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (!StringUtils.isEmpty(empName)) {
                predicates.add(criteriaBuilder.like(root.get("empName"), "%" + empName + "%"));
            }
            if (!StringUtils.isEmpty(departmentName)) {
                predicates.add(criteriaBuilder.like(root.get("department").get("depName"), "%" + departmentName + "%"));
            }
            if (!StringUtils.isEmpty(pos)) {
                predicates.add(criteriaBuilder.like(root.get("pos"), "%" + pos + "%"));
            }
            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };

        return empDAO.findAll(spec, pageable);
    }

    public Employee getEmployeeDetails(Integer empId) {
        Employee employee = empDAO.findById(empId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "员工不存在。"));
        employee.setEmpPwd(null);
        return employee;
    }

    public Employee updateEmployeeDetails(Integer empId, Employee employeeDetails) {
        Employee existingEmployee = empDAO.findById(empId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "员工不存在。"));


        if (employeeDetails.getEmpName() != null) {
            existingEmployee.setEmpName(employeeDetails.getEmpName());
        }
        if (employeeDetails.getEmail() != null) {
            existingEmployee.setEmail(employeeDetails.getEmail());
        }
        if (employeeDetails.getGender() != null) {
            existingEmployee.setGender(employeeDetails.getGender());
        }
        if (employeeDetails.getBirthDate() != null) {
            existingEmployee.setBirthDate(employeeDetails.getBirthDate());
        }
        if (employeeDetails.getSalary() != null) {
            existingEmployee.setSalary(employeeDetails.getSalary());
        }
        if (employeeDetails.getAddress() != null) {
            existingEmployee.setAddress(employeeDetails.getAddress());
        }
        if (employeeDetails.getPhone() != null) {
            existingEmployee.setPhone(employeeDetails.getPhone());
        }
        if (employeeDetails.getPreferType() != null) {
            existingEmployee.setPreferType(employeeDetails.getPreferType());
        }
        if (employeeDetails.getPosition() != null) {
            existingEmployee.setPosition(employeeDetails.getPosition());
        }


        return empDAO.save(existingEmployee);
    }




@Transactional
    public Employee updateEmployee(EmployeeUpdateDTO dto) {
        Employee employee = empDAO.findById(dto.getEmpId())
                .orElseThrow(() -> new EntityNotFoundException("Employee not found with id: " + dto.getEmpId()));

        employee.setEmpName(dto.getEmpName());
        employee.setGender(dto.getGender());
        employee.setEmail(dto.getEmail());
        employee.setPhone(dto.getPhone());
        employee.setAddress(dto.getAddress());
        employee.setWorkTypes(schDAO.findById(Integer.valueOf(dto.getWorkTypeId()))
                .orElseThrow(() -> new EntityNotFoundException("WorkType not found")));
    Department department = departmentDAO.findById(Integer.valueOf(dto.getDepartmentId()))
            .orElseThrow(() -> new EntityNotFoundException("Department not found"));
    employee.setDepartment(department);


    Position position = positionDAO.findById(Integer.valueOf(dto.getPositionId()))
            .orElseThrow(() -> new EntityNotFoundException("Position not found"));
    employee.setPosition(position);
        employee.setPreferType(dto.getPreferType());
        employee.setRole(Integer.valueOf(dto.getRole()));
        employee.setSalary(Integer.valueOf(dto.getSalary()));
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate birthDay = LocalDate.parse(dto.getBirthDate(), formatter);
        employee.setBirthDate(java.sql.Date.valueOf(birthDay));
        byte[] decodedImage = Base64.getDecoder().decode(dto.getEmpPhoto());
        employee.setEmpPhoto(decodedImage);


        return empDAO.save(employee);
    }


    private Employee convertUpdateDTOToEmployee(EmployeeUpdateDTO dto) {
        Employee employee = new Employee();

        employee.setEmpName(dto.getEmpName());
        employee.setGender(dto.getGender());
        employee.setEmail(dto.getEmail());
        employee.setPhone(dto.getPhone());
        employee.setAddress(dto.getAddress());

        employee.setPreferType(dto.getPreferType());
        employee.setRole(Integer.valueOf(dto.getRole()));
        employee.setSalary(Integer.valueOf(dto.getSalary()));
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate birthDay = LocalDate.parse(dto.getBirthDate(), formatter);
        employee.setBirthDate(java.sql.Date.valueOf(birthDay));
        byte[] decodedImage = Base64.getDecoder().decode(dto.getEmpPhoto());
        employee.setEmpPhoto(decodedImage);


        return employee;
    }







    public Page<Employee> getAllEmployees(Pageable pageable) {
        return empDAO.findAll(pageable);
    }


    public EmployeeListDTO getEmployeeById2(Integer empId) {
        Employee employee = empDAO.findById(empId)
                .orElseThrow(() -> new EntityNotFoundException("Employee not found with id: " + empId));
        return convertToEmpListDTO(employee);
    }





    public Page<EmployeeListDTO> findByPage2(Integer pageNumber) {
        Pageable pageable = PageRequest.of(pageNumber - 1, 5, Sort.Direction.ASC, "empId");
        Page<Employee> employeePage = empDAO.findAll(pageable);


        Page<EmployeeListDTO> dtoPage = employeePage.map(this::convertToEmpListDTO);

        return dtoPage;
    }

    private EmployeeListDTO convertToEmpListDTO(Employee employee) {

        EmployeeListDTO dto = new EmployeeListDTO();
//        BeanUtils.copyProperties(employee,dto);
        dto.setEmpId(employee.getEmpId());
        dto.setEmpName(employee.getEmpName());
        dto.setEmail(employee.getEmail());
        dto.setPhone(employee.getPhone());
        dto.setGender(employee.getGender());
        dto.setAddress(employee.getAddress());
        dto.setBirthDate(employee.getBirthDate().toString().split(" ")[0]);
        dto.setPreferType(employee.getPreferType());
        dto.setDepartmentName(employee.getDepartment() != null ? employee.getDepartment().getDepName() : null);
        dto.setDepartmentId(String.valueOf(employee.getDepartment() != null ? employee.getDepartment().getId() : null));
        dto.setWorkType(employee.getWorkTypes().getWhType());
        dto.setWorkTypeId(String.valueOf(employee.getWorkTypes().getId()));
        dto.setPositionName(employee.getPosition() != null ? employee.getPosition().getEmpPos() : null);
        dto.setPositionId(String.valueOf(employee.getPosition() != null ? employee.getPosition().getId() : null));
        dto.setRole(String.valueOf(employee.getRole()));
        if (employee.getEmpPhoto() != null) {
            dto.setEmpPhoto(Base64.getEncoder().encodeToString(employee.getEmpPhoto()));
        } else {

            dto.setEmpPhoto(null);
        }
        dto.setSalary(String.valueOf(employee.getSalary()));



        return dto;
    }




    public Page<Employee> findByPage(Integer pageNumber){
        Pageable pgb = PageRequest.of(pageNumber-1, 7, Sort.Direction.ASC, "empId");

        return empDAO.findAll(pgb);
    }

//   －－－－－－－－－－－－－－－－－－－－－ 帳號註冊功能－－－－－－－－－－－－－－－－－－－－－－－－－－－－
    public Employee createNewEmployee(Employee employee) {
        // 檢查帳號是否重複
        if (empDAO.existsByEmpAccount(employee.getEmpAccount())) {
            throw new BusinessException("帳號已存在");
        }

        // 驗證帳號密碼
        validateCredentials(employee.getEmpAccount(), employee.getEmpPwd(),employee.getPhone(),employee.getAddress());

        if (employee.getRole() == null) {
            employee.setRole(4); // 預設角色
        }
        if (employee.getCreateTime() == null) {
            employee.setCreateTime(new Timestamp(System.currentTimeMillis())); // 當前時間
        }
        if (employee.getSalary() == null) {
            employee.setSalary(0); // 預設薪資
        }
        if (employee.getEmpPhoto() == null) {
            employee.setEmpPhoto(new byte[0]); // 空的照片數據
        }
        if (employee.getAddress() == null) {
            employee.setAddress(""); // 預設地址
        }
        if (employee.getPhone() == null) {
            employee.setPhone(""); // 預設電話
        }
        if (employee.getEmail() == null) {
            employee.setEmail(""); // 預設電子郵件
        }
        if (employee.getGender() == null) {
            employee.setGender(""); // 預設性別
        }
        if (employee.getBirthDate() == null) {
            employee.setBirthDate(new Date(0)); // 預設出生日期
        }
        if (employee.getPreferType() == null) {
            employee.setPreferType(""); // 預設偏好類型
        }
//        if (employee.getDepartment() == null) {
//            employee.setDepartment(new Department().setId(1)); // 預設部門
//        }



        Employee savedEmployee = empDAO.save(employee);

        // 初始化
        initializeEmployeeLeaveBalances(savedEmployee);

        return savedEmployee;
    }

    private void validateCredentials(String account, String password,String phone,String address) {
        if (account.length() < 6 || !account.matches(".*[a-zA-Z]+.*")) {
            throw new BusinessException("帳號至少要有六個字符,並至少要有一個英文");
        }

        if (password.length() < 6 || !password.matches(".*[a-z]+.*") || !password.matches(".*[A-Z]+.*")) {
            throw new BusinessException("密碼至少需要6个字符，並包含至少一個大寫跟一個小寫英文字母");
        }
        if (phone.length() < 10 ) {
            throw new BusinessException("不是正確的電話號碼");

        }
        if (address.length() < 10 ) {
            throw new BusinessException("不是正確的住址");

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




    //-----------------------------login----------------------------



    public Employee login(String username, String password) {

        Employee employee = empDAO.findByEmpAccount(username)
                .orElseThrow(() -> new BusinessException("User not found with username: " + username));


        if (!password.equals(employee.getEmpPwd())) {
            throw new BusinessException("Invalid password");
        }

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







