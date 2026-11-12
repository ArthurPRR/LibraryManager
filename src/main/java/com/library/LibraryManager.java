package com.library;

import com.library.service.BookService;
import com.library.repository.BookRepository;

public class LibraryManager {
    public void start() {
        System.out.println("Library Manager started.");
        BookService bookService = new BookService(new BookRepository());
        System.out.println("BookService instance created: " + bookService);
    }
}
