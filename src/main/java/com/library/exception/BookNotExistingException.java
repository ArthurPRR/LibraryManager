package com.library.exception;

public class BookNotExistingException extends RuntimeException {
    public BookNotExistingException(String message) {
        super(message);
    }
    
}
