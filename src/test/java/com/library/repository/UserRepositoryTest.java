package com.library.repository;

import com.library.model.User;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;



public class UserRepositoryTest {
    private UserRepository userRepository;

    @BeforeEach
    public void setUp() {
        userRepository = new UserRepository();
    }

    @Test
    public void testSaveUser() {
        UUID userId = UUID.randomUUID();
        User user = new User(userId, "John", "Doe", "john.doe@example.com");
        userRepository.save(user);
        assertEquals(user, userRepository.findById(userId).orElse(null));
    }

    @Test
    public void testSaveNullUser() {
        assertThrows(IllegalArgumentException.class, () -> userRepository.save(null));
    }

    @Test
    public void testSaveDuplicateId() {
        UUID userId = UUID.randomUUID();
        User user1 = new User(userId, "John", "Doe", "john.doe@example.com");
        User user2 = new User(userId, "Jane", "Doe", "jane.doe@example.com");
        userRepository.save(user1);
        assertThrows(IllegalArgumentException.class, () -> userRepository.save(user2));
    }

    @Test
    public void testSaveDuplicateEmail() {
        UUID userId1 = UUID.randomUUID();
        UUID userId2 = UUID.randomUUID();
        User user1 = new User(userId1, "John", "Doe", "john.doe@example.com");
        User user2 = new User(userId2, "Jane", "Doe", "john.doe@example.com");
        userRepository.save(user1);
        assertThrows(IllegalArgumentException.class, () -> userRepository.save(user2));
    }


    @Test
    public void testFindById() {
        UUID userId = UUID.randomUUID();
        User user = new User(userId, "John", "Doe", "john.doe@example.com");
        userRepository.save(user);
        assertTrue(userRepository.existsById(userId));
        assertEquals(user, userRepository.findById(userId).orElse(null));
    }

    @Test
    public void testFindByIdNotFound() {
        UUID userId = UUID.randomUUID();
        assertFalse(userRepository.existsById(userId));
    }

    @Test
    public void testFindByEmail() {
        UUID userId = UUID.randomUUID();
        User user = new User(userId, "John", "Doe", "john.doe@example.com");
        userRepository.save(user);
        assertEquals(user, userRepository.findByEmail("john.doe@example.com").orElse(null));
    }

    @Test
    public void testFindByEmailNotFound() {
        assertTrue(userRepository.findByEmail("nonexistent@example.com").isEmpty());
    }

    @Test
    public void testDeleteById() {
        UUID userId = UUID.randomUUID();
        User user = new User(userId, "John", "Doe", "john.doe@example.com");
        userRepository.save(user);
        userRepository.deleteById(userId);
        assertFalse(userRepository.existsById(userId));           
    }

    @Test
    public void testFindByEmailWithDifferentCase() {
        UUID userId = UUID.randomUUID();
        User user = new User(userId, "John", "Doe", "john.doe@example.com");
        userRepository.save(user);
        assertEquals(user, userRepository.findByEmail("JOHN.DOE@EXAMPLE.COM").orElse(null));
    }


    @Test
    public void testUpdateEmail() {
        UUID userId = UUID.randomUUID();
        User user = new User(userId, "John", "Doe", "john.doe@example.com");
        userRepository.save(user);
        String newEmail = "jane.doe@example.com";
        userRepository.updateEmail(userId, newEmail);
        assertEquals(newEmail, userRepository.findById(userId).orElse(null).getEmail());
        assertTrue(userRepository.existsByEmail(newEmail));
        assertFalse(userRepository.existsByEmail("john.doe@example.com"));
    }

    @Test
    public void testUpdateEmailAlreadyUsed() {
        UUID userId1 = UUID.randomUUID();
        UUID userId2 = UUID.randomUUID();
        User user1 = new User(userId1, "John", "Doe", "john.doe@example.com");
        User user2 = new User(userId2, "Jane", "Doe", "jane.doe@example.com");
        userRepository.save(user1);
        userRepository.save(user2);
        assertThrows(IllegalArgumentException.class, () -> userRepository.updateEmail(userId1, "jane.doe@example.com"));
    }
    
    @Test
    public void testUpdateEmailNotFound() {
        assertThrows(IllegalArgumentException.class, () -> userRepository.updateEmail(UUID.randomUUID(), "john.smith@example.com"));
    }


    @Test
    public void testUpdateName() {
        UUID userId = UUID.randomUUID();
        User user = new User(userId, "John", "Doe", "john.doe@example.com");
        userRepository.save(user);
        String newFirstName = "Jonathan";
        String newLastName = "Smith";
        userRepository.updateFirstName(userId, newFirstName);
        assertEquals(newFirstName, userRepository.findById(userId).orElse(null).getFirstName());
        userRepository.updateLastName(userId, newLastName);
        assertEquals(newLastName, userRepository.findById(userId).orElse(null).getLastName());
    }

    @Test
    public void testUpdateNameNotFound() {
        assertThrows(IllegalArgumentException.class, () -> userRepository.updateFirstName(UUID.randomUUID(), "Jonathan"));
        assertThrows(IllegalArgumentException.class, () -> userRepository.updateLastName(UUID.randomUUID(), "Smith"));
    }
}
