package com.mouramath.todolistapi.domain.repository;

import com.mouramath.todolistapi.domain.entity.User;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.List;

public interface UserRepository {

    User save(User user);
    Optional<User> findById(String id);
    Optional<User> findByUsername(String username);
    Optional<User> findByEmail(String email);
    boolean existsById(String id);
    List<User> findAll();
    void deleteById(String id);
    CompletableFuture<User> findByIdAsync(String id);
}
