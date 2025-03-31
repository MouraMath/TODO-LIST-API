package com.mouramath.todolistapi.infrastructure.persistense;

import com.mouramath.todolistapi.domain.repository.TaskRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DynamoDBTaskRepository implements TaskRepository {
}
