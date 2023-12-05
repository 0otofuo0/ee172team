package com.ee172.team2.nemo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.ee172.team2.nemo.model.WeddingPrice;
import com.ee172.team2.nemo.service.WeddingPriceService;

import java.util.List;

@RestController
@RequestMapping("/weddingPrice")
public class WeddingPriceController {

    @Autowired
    private WeddingPriceService weddingPriceService;

    @GetMapping
    public List<WeddingPrice> getAllPrices() {
        return weddingPriceService.findAll();
    }

    @GetMapping("/{id}")
    public WeddingPrice getPriceById(@PathVariable Integer id) {
        return weddingPriceService.findById(id).orElse(null);
    }

    @PostMapping("/tt")
    public WeddingPrice createPrice(@RequestBody WeddingPrice price) {
        return weddingPriceService.save(price);
    }

    
}
