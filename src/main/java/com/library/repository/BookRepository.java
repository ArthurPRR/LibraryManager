package com.library.repository;

import com.library.model.Book;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.Optional;
import java.util.List;
import java.util.ArrayList;

import com.library.exception.BookNotExistingException;

public class BookRepository {

    private final Map<UUID, Book> books = new HashMap<>();

    public BookRepository() {
    }

    public void save(Book book) {
        if (book == null) {
            throw new IllegalArgumentException("Book cannot be null");
        }
        if (existsById(book.getBookId())) {
            throw new IllegalArgumentException("Book with this ID already exists");
        }
        books.put(book.getBookId(), book);
    }

    public Optional<Book> findById(UUID bookId) {
        return Optional.ofNullable(books.get(bookId));
    }

    public void deleteById(UUID bookId) {
        books.remove(bookId);
    }

    public List<Book> findAll() {
        return new ArrayList<>(books.values().stream().toList());
    }

    public boolean existsById(UUID bookId) {
        return books.containsKey(bookId);
    }

    public List<Book> findByISBN(String isbn) {
        if (isbn == null || isbn.isBlank()) {
            throw new IllegalArgumentException("ISBN cannot be null or empty");
        }
        List<Book> result = new ArrayList<>();
        for (Book book : books.values()) {
            if (book.getISBN().equals(isbn)) {
                result.add(book);
            }
        }
        return result;
    }
    
    public List<Book> findByTitle(String title) {
        if (title == null || title.isBlank()) {
            throw new IllegalArgumentException("Title cannot be null or empty");
        }
        List<Book> result = new ArrayList<>();
        for (Book book : books.values()) {
            if (book.getTitle().equals(title)) {
                result.add(book);
            }
        }
        return result;
    }

    public List<Book> findByAuthor(String author) {
        if (author == null || author.isBlank()) {
            throw new IllegalArgumentException("Author cannot be null or empty");
        }
        List<Book> result = new ArrayList<>();
        for (Book book : books.values()) {
            if (book.getAuthor().equals(author)) {
                result.add(book);
            }
        }
        return result;
    }
    
    public void update(Book book) {
        if (book == null) {
            throw new IllegalArgumentException("Book cannot be null");
        }
        if (!books.containsKey(book.getBookId())) {
            throw new BookNotExistingException("Book does not exist");
        }
        books.put(book.getBookId(), book);
    }


}
