package com.library.service;
import java.util.UUID;
import java.util.List;

import com.library.model.Book;
import com.library.repository.BookRepository;
import com.library.exception.BookNotExistingException;


public class BookService {
    final private BookRepository bookRepository;

    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public void addBook(Book book) {

        bookRepository.save(book);
    }

    public Book getBookById(UUID bookId) {
        return bookRepository.findById(bookId).orElseThrow(() -> new BookNotExistingException("Book not found"));
    }

    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    public void deleteBook(UUID bookId) {
        Book book = bookRepository.findById(bookId).orElseThrow(() -> new BookNotExistingException("Book not found"));
        if (book.getStatus() == Book.Status.AVAILABLE) {
            bookRepository.deleteById(bookId);
        }
        else {
            throw new IllegalStateException("Cannot delete a book that is not available");
        }
    }

    public void updateBook(Book book) {
        if (book == null) {
            throw new IllegalArgumentException("Book cannot be null");
        }
        bookRepository.update(book);
    }

    public List<Book> findBooksByISBN(String isbn) {
        return bookRepository.findByISBN(isbn);
    }

    public List<Book> findBooksByTitle(String title) {
        return bookRepository.findByTitle(title);
    }

    public List<Book> findBooksByAuthor(String author) {
        return bookRepository.findByAuthor(author);
    }
    
    
}
