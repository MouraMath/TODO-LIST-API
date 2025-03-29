package com.mouramath.todolistapi.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbBean;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbPartitionKey;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbSecondaryPartitionKey;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbSortKey;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@DynamoDbBean

public class Todo {

    private String id, userId, title, description, created_at, updated_at;
    private Boolean completed; //TODO conferir backlog, processos, completed
    private List<String> tags;

    @DynamoDbPartitionKey
    public String getUserId() {
        return id;
    }

    @DynamoDbSortKey
    public String getId() {
        return id;
    }

    @DynamoDbSecondaryPartitionKey(indexNames = {"CompletedIndex"})
    public Boolean getCompleted() {
        return completed;
    }





}

/*
 * Uso o userId como chave de partição (partition key) porque:
 * 1. Agrupa todas as tarefas de um usuário na mesma partição física
 * 2. Permite operações eficientes como "listar todas as tarefas do usuário"
 * 3. Distribui a carga entre partições baseado nos usuários
 * Complexidade: O(1) para acessar a partição do usuário
 */

/*
 * Uso o id como chave de ordenação (sort key) porque:
 * 1. Permite identificar unicamente uma tarefa dentro da partição do usuário
 * 2. Facilita a ordenação e paginação de tarefas
 * 3. Possibilita consultas de intervalo (range queries)
 *
 * Complexidade: O(log n) para buscar dentro de uma partição,
 * onde n é o número de tarefas do usuário
 */

/*
 * Defino um índice secundário no campo 'completed' para:
 * 1. Permitir consultas eficientes por status (tarefas concluídas/pendentes)
 * 2. Evitar operações de scan completo que teriam performance O(n)
 */


