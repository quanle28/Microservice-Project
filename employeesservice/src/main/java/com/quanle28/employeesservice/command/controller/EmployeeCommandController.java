package com.quanle28.employeesservice.command.controller;

import java.util.UUID;

import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.quanle28.employeesservice.command.command.CreateEmployeeCommand;
import com.quanle28.employeesservice.command.command.DeleteEmployeeCommand;
import com.quanle28.employeesservice.command.command.UpdateEmployeeCommand;
import com.quanle28.employeesservice.command.model.EmployeeRequestModel;

@RestController
@RequestMapping("/api/v1/employees")
public class EmployeeCommandController {
	@Autowired
	private CommandGateway commandGateway;

	@Autowired
	private StreamBridge streamBridge;
	
	@PostMapping
	public String addEmployee(@RequestBody EmployeeRequestModel model) {
		CreateEmployeeCommand command = new CreateEmployeeCommand(UUID.randomUUID().toString(), model.getFirstName(),
				model.getLastName(), model.getKin(), false);

		commandGateway.sendAndWait(command);

		return "emmployee added";
	}

	@PutMapping
	public String updateEmployee(@RequestBody EmployeeRequestModel model) {
		UpdateEmployeeCommand command = new UpdateEmployeeCommand(model.getEmployeeId(), model.getFirstName(),
				model.getLastName(), model.getKin(), model.getIsDisciplined());
		commandGateway.sendAndWait(command);
		return "employee updated";
	}

	@DeleteMapping("/{employeeId}")
	public String deleteEmployee(@PathVariable String employeeId) {
		DeleteEmployeeCommand command = new DeleteEmployeeCommand(employeeId);
		commandGateway.sendAndWait(command);
		return "emmployee deleted";
	}
	
	@PostMapping("/sendMessage")
	public void SendMessage(@RequestBody String message) {
		try {
			
			ObjectMapper mapper = new ObjectMapper();
			String json = mapper.writeValueAsString(message);
			streamBridge.send("quanle-out-0", MessageBuilder.withPayload(json).build());
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
