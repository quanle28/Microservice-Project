package com.quanle28.employeesservice.command.data;

import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeRepository extends JpaRepository<Employee, String>{

}
