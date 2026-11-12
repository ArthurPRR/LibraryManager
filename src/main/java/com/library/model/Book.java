package com.library.model;
import java.util.UUID;

public class Book {
    private final UUID bookId;
    private String title;
    private final String ISBN;
    private String author;
    private int year;
    private Book.Status status;

    public enum Status {
        AVAILABLE,
        BORROWED
    }

    public Book(UUID bookId, String title, String isbn, String author, int year, Book.Status status) {
        if (bookId == null || title == null || isbn == null || author == null || year <= 0 || status == null) {
            throw new IllegalArgumentException("Invalid book parameters");
        }
        if (isbn.length() != 13 || isbn.matches(".*[^0-9].*") || title.isBlank() || author.isBlank()) {
            throw new IllegalArgumentException("Invalid book parameters: ISBN must be 13 digits, title and author cannot be blank");
        }

        this.bookId = bookId;
        this.title = title;
        ISBN = isbn;
        this.author = author;
        this.year = year;
        this.status = status;
    }

    public UUID getBookId() {
        return bookId;
    }

    public String getTitle() {
        return title;
    }

    public String getISBN() {
        return ISBN;
    }

    public String getAuthor() {
        return author;
    }

    public int getYear() {
        return year;
    }

    public Book.Status getStatus() {
        return status;
    }

    public void setStatus(Book.Status status) {
        if (status == null) {
            throw new IllegalArgumentException("Status cannot be null");
        }
        this.status = status;
    }

    public void setTitle(String title) {
        if (title == null || title.isBlank()) {
            throw new IllegalArgumentException("Title cannot be null or blank");
        }
        this.title = title;
    }

    public void setAuthor(String author) {
        if (author == null || author.isBlank()) {
            throw new IllegalArgumentException("Author cannot be null or blank");
        }
        this.author = author;
    }

    public void setYear(int year) {
        if (year <= 0) {
            throw new IllegalArgumentException("Year must be a positive integer");
        }
        this.year = year;
    }

    @Override 
    public int hashCode() {
        return bookId.hashCode();
    }

    @Override 
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Book book = (Book) obj;
        return bookId.equals(book.bookId);
    }

    @Override
    public String toString() {
        return "Book{" +
                "bookId=" + bookId +
                ", title='" + title + '\'' +
                ", ISBN='" + ISBN + '\'' +
                ", author='" + author + '\'' +
                ", year=" + year +
                ", status=" + status +
                '}';
    }
}
