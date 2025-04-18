package com.mouramath.todolistapi.domain.entity;


import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTypeConvertedEnum;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.HashSet;
import java.util.UUID;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@DynamoDBTable(tableName = "Tasks")

public class Task {

    @DynamoDBHashKey
    private String id;

    @DynamoDBAttribute
    private String userId;

    @DynamoDBAttribute
    private String title;

    @DynamoDBAttribute
    private String description;

    @DynamoDBAttribute
    private Set<String> tags;

    @DynamoDBTypeConvertedEnum
    @DynamoDBAttribute
    private TaskStatus status;

    @DynamoDBAttribute
    private boolean completed;

    @DynamoDBAttribute
    private LocalDateTime created_at;

    @DynamoDBAttribute
    private LocalDateTime updated_at;

    @DynamoDBAttribute
    private LocalDateTime completedAt;

    @DynamoDBAttribute
    private LocalDateTime dueDate;


    public void updateStatus(TaskStatus newStatus){
        this.status = newStatus;
        this.completed = newStatus.isCompleted();
        this.updated_at = LocalDateTime.now();

        if(newStatus == TaskStatus.COMPLETED && completedAt == null){
            this.completedAt = LocalDateTime.now();
        } else if(newStatus != TaskStatus.COMPLETED){
            this.completedAt = null;
        }

    }

    public static Task createNew(String title, String description, String userId){
        return Task.builder()
                .id(UUID.randomUUID().toString())
                .userId(userId)
                .title(title)
                .description(description)
                .tags(new HashSet<>())
                .status(TaskStatus.BACKLOG)
                .completed(false)
                .created_at(LocalDateTime.now())
                .updated_at(LocalDateTime.now())
                .build();
    }


}


/**   TaskStatus status
 * Uso o enum para representar o estado detalhado da tarefa.
 * Isso permite mais granularidade na representação do fluxo de trabalho.
 */

/** completed
 * Mantenho o campo completed para consultas rápidas.
 * Aproveito a indexação O(1) em vez de traduzir a partir do enum em cada consulta.
 */

/** tags
 * Uso HashSet para tags pois:
 * 1. Garante unicidade (não faz sentido ter tags duplicadas)
 * 2. Busca em O(1), mais eficiente que ArrayList (O(n))
 * 3. Não preciso manter ordem específica das tags
 */

/** updateStatus
 * Atualiza o status e mantém a consistência com o campo completed.
 * Centralizei este comportamento para garantir que ambos os campos
 * permaneçam sincronizados.
 */