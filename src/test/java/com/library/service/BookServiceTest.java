package com.library.service;

import com.library.exception.BookNotExistingException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import com.library.model.Book;
import com.library.repository.BookRepository;
import java.util.UUID;
import static org.junit.jupiter.api.Assertions.*;
import java.util.Optional;
import java.util.List;

import static org.mockito.Mockito.*;

public class BookServiceTest {

    private BookRepository mockRepository;

    @BeforeEach 
    public void setUp() {
        mockRepository = mock(BookRepository.class);
    }
    
    @Test 
    public void testAddBook() {
        BookService bookService = new BookService(mockRepository);
        Book book = new Book(UUID.randomUUID(), "Title", "9782070408504", "Author", 2020, Book.Status.AVAILABLE);
        bookService.addBook(book);
        verify(mockRepository).save(book);
    }

    @Test 
    public void testGetBookById() {
        BookService bookService = new BookService(mockRepository);
        UUID bookId = UUID.randomUUID();
        Book book = new Book(bookId, "Title", "9782070408504", "Author", 2020, Book.Status.AVAILABLE);
        when(mockRepository.findById(bookId)).thenReturn(Optional.of(book));
        assertEquals(book, bookService.getBookById(bookId));
        verify(mockRepository).findById(bookId);
    }

    @Test
    public void testGetBookByIdNotFound() {
        BookService bookService = new BookService(mockRepository);
        UUID bookId = UUID.randomUUID();
        when(mockRepository.findById(bookId)).thenReturn(Optional.empty());
        assertThrows(BookNotExistingException.class, () -> bookService.getBookById(bookId));
        verify(mockRepository).findById(bookId);
    }

    @Test 
    public void testDeleteBook() {
        BookService bookService = new BookService(mockRepository);
        UUID bookId = UUID.randomUUID();
        when(mockRepository.findById(bookId)).thenReturn(Optional.of(new Book(bookId, "Title", "9782070408504", "Author", 2020, Book.Status.AVAILABLE)));
        bookService.deleteBook(bookId);
        verify(mockRepository).deleteById(bookId);
    }

    @Test 
    public void testDeleteBorrowedBook() {
        BookService bookService = new BookService(mockRepository);
        UUID bookId = UUID.randomUUID();
        when(mockRepository.findById(bookId)).thenReturn(Optional.of(new Book(bookId, "Title", "9782070408504", "Author", 2020, Book.Status.BORROWED)));
        assertThrows(IllegalStateException.class, () -> bookService.deleteBook(bookId));
        verify(mockRepository, never()).deleteById(bookId);
    }

    @Test 
    public void testDeleteBookNotFound() {
        BookService bookService = new BookService(mockRepository);
        UUID bookId = UUID.randomUUID();
        when(mockRepository.findById(bookId)).thenReturn(Optional.empty());
        assertThrows(BookNotExistingException.class, () -> bookService.deleteBook(bookId));
        verify(mockRepository, never()).deleteById(bookId);
    }

    @Test 
    public void testUpdateBook() {
        BookService bookService = new BookService(mockRepository);
        Book book = new Book(UUID.randomUUID(), "Title", "9782070408504", "Author", 2020, Book.Status.AVAILABLE);
        bookService.updateBook(book);
        verify(mockRepository).update(book);
    }

    @Test
    public void testUpdateBookNotFound() {
        BookService bookService = new BookService(mockRepository);
        Book book = new Book(UUID.randomUUID(), "Title", "9782070408504", "Author", 2020, Book.Status.AVAILABLE);
        doThrow(new BookNotExistingException("Book not found")).when(mockRepository).update(book);
        assertThrows(BookNotExistingException.class, () -> bookService.updateBook(book));
        verify(mockRepository).update(book);
    }

    @Test 
    public void testUpdateNullBook() {
        BookService bookService = new BookService(mockRepository);
        assertThrows(IllegalArgumentException.class, () -> bookService.updateBook(null));
        verify(mockRepository, never()).update(any());
    }

    @Test 
    public void testFindBooksByISBN() {
        BookService bookService = new BookService(mockRepository);
        String isbn = "9782070408504";
        bookService.findBooksByISBN(isbn);
        verify(mockRepository).findByISBN(isbn);
    }

    @Test
    public void testGetAllBooks() {
        BookService bookService = new BookService(mockRepository);
        List<Book> books = List.of(new Book(UUID.randomUUID(), "Title", "9782070408504", "Author", 2020, Book.Status.AVAILABLE));
        when(mockRepository.findAll()).thenReturn(books);
        assertEquals(books, bookService.getAllBooks());
    }

    @Test
    public void testFindBooksByTitle() {
        BookService bookService = new BookService(mockRepository);
        List<Book> books = List.of(new Book(UUID.randomUUID(), "Title", "9782070408504", "Author", 2020, Book.Status.AVAILABLE));
        when(mockRepository.findByTitle("Title")).thenReturn(books);
        String title = "Title";;
        assertEquals(books, bookService.findBooksByTitle(title));
    }

    @Test 
    public void testFindBooksByAuthor() {
        BookService bookService = new BookService(mockRepository);
        List<Book> books = List.of(new Book(UUID.randomUUID(), "Title", "9782070408504", "Author", 2020, Book.Status.AVAILABLE));
        when(mockRepository.findByAuthor("Author")).thenReturn(books);
        String author = "Author";
        assertEquals(books, bookService.findBooksByAuthor(author));
    }
}
