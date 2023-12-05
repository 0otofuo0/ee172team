package com.ee172.team2.steven.DTO;

import lombok.Data;

@Data
public class EmployeeDTO {

	private Integer empId;

	private String empName;

	private String position;

	private String department;

	private Integer role; // 權限等級

}
