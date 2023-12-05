package com.ee172.team2.nemo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ee172.team2.nemo.model.WeddingPrice;
import com.ee172.team2.nemo.repository.WeddingPriceRepository;

import java.util.List;
import java.util.Optional;

@Service
public class WeddingPriceService {

    @Autowired
    private WeddingPriceRepository weddingPriceRepository;

    public List<WeddingPrice> findAll() {
        return weddingPriceRepository.findAll();
    }

    public Optional<WeddingPrice> findById(Integer id) {
        return weddingPriceRepository.findById(id);
    }

    public WeddingPrice save(WeddingPrice price) {
        return weddingPriceRepository.save(price);
    }

    // 其他必要的方法
}
