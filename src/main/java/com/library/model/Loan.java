package com.library.model;
import java.util.UUID;
import java.util.Date;

public class Loan {
    UUID loanId;
    Book book;
    User user;
    Date loanDate;
    Date returnDate;
    Date dueDate;

    public Loan(UUID loanId, Book book, User user, Date loanDate, Date returnDate, Date dueDate) {
        this.loanId = loanId;
        this.book = book;
        this.user = user;
        this.loanDate = loanDate;
        this.returnDate = returnDate;
        this.dueDate = dueDate;
    }

    public UUID getLoanId() {
        return loanId;
    }

    public Book getBook() {
        return book;
    }

    public User getUser() {
        return user;
    }

    public Date getLoanDate() {
        return loanDate;
    }
    
}
