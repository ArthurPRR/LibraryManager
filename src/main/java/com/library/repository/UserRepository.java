package com.library.repository;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import com.library.model.User;

public class UserRepository {
    private final Map<UUID, User> users = new HashMap<>();

    public UserRepository() {
    }

    public void save(User user) {
        if (user == null) {
            throw new IllegalArgumentException("User cannot be null");
        }
        if (existsById(user.getUserId())) {
            throw new IllegalArgumentException("User with this ID already exists");
        }
        if (existsByEmail(user.getEmail())) {
            throw new IllegalArgumentException("User with this email already exists");
        }
        users.put(user.getUserId(), user);
    }
    
    public Optional<User> findById(UUID userId) {
        return Optional.ofNullable(users.get(userId));
    }

    public void deleteById(UUID userId) {
        if (userId == null) {
            throw new IllegalArgumentException("User ID cannot be null");
        }
        if (!existsById(userId)) {
            throw new IllegalArgumentException("User with this ID does not exist");
        }
        users.remove(userId);
    }

    public boolean existsById(UUID userId) {
        return users.containsKey(userId);
    }

    public Optional<User> findByEmail(String email) {
        if (email == null || email.isBlank()) {
            throw new IllegalArgumentException("Email cannot be null or blank");
        }
        return users.values().stream()
                .filter(user -> user.getEmail().equalsIgnoreCase(email.trim()))
                .findFirst();
    }
    
    public boolean existsByEmail(String email) {
        if (email == null || email.isBlank()) {
            throw new IllegalArgumentException("Email cannot be null or blank");
        }
        return users.values().stream()
                .anyMatch(user -> user.getEmail().equalsIgnoreCase(email.trim()));
    }


    public void updateEmail(UUID userId, String newEmail) {
        if (userId == null || newEmail == null || newEmail.isBlank()) {
            throw new IllegalArgumentException("User ID and new email cannot be null or blank");
        }
        User user = users.get(userId);
        if (user == null) {
            throw new IllegalArgumentException("User with this ID does not exist");
        }
        if (user.getEmail().equalsIgnoreCase(newEmail.trim())) {
            throw new IllegalArgumentException("New email is the same as the current email");
        }

        if (existsByEmail(newEmail)) {
            throw new IllegalArgumentException("User with this email already exists");
        }
        user.setUserEmail(newEmail);
    }

    public void updateFirstName(UUID userId, String newName) {
        if (userId == null || newName == null || newName.isBlank()) {
            throw new IllegalArgumentException("User ID and new name cannot be null or blank");
        }
        User user = users.get(userId);
        if (user == null) {
            throw new IllegalArgumentException("User with this ID does not exist");
        }
        user.setUserFirstName(newName);
    }

    public void updateLastName(UUID userId, String newLastName) {
        if (userId == null || newLastName == null || newLastName.isBlank()) {
            throw new IllegalArgumentException("User ID and new last name cannot be null or blank");
        }
        User user = users.get(userId);
        if (user == null) {
            throw new IllegalArgumentException("User with this ID does not exist");
        }
        user.setUserLastName(newLastName);
    }
}
