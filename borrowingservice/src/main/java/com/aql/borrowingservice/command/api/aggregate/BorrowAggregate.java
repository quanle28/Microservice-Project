package com.aql.borrowingservice.command.api.aggregate;

import java.util.Date;

import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;
import org.springframework.beans.BeanUtils;

import com.aql.borrowingservice.command.api.command.CreateBorrowCommand;
import com.aql.borrowingservice.command.api.command.DeleteBorrowCommand;
import com.aql.borrowingservice.command.api.command.SendMessageCommand;
import com.aql.borrowingservice.command.api.command.UpdateBookReturnCommand;
import com.aql.borrowingservice.command.api.events.BorrowCreatedEvent;
import com.aql.borrowingservice.command.api.events.BorrowDeletedEvent;
import com.aql.borrowingservice.command.api.events.BorrowSendMessageEvent;
import com.aql.borrowingservice.command.api.events.BorrowingUpdateBookReturnEvent;

@Aggregate
public class BorrowAggregate {

	@AggregateIdentifier
	private String id;
	private String bookId;
	private String employeeId;
	private Date borrowingDate;
	private Date returnDate;

	private String message;

	public BorrowAggregate() {
	}

	@CommandHandler
	public BorrowAggregate(CreateBorrowCommand createBorrowCommand) {
		BorrowCreatedEvent borrowCreatedEvent = new BorrowCreatedEvent();
		BeanUtils.copyProperties(createBorrowCommand, borrowCreatedEvent);
		AggregateLifecycle.apply(borrowCreatedEvent);
	}
	
	@EventSourcingHandler
	public void on (BorrowCreatedEvent event) {
		this.bookId = event.getBookId();
		this.borrowingDate = event.getBorrowingDate();
		this.employeeId = event.getEmployeeId();
		this.id = event.getId();
	}
//	
	@CommandHandler
	public void on (DeleteBorrowCommand borrowCommand) {
		BorrowDeletedEvent borrowDeletedEvent = new BorrowDeletedEvent();
		BeanUtils.copyProperties(borrowCommand, borrowDeletedEvent);
		AggregateLifecycle.apply(borrowDeletedEvent);
	}
	
	@EventSourcingHandler
	public void on (BorrowDeletedEvent event) {
		this.id = event.getId();
		
	}
	
	@CommandHandler
	public void handle(UpdateBookReturnCommand command) {
		BorrowingUpdateBookReturnEvent event = new BorrowingUpdateBookReturnEvent();
		BeanUtils.copyProperties(command, event);
		AggregateLifecycle.apply(event);
	}
	
	@EventSourcingHandler
	public void on(BorrowingUpdateBookReturnEvent event) {
		
		this.returnDate = event.getReturnDate();
		this.bookId = event.getBookId();
		this.employeeId = event.getEmployee();
	}
	
	@CommandHandler
	public void handle(SendMessageCommand command) {
		BorrowSendMessageEvent event = new BorrowSendMessageEvent();
		BeanUtils.copyProperties(command, event);
		AggregateLifecycle.apply(event);
	}
	@EventSourcingHandler
	public void on(BorrowSendMessageEvent event) {
		this.id = event.getId();
		this.message = event.getMessage();
		this.employeeId = event.getEmployeeId();
	}
}
