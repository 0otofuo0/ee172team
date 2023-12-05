package com.ee172.team2.nemo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ee172.team2.nemo.model.WeddingData;
import com.ee172.team2.nemo.repository.WeddingDataRepository;

import java.util.Date;
import java.util.List;

@Service
public class WeddingDataService {

    @Autowired
    private WeddingDataRepository weddingDataRepository;

    public List<WeddingData> findAll() {
        return weddingDataRepository.findAll();
    }

    public WeddingData findById(Integer id) {
        return weddingDataRepository.findById(id).orElse(null);
    }

    public WeddingData save(WeddingData weddingData) {
        return weddingDataRepository.save(weddingData);
    }

    public void delete(Integer id) {
        weddingDataRepository.deleteById(id);
    }

    // 根據日期範圍搜尋婚禮
    public List<WeddingData> findByDateRange(Date startDate, Date endDate) {
        return weddingDataRepository.findByWeddingDateBetween(startDate, endDate);
    }

    // 獲得ID後得知所有訊息
    public List<WeddingData> findByMemberId(Integer memberId) {
        return weddingDataRepository.findByWeddingMemberIdMemberId(memberId);
    }
}
