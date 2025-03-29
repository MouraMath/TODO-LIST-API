package com.mouramath.todolistapi.domain.entity;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbBean;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbPartitionKey;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@DynamoDbBean
public class User {

    private String name, password, createdAt, updatedAt, email; //TODO refinar tipo email

    @DynamoDbPartitionKey
    public String getEmail(){
        return email;
    }

}

/*
 * Uso o email como chave de partição (partition key) porque:
 * 1. É naturalmente único para cada usuário
 * 2. É o identificador principal usado no login
 * 3. Distribui uniformemente os dados (melhor que IDs sequenciais)
 * Complexidade de acesso: O(1) para buscas por email
 */
