package com.ee172.team2.nemo.repository;

import com.ee172.team2.nemo.model.WeddingPrice;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WeddingPriceRepository extends JpaRepository<WeddingPrice, Integer> {
    // 可添加特定的查詢方法
}
