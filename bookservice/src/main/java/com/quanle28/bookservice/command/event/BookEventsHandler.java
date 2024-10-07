package com.quanle28.bookservice.command.event;

import org.axonframework.eventhandling.EventHandler;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.aql.commonservice.event.BookRollBackStatusEvent;
import com.aql.commonservice.event.BookUpdateStatusEvent;
import com.quanle28.bookservice.command.data.Book;
import com.quanle28.bookservice.command.data.BookRepository;

@Component
public class BookEventsHandler {
	@Autowired
	private BookRepository bookRepository;

	@EventHandler
	public void on(BookCreatedEvent event) {
		Book book = new Book();
		BeanUtils.copyProperties(event, book);
		bookRepository.save(book);
	}

	@EventHandler
	public void on(BookUpdatedEvent event) {
		Book book = bookRepository.findById(event.getBookId()).get();
		book.setAuthor(event.getAuthor());
		book.setName(event.getName());
		book.setIsReady(event.getIsReady());
		bookRepository.save(book);
	}

	@EventHandler
	public void on(BookDeletedEvent event) {
		bookRepository.deleteById(event.getBookId());
	}

	@EventHandler
	public void on(BookUpdateStatusEvent event) {
		Book book = bookRepository.findById(event.getBookId()).get();
		book.setIsReady(event.getIsReady());
		bookRepository.save(book);
	}

	@EventHandler
	public void on(BookRollBackStatusEvent event) {
		Book book = bookRepository.findById(event.getBookId()).get();
		book.setIsReady(event.getIsReady());
		bookRepository.save(book);
	}
}
