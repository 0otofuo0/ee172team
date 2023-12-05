package com.ee172.team2.steven.DTO;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

@Data
public class RegisterDTO {

    private  String userAccount;

    private String username;

    private String email;

    private String password;

    @JsonFormat(pattern = "yyyy/MM/dd ")
    @DateTimeFormat(pattern = "yyyy/MM/dd") // 轉換前端 String 日期到 Java 端日期格式
    private String birthdate;

    private String address;

    private String gender;

    private String phone;

    private String preferType;

    private String empPhoto;
}
