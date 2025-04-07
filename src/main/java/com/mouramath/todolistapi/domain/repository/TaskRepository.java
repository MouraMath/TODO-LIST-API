package com.mouramath.todolistapi.domain.repository;


import com.mouramath.todolistapi.domain.entity.Task;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

@Repository
public interface TaskRepository {
    Task save(Task task);
    Optional<Task> findById(String id);
    List<Task> findByUserId(String userId);
    List<Task> findUserIdAndCompleted(String userId, boolean completed);
    CompletableFuture<List<Task>> findAllByUserIdAsync(String userId);
    List<Task> findByUserIdAndDueDateBetween(String userId, LocalDateTime startDate, LocalDateTime endDate);
    List<Task> findByUserIdAndDueDateBeforeAndCompletedFalse(String userId, LocalDateTime now);
    long countByUserIdAndCompleted(String userId, boolean completed);
    void deleteById(String id);

}