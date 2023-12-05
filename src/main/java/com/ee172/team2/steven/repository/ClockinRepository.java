package com.ee172.team2.steven.repository;

import com.ee172.team2.steven.model.Clockin;
import com.ee172.team2.steven.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface ClockinRepository extends JpaRepository<Clockin,Integer>{

    @Query(value = "SELECT * FROM clockin WHERE empId = ?1 ORDER BY clockinTime DESC LIMIT 1", nativeQuery = true)
    Optional<Clockin> findLatestClockinRecordByEmployee(Integer empId);

    @Query("SELECT c FROM Clockin c WHERE c.employee = :employee ORDER BY c.clockinTime DESC")
    Optional<Clockin> findLatestClockinRecordByEmployee(@Param("employee") Employee employee);

}
