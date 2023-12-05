package com.ee172.team2.steven.repository;

import com.ee172.team2.steven.model.ScheduleSetting;
import org.springframework.data.jpa.repository.JpaRepository;


public interface ScheduleSettingRepository extends JpaRepository<ScheduleSetting,Integer> {

    ScheduleSetting findScheduleSettingByWhType(String whType);



}
