package com.quanle28.bookservice.command.aggregate;

import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;
import org.springframework.beans.BeanUtils;

import com.aql.commonservice.command.RollBackStatusBookCommand;
import com.aql.commonservice.command.UpdateStatusBookCommand;
import com.aql.commonservice.event.BookRollBackStatusEvent;
import com.aql.commonservice.event.BookUpdateStatusEvent;
import com.quanle28.bookservice.command.command.CreateBookCommand;
import com.quanle28.bookservice.command.command.DeleteBookCommand;
import com.quanle28.bookservice.command.command.UpdateBookCommand;
import com.quanle28.bookservice.command.event.BookCreatedEvent;
import com.quanle28.bookservice.command.event.BookDeletedEvent;
import com.quanle28.bookservice.command.event.BookUpdatedEvent;

@Aggregate
public class BookAggregate {
	// xu ly tu command

	@AggregateIdentifier
	private String bookId;
	private String name;
	private String author;
	private Boolean isReady;

	public BookAggregate() {
	}

	@CommandHandler
	public BookAggregate(CreateBookCommand createBookCommand) {
		// nhan command da gui tu controller
		BookCreatedEvent bookCreatedEvent = new BookCreatedEvent();

		// copy tu createBookCommand set qua bookCreatedEvent
		BeanUtils.copyProperties(createBookCommand, bookCreatedEvent);
		AggregateLifecycle.apply(bookCreatedEvent);
	}

	@CommandHandler
	public void handle(UpdateBookCommand updateBookCommand) {
		// nhan command da gui tu controller
		BookUpdatedEvent bookUpdatedEvent = new BookUpdatedEvent();

		// copy tu createBookCommand set qua bookCreatedEvent
		BeanUtils.copyProperties(updateBookCommand, bookUpdatedEvent);
		AggregateLifecycle.apply(bookUpdatedEvent);
	}

	@CommandHandler
	public void handle(DeleteBookCommand deleteBookCommand) {
		// nhan command da gui tu controller
		BookDeletedEvent bookDeletedEvent = new BookDeletedEvent();
		BeanUtils.copyProperties(deleteBookCommand, bookDeletedEvent);
		AggregateLifecycle.apply(bookDeletedEvent);
	}

	// lay tu BookCreatedEvent set cho BookAggregate
	@EventSourcingHandler
	public void on(BookCreatedEvent event) {
		this.bookId = event.getBookId();
		this.author = event.getAuthor();
		this.isReady = event.getIsReady();
		this.name = event.getName();
	}

	@EventSourcingHandler
	public void on(BookUpdatedEvent event) {
		this.bookId = event.getBookId();
		this.author = event.getAuthor();
		this.isReady = event.getIsReady();
		this.name = event.getName();
	}

	// lay tu BookDeletedEvent set cho BookAggregate
	@EventSourcingHandler
	public void on(BookDeletedEvent event) {
		this.bookId = event.getBookId();
	}

	@CommandHandler
	public void handle(UpdateStatusBookCommand command) {
		BookUpdateStatusEvent event = new BookUpdateStatusEvent();
		BeanUtils.copyProperties(command, event);
		AggregateLifecycle.apply(event);
	}

	@EventSourcingHandler
	public void on(BookUpdateStatusEvent event) {
		this.bookId = event.getBookId();
		this.isReady = event.getIsReady();
	}

	@CommandHandler
	public void handle(RollBackStatusBookCommand command) {
		BookRollBackStatusEvent event = new BookRollBackStatusEvent();
		BeanUtils.copyProperties(command, event);
		AggregateLifecycle.apply(event);
	}

	@EventSourcingHandler
	public void on(BookRollBackStatusEvent event) {
		this.bookId = event.getBookId();
		this.isReady = event.getIsReady();
	}

}
