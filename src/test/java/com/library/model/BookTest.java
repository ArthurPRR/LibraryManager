package com.library.model;

import java.util.UUID;
import java.util.Set;
import java.util.HashSet;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


public class BookTest {
    @Test
    void shouldRejectBlankTitle() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Book(UUID.randomUUID(), "", "1234567890123", "Author", 2020, Book.Status.AVAILABLE);
        });
    }

    @Test
    void shouldRejectBlankAuthor() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Book(UUID.randomUUID(), "Title", "1234567890123", "", 2020, Book.Status.AVAILABLE);
        });
    }

    @Test
    void shouldRejectInvalidYear() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Book(UUID.randomUUID(), "Title", "1234567890123", "Author", -3, Book.Status.AVAILABLE);
        });
    }

    @Test
    void shouldRejectInvalidISBN() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Book(UUID.randomUUID(), "Title", "123456789012", "Author", 2020, Book.Status.AVAILABLE);
        });
    }

    @Test
    void shouldRejectNullStatus() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Book(UUID.randomUUID(), "Title", "1234567890123", "Author", 2020, null);
        });
    }

    @Test
    void shouldRejectNullTitle() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Book(UUID.randomUUID(), null, "1234567890123", "Author", 2020, Book.Status.AVAILABLE);
        });
    }

        @Test
        void gettersShouldReturnCorrectValues() {
            Book book = new Book(UUID.randomUUID(), "Title", "1234567890123", "Author", 2020, Book.Status.AVAILABLE);
            assert book.getTitle().equals("Title");
            assert book.getISBN().equals("1234567890123");
            assert book.getAuthor().equals("Author");
            assert book.getYear() == 2020;
            assert book.getStatus() == Book.Status.AVAILABLE;
        }

        @Test
        void settersShouldUpdateValues() {
            Book book = new Book(UUID.randomUUID(), "Title", "1234567890123", "Author", 2020, Book.Status.AVAILABLE);
            book.setTitle("New Title");
            book.setAuthor("New Author");
            book.setYear(2021);
            book.setStatus(Book.Status.BORROWED);

            assert book.getTitle().equals("New Title");
            assert book.getAuthor().equals("New Author");
            assert book.getYear() == 2021;
            assert book.getStatus() == Book.Status.BORROWED;
        }

        @Test
        void sameBookIdShouldBeEqual() {
            UUID bookId = UUID.randomUUID();
            Book book1 = new Book(bookId, "Title1", "1234567890123", "Author1", 2020, Book.Status.AVAILABLE);
            Book book2 = new Book(bookId, "Title2", "1234567890123", "Author2", 2021, Book.Status.BORROWED);

            assertEquals(book1, book2);
            Set<Book> books = new HashSet<>();
            books.add(book1);
            books.add(book2);
            assertEquals(books.size(), 1);
        }

    }
