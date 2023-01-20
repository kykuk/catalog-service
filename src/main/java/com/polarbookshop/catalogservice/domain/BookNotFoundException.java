package com.polarbookshop.catalogservice.domain;

@SuppressWarnings("serial")
public class BookNotFoundException extends RuntimeException {

    public BookNotFoundException(String isbn) {
        super("The book with ISBN " + isbn + " was not found.");
    }

}
