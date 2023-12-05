package com.ee172.team2.steven.repository;

import com.ee172.team2.steven.model.Employee;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface EmployeeRepository extends JpaRepository<Employee,Integer> , JpaSpecificationExecutor<Employee> {


//    Page<Employee> findAll(Example<Employee> employeeExample, Pageable pageable);
    boolean existsByEmpAccount(String empAccount);
    List<Employee> findByEmpNameContainingOrDepartmentContainingOrPositionContaining(String name, String department, String position);


    Optional<Employee> findByEmpAccount(String empAccount);
    Employee findByEmpAccountAndEmpPwd(String empAccount, String empPwd);

    Page<Employee> findByEmpId(Long id, Pageable pageable);

    Page<Employee> findEmployeeByEmpNameContaining(String name, Pageable pageable);

    Employee findByEmpName(String empName);
//    @Query(value="select top(1) * from employees order by added desc", nativeQuery = true)
//    public Employee findTheLatestEmployee();
//
//
//
//    @Query(value="from Employee where empName like %:empName%")
//    public List<Employee> findByEmployeeNameLike(@Param("name") String name);
}
