package com.aql.borrowingservice.command.api.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.stereotype.Service;
import org.springframework.messaging.support.MessageBuilder;

import com.aql.borrowingservice.command.api.data.BorrowRepository;
import com.aql.borrowingservice.command.api.model.Message;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class BorrowService {
	@Autowired
	private BorrowRepository repository;

	@Autowired
	private StreamBridge streamBridge;

	public void sendMessage(Message message) {
		try {

			ObjectMapper mapper = new ObjectMapper();
			String json = mapper.writeValueAsString(message);
			streamBridge.send("aql-out-0", MessageBuilder.withPayload(json).build());
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public String findIdBorrowing(String employeeId, String bookId) {
		return repository.findByEmployeeIdAndBookIdAndReturnDateIsNull(employeeId, bookId).getId();
	}
}
