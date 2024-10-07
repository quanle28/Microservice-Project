package com.quanle28.employeesservice.command.event;

public class EmployeeDeletedEvent {
	private String employeeId;

	public String getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(String employeeId) {
		this.employeeId = employeeId;
	}
}
