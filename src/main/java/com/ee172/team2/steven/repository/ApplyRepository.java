package com.ee172.team2.steven.repository;

import com.ee172.team2.steven.model.Apply;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ApplyRepository extends JpaRepository<Apply,Integer> {

    List<Apply> findByCheckApply(Integer checkApplyStatus);
}
