package com.quanle28.bookservice.command.data;

import jakarta.persistence.Column; 
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "books")
public class Book {
	@Id
	private String bookId;
	
	@Column(name ="name")
	private String name;
	
	@Column(name ="author")
	private String author;
	
	@Column(name ="isReady")
	private Boolean isReady;

	public String getBookId() {
		return bookId;
	}

	public void setBookId(String bookId) {
		this.bookId = bookId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public Boolean getIsReady() {
		return isReady;
	}

	public void setIsReady(Boolean isReady) {
		this.isReady = isReady;
	}
	
	
}
