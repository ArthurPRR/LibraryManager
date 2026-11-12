package com.library.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.junit.jupiter.params.provider.NullSource;

import static org.junit.jupiter.api.Assertions.*;

import java.util.UUID;


public class UserTest {
    
    private User user;

    @BeforeEach
    public void setUp() {
        this.user = new User(UUID.randomUUID(), "John", "john@example.com", "Doe");
    }
    
    @Test
    public void testUserCreation() {
        User user = new User(UUID.randomUUID(), "John", "john@example.com", "Doe");
        assertEquals("John", user.getFirstName());
        assertEquals("john@example.com", user.getEmail());
        assertEquals("Doe", user.getLastName());
    }

    @Test
    public void testUserCreationWithNullValues() {
        assertThrows(IllegalArgumentException.class, () -> {
            new User(null, "John", "john@example.com", "Doe");
        });
    }

    @ParameterizedTest
    @NullSource
    @ValueSource(strings = {"invalid-email", "another-invalid-email",  ""})
    public void testInvalidEmail(String invalidEmail) {
        assertThrows(IllegalArgumentException.class, () -> {
            new User(UUID.randomUUID(), "John", invalidEmail, "Doe");
        });
    }

    @Test
    public void testSetUserFirstName() {
        user.setUserFirstName("Jane");
        assertEquals("Jane", user.getFirstName());
    }

    @Test 
    public void testSetUserEmail() {
        user.setUserEmail("jane@example.com");
        assertEquals("jane@example.com", user.getEmail());
    }

    @Test 
    public void testSetUserLastName() {
        user.setUserLastName("Smith");
        assertEquals("Smith", user.getLastName());
    }

    @Test 
    public void testSetUserFirstNameWithNull() {
        assertThrows(IllegalArgumentException.class, () -> {
            user.setUserFirstName(null);
        });
    }

    @Test 
    public void testSetUserEmailWithNull() {
        assertThrows(IllegalArgumentException.class, () -> {
            user.setUserEmail(null);
        });
    }

    @Test
    public void testSetUserLastNameWithNull() {
        assertThrows(IllegalArgumentException.class, () -> {
            user.setUserLastName(null);
        });
    }

    @Test
    public void testSameUserIdEquals() {
        UUID userId = UUID.randomUUID();
        User user1 = new User(userId, "John", "john@example.com", "Doe");
        User user2 = new User(userId, "Jane", "jane@example.com", "Smith");
        assertEquals(user1, user2);
    }

    @Test
    public void testDifferentUserIdNotEquals() {
        User user1 = new User(UUID.randomUUID(), "John", "john@example.com", "Doe");
        User user2 = new User(UUID.randomUUID(), "Jane", "jane@example.com", "Smith");
        assertNotEquals(user1, user2);
    }

    @Test
    public void testHashCodeConsistency() {
        UUID userId = UUID.randomUUID();
        User user1 = new User(userId, "John", "john@example.com", "Doe");
        User user2 = new User(userId, "Jane", "jane@example.com", "Smith");
        assertEquals(user1.hashCode(), user2.hashCode());
    }

    @Test 
    public void testNormalizeEmail() {
        String email = "JOHN@EXAMPLE.COM";
        user.setUserEmail(email);
        String normalizedEmail = user.getEmail();
        assertEquals("john@example.com", normalizedEmail);
    }

}
