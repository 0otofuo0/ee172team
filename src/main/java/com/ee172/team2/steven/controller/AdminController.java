package com.ee172.team2.steven.controller;

import com.ee172.team2.steven.repository.AdminRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AdminController {

    @Autowired
    private AdminRepository adminDAO;

    @GetMapping("/s")
    public String findAdminAll(){
       return adminDAO.findAll().toString();


    }

}
