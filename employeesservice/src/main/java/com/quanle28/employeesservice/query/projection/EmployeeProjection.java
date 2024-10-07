package com.quanle28.employeesservice.query.projection;

import java.util.ArrayList;
import java.util.List;

import org.axonframework.queryhandling.QueryHandler;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.aql.commonservice.model.EmployeeResponseCommonModel;
import com.aql.commonservice.query.GetDetailsEmployeeQuery;
import com.quanle28.employeesservice.command.data.Employee;
import com.quanle28.employeesservice.command.data.EmployeeRepository;
import com.quanle28.employeesservice.query.model.EmployeeResponseModel;
import com.quanle28.employeesservice.query.queries.GetAllEmployeesQuery;
import com.quanle28.employeesservice.query.queries.GetEmployeeQuery;

@Component
public class EmployeeProjection {
	@Autowired
	private EmployeeRepository employeeRepository;

	@QueryHandler
	public EmployeeResponseModel handle(GetEmployeeQuery getEmployeesQuery) {
		EmployeeResponseModel model = new EmployeeResponseModel();
		Employee employee = employeeRepository.findById(getEmployeesQuery.getEmployeeId()).get();
		BeanUtils.copyProperties(employee, model);

		return model;
	}

	@QueryHandler
	public List<EmployeeResponseModel> handle(GetAllEmployeesQuery getAllEmployeeQuery) {
		List<EmployeeResponseModel> listModel = new ArrayList<>();
		List<Employee> listEntity = employeeRepository.findAll();
		listEntity.stream().forEach(s -> {
			EmployeeResponseModel model = new EmployeeResponseModel();
			BeanUtils.copyProperties(s, model);
			listModel.add(model);
		});
		return listModel;
	}

	@QueryHandler
	public EmployeeResponseCommonModel handle(GetDetailsEmployeeQuery getDetailsEmployeeQuery) {
		EmployeeResponseCommonModel model = new EmployeeResponseCommonModel();
		Employee employee = employeeRepository.findById(getDetailsEmployeeQuery.getEmployeeId()).get();
		BeanUtils.copyProperties(employee, model);
		return model;
	}
}
