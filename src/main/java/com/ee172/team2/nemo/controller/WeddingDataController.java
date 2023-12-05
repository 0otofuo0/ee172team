package com.ee172.team2.nemo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.ee172.team2.nemo.model.WeddingData;
import com.ee172.team2.nemo.service.WeddingDataService;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/weddingData")
public class WeddingDataController {

    @Autowired
    private WeddingDataService weddingDataService;

    @GetMapping
    public List<WeddingData> getAllWeddingData() {
        return weddingDataService.findAll();
    }

    @GetMapping("/get{id}")
    public WeddingData getWeddingDataById(@PathVariable Integer id) {
        return weddingDataService.findById(id);
    }

    @PostMapping
    public WeddingData createWeddingData(@RequestBody WeddingData weddingData) {
        return weddingDataService.save(weddingData);
    }

    @PutMapping("/{updateid}")
    public WeddingData updateWeddingData(@PathVariable Integer id, @RequestBody WeddingData weddingData) {
        weddingData.setWeddingId(id);
        return weddingDataService.save(weddingData);
    }

    @DeleteMapping("/delete/{id}")
    public void deleteWeddingData(@PathVariable("id") Integer id) {
        weddingDataService.delete(id);
    }

    // 根據日期範圍搜尋婚禮
    @GetMapping("/searchByDate")
    public List<WeddingData> searchByDateRange(@RequestParam Date startDate, @RequestParam Date endDate) {
        return weddingDataService.findByDateRange(startDate, endDate);
    }

    // 獲得ID後得知所有訊息
    @GetMapping("/searchByMemberId")
    public List<WeddingData> searchByMemberId(@RequestParam Integer memberId) {
        return weddingDataService.findByMemberId(memberId);
    }
}
