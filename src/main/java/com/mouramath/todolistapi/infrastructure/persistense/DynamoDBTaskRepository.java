package com.mouramath.todolistapi.infrastructure.persistense;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.mouramath.todolistapi.domain.entity.Task;
import com.mouramath.todolistapi.domain.repository.TaskRepository;
import org.springframework.stereotype.Repository;

@Repository
public class DynamoDBTodoRepository implements TaskRepository {

    private final DynamoDBMapper dynamoDBMapper;

    public DynamoDBTodoRepository(DynamoDBMapper dynamoDBMapper){
        this.dynamoDBMapper = dynamoDBMapper;
    }

    @Override
    public Task save(Task task){
        dynamoDBMapper.save(task);
        return task;
    }



}
