package com.mouramath.todolistapi.domain.service;


import com.mouramath.todolistapi.domain.entity.Task;
import com.mouramath.todolistapi.domain.entity.TaskStatus;
import com.mouramath.todolistapi.domain.entity.User;
import com.mouramath.todolistapi.domain.exception.ResourceNotFoundException;
import com.mouramath.todolistapi.domain.exception.UnauthorizedException;
import com.mouramath.todolistapi.domain.repository.TaskRepository;
import com.mouramath.todolistapi.domain.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class TaskService {

    private static final Logger logger =
            LoggerFactory.getLogger(TaskService.class);
    private final TaskRepository taskRepository;
    private final UserRepository userRepository;

    public TaskService(TaskRepository taskRepository, UserRepository userRepository) {
        this.taskRepository = taskRepository;
        this.userRepository = userRepository;
    }


    public Task createTask(String userId, Task task){
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + userId));

                task.setUserId(userId);
                task.setCreated_at(LocalDateTime.now());


                if(task.getStatus() == null){
                    task.setStatus(TaskStatus.BACKLOG);
                }


                logger.info("Creating task for user: {}", userId);
                return taskRepository.save(task);
                }


    //Optional.orElseThrow em vez de verificações manuais de nulidade para código mais limpo
    // e menos propenso a NullPointerException

    public Task getTaskById(String id, String usedId) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Task not found with id: " +id));

        if(!task.getUserId().equals(usedId)){
            throw new UnauthorizedException("You don't have permission to access this task");
        }

        return task;
    }

    public Task updateTask(String id, Task updatedTask, String usedId) {
        Task existingTask = getTaskById(id, usedId);

        //Atualiza campos não-nulos

        if(updatedTask.getTitle() != null){
            existingTask.setTitle(updatedTask.getTitle());
        }

        if(updatedTask.getDescription() != null){
            existingTask.setDescription(updatedTask.getDescription());
        }

        if(updatedTask.getDueDate() != null){
            existingTask.setDueDate(updatedTask.getDueDate());
        }

        if(updatedTask.getStatus() != null){
            existingTask.setStatus(updatedTask.getStatus());
        }

            if(updatedTask.getStatus().isCompleted()){
                existingTask.isCompleted(LocalDateTime.now()); // TODO corrigir
            }else{
                existingTask.setCompleted(false);
            }

            existingTask.setUpdated_at(LocalDateTime.now());

            return taskRepository.save(existingTask);

    }

    //Busca tarefas vencidas e não concluídas
    //Uso de method específico no repository em vez de buscar todas e filtrar,
    //reduzindo a quantidade de dados transferidos do DynamoDB

    public List<Task> getOverdueTasks(String userId) {
        return taskRepository.findByUserIdAndDueDateBeforeAndCompletedFalse(userId, LocalDateTime.now());

    }


        }




