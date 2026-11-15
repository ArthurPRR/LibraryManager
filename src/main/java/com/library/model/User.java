package com.library.model;
import java.util.UUID;

public class User {
    private final UUID userId;
    private String email;
    private String firstName;
    private String lastName;

    public User(UUID userId, String firstName, String lastName, String email) {
        if (userId == null || firstName == null || lastName == null) {
            throw new IllegalArgumentException("User parameters cannot be null");
        }
        System.out.println("Creating user with email: " + email + ", firstName: " + firstName + ", lastName: " + lastName);

        if (firstName.isBlank() || lastName.isBlank()) {
            throw new IllegalArgumentException("User parameters cannot be blank");
        }

        if (!isValidEmail(email)) {
            System.out.println("Invalid email format: " + email);
            throw new IllegalArgumentException("Invalid email format");
        }
        
        email = normalizeEmail(email);

        this.userId = userId;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    private boolean isValidEmail(String email) {
        return email != null && !email.isBlank() && email.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$");
    }

    private String normalizeEmail(String email) {
        return email == null ? null : email.trim().toLowerCase();
    }

    public void setUserFirstName(String firstName) {
        if (firstName == null || firstName.isBlank()) {
            throw new IllegalArgumentException("Name cannot be null or blank");
        }
        this.firstName = firstName;
    }

    public void setUserEmail(String email) {
        if (!isValidEmail(email)) {
            throw new IllegalArgumentException("Invalid email format");
        }

        email = normalizeEmail(email);

        this.email = email;
    }

    public void setUserLastName(String lastName) {
        if (lastName == null || lastName.isBlank()) {
            throw new IllegalArgumentException("lastName cannot be null or blank");
        }
        this.lastName = lastName;
    }

    public UUID getUserId() {
        return userId;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getEmail() {
        return email;
    }

    public String getLastName() {
        return lastName;
    }

    @Override 
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        User user = (User) obj;
        return userId.equals(user.userId);
    }

    @Override 
    public int hashCode() {
        return userId.hashCode();
    }
}
