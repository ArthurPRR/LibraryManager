package com.library.repository;

import com.library.model.Book;
import com.library.exception.BookNotExistingException;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class BookRepositoryTest {
    private BookRepository repository;

    @BeforeEach 
    public void setUp() {
        repository = new BookRepository();
    }

    @Test 
    public void testSaveAndFindById() {
        Book book = new Book(UUID.randomUUID(), "Title", "9782070408504", "Author", 2020, Book.Status.AVAILABLE);
        repository.save(book);
        assertEquals(repository.findById(book.getBookId()).get(), book);
    }

    @Test 
    public void testDeleteById() {
        
        Book book = new Book(UUID.randomUUID(), "Title", "9782070408504", "Author", 2020, Book.Status.AVAILABLE);
        repository.save(book);
        repository.deleteById(book.getBookId());
        assertFalse(repository.findById(book.getBookId()).isPresent());
    }

    @Test
    public void rejectDuplicateBookId() {
        
        UUID bookId = UUID.randomUUID();
        Book book1 = new Book(bookId, "Title1", "9782070408504", "Author1", 2020, Book.Status.AVAILABLE);
        Book book2 = new Book(bookId, "Title2", "1234567890124", "Author2", 2021, Book.Status.AVAILABLE);
        repository.save(book1);
        assertThrows(IllegalArgumentException.class, () -> repository.save(book2));
    }

    @Test
    public void rejectNullBook() {
        
        assertThrows(IllegalArgumentException.class, () -> repository.save(null));
    }


    @Test 
    public void testFindByISBN() {
        
        Book book1 = new Book(UUID.randomUUID(), "Title1", "9782070408504", "Author1", 2020, Book.Status.AVAILABLE);
        Book book2 = new Book(UUID.randomUUID(), "Title2", "9782070408504", "Author2", 2021, Book.Status.AVAILABLE);
        repository.save(book1);
        repository.save(book2);
        assertEquals(2, repository.findByISBN("9782070408504").size());
    }

    @Test
    public void testFindNonExistingISBN() {
        
        assertEquals(0, repository.findByISBN("non-existing-isbn").size());
    }

    @Test 
    public void testFindNonExistingID() {
        
        assertFalse(repository.findById(UUID.randomUUID()).isPresent());
    }

    @Test
    public void testRemoveNonExistingBook() {
        
        UUID nonExistingId = UUID.randomUUID();
        repository.deleteById(nonExistingId);
        assertFalse(repository.findById(nonExistingId).isPresent());
    }

    @Test
    public void testRemoveBookAndCheckExistence() {
        
        Book book = new Book(UUID.randomUUID(), "Title", "9782070408504", "Author", 2020, Book.Status.AVAILABLE);
        repository.save(book);
        repository.deleteById(book.getBookId());
        assertFalse(repository.existsById(book.getBookId()));
    }

    @Test
    public void testFindByTitle() {
        
        Book book1 = new Book(UUID.randomUUID(), "Common Title", "9782070408504", "Author1", 2020, Book.Status.AVAILABLE);
        Book book2 = new Book(UUID.randomUUID(), "Common Title", "1234567890124", "Author2", 2021, Book.Status.AVAILABLE);
        repository.save(book1);
        repository.save(book2);
        assertEquals(2, repository.findByTitle("Common Title").size());
    }

    @Test
    public void testFindByAuthor() {
        
        Book book1 = new Book(UUID.randomUUID(), "Title1", "9782070408504", "Common Author", 2020, Book.Status.AVAILABLE);
        Book book2 = new Book(UUID.randomUUID(), "Title2", "1234567890124", "Common Author", 2021, Book.Status.AVAILABLE);
        repository.save(book1);
        repository.save(book2);
        assertEquals(2, repository.findByAuthor("Common Author").size());
    }

    @Test
    public void testFindAll() {
        
        Book book1 = new Book(UUID.randomUUID(), "Title1", "9782070408504", "Author1", 2020, Book.Status.AVAILABLE);
        Book book2 = new Book(UUID.randomUUID(), "Title2", "1234567890124", "Author2", 2021, Book.Status.AVAILABLE);
        repository.save(book1);
        repository.save(book2);
        assertEquals(2, repository.findAll().size());
    }   

    @Test 
    public void testExistsById() {
        
        Book book = new Book(UUID.randomUUID(), "Title", "9782070408504", "Author", 2020, Book.Status.AVAILABLE);
        repository.save(book);
        assertTrue(repository.existsById(book.getBookId()));
        assertFalse(repository.existsById(UUID.randomUUID()));
    }

    @Test
    public void testFindByISBNWithNullOrEmpty() {
        assertThrows(IllegalArgumentException.class, () -> repository.findByISBN(null));
        assertThrows(IllegalArgumentException.class, () -> repository.findByISBN(""));
        assertThrows(IllegalArgumentException.class, () -> repository.findByISBN("   "));
    }

    @Test 
    public void testFindByAuthorReturnsList() {
        
        Book book1 = new Book(UUID.randomUUID(), "Title1", "9782070408504", "Author1", 2020, Book.Status.AVAILABLE);
        Book book2 = new Book(UUID.randomUUID(), "Title2", "1234567890124", "Author1", 2021, Book.Status.AVAILABLE);
        repository.save(book1);
        repository.save(book2);
        assertEquals(2, repository.findByAuthor("Author1").size());
    }

    @Test
    public void testFindByTitleWithNullOrEmpty() {
        assertThrows(IllegalArgumentException.class, () -> repository.findByTitle(null));
        assertThrows(IllegalArgumentException.class, () -> repository.findByTitle(""));
        assertThrows(IllegalArgumentException.class, () -> repository.findByTitle("   "));
    }

    @Test 
    public void testFindByAuthorWithNullOrEmpty() {
        assertThrows(IllegalArgumentException.class, () -> repository.findByAuthor(null));
        assertThrows(IllegalArgumentException.class, () -> repository.findByAuthor(""));
        assertThrows(IllegalArgumentException.class, () -> repository.findByAuthor("   "));
    }

    @Test 
    public void testUpdateBook() {
        
        Book book = new Book(UUID.randomUUID(), "Title", "9782070408504", "Author", 2020, Book.Status.AVAILABLE);
        repository.save(book);
        book.setTitle("Updated Title");
        repository.update(book);
        assertEquals("Updated Title", repository.findById(book.getBookId()).get().getTitle());
    }

    @Test 
    public void testUpdateNonExistingBook() {
        
        Book book = new Book(UUID.randomUUID(), "Title", "9782070408504", "Author", 2020, Book.Status.AVAILABLE);
        assertThrows(BookNotExistingException.class, () -> repository.update(book));
    }

    @Test public void testUpdateWithNullBook() {
        assertThrows(IllegalArgumentException.class, () -> repository.update(null));
    }
}
