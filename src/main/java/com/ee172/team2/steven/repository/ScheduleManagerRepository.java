package com.ee172.team2.steven.repository;

import com.ee172.team2.steven.model.Employee;
import com.ee172.team2.steven.model.ScheduleManager;
import com.ee172.team2.steven.model.ScheduleSetting;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;

public interface ScheduleManagerRepository extends JpaRepository<ScheduleManager,Integer> {


    List<ScheduleManager> findByEmployeeAndDayBetween(Employee employee, Date startDate, Date endDate);


    List<ScheduleManager> findByDayBetween(Date startDate, Date endDate);

    // 檢查特定員工在特定日期是否已有排班
    boolean existsByEmployeeAndDay(Employee employee, Date day);

//    List<ScheduleManager> findByEmpId(Employee empId);
    List<ScheduleManager> findByShiftType(ScheduleSetting shiftType);

//    @Query("SELECT sm FROM ScheduleManager sm WHERE sm.employee.empId = :empId AND sm.day = :day")
//    List<ScheduleManager> findByEmpIdAndDay(@Param("empId") Integer empId, @Param("day") Date day);
//    @Query("SELECT sm FROM ScheduleManager sm WHERE sm.employee.empId = :empId AND sm.day BETWEEN :startDate AND :endDate")
//    List<ScheduleManager> findByEmpIdAndDateRange(@Param("empId") Integer empId, @Param("startDate") Date startDate, @Param("endDate") Date endDate);
//
//
//    @Query(value = "SELECT * FROM schedule_manager WHERE emp_id = :empId " +
//            "AND DATE(day) >= :weekStart AND DATE(day) <= :weekEnd",
//            nativeQuery = true)
//    List<ScheduleManager> findByEmpIdAndWeek(@Param("empId") Integer empId,
//                                             @Param("weekStart") Date weekStart,
//                                             @Param("weekEnd") Date weekEnd);
}
