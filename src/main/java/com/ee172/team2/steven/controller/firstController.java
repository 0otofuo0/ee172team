package com.ee172.team2.steven.controller;

import com.ee172.team2.steven.model.Employee;
import com.ee172.team2.steven.repository.EmployeeRepository;
import com.ee172.team2.steven.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class firstController {
    @Autowired
    private EmployeeRepository empDAO;

    @Autowired
    private EmployeeService empService;




    @GetMapping("/123")
    public String test(){
        return "/steven/auth-login";

    }

    @GetMapping("/login")
    public String test2(){
        return "/steven/login";

    }
    @GetMapping("/register")
    public String test3(){
        return "/steven/register";

    }
    @GetMapping("/redirectRegister")
    public String redirectRegister(){
        return "redirect:/steven/register";

    }
    @GetMapping("/index")
    public String test4(){
        return "/steven/index";

    }

    @GetMapping("/test/employee")
    public String employee(){
        return "/steven/employee";

    }

    @GetMapping("/index2")
    public String index2(){
        return "/steven/index2";

    }
    @GetMapping("/employeeData")
    public String empData(){
        return "/steven/employeeData";

    }

    @GetMapping("/employeeData2")
    public String empData2(){
        return "/steven/employeeData2";

    }

    @GetMapping("/employeeData3")
    public String empData3(){
        return "/steven/employeeData3";

    }



    @GetMapping("/empList")
    public String showEmployees(@RequestParam(name="p", defaultValue = "1") Integer pageNumber, Model model) {
        Page<Employee> page = empService.findByPage(pageNumber);

        model.addAttribute("page", page);

        return "steven/employeeData";
    }


}
