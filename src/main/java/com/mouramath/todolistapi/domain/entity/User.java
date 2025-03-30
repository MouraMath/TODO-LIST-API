package com.mouramath.todolistapi.domain.entity;


import com.amazonaws.services.dynamodbv2.datamodeling.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@DynamoDBTable(tableName = "Users")
public class User {

    @DynamoDBHashKey
    private String id;

    @DynamoDBAttribute
    @DynamoDBIndexHashKey(globalSecondaryIndexName = "usernameIndex")
    private String username;

    @DynamoDBAttribute
    private String password;

    @DynamoDBAttribute
    private String email; //TODO revisar tipo email

    @DynamoDBAttribute
    private String fullName;

    @DynamoDBAttribute
    private List<String> roles;

}

/** roles
 * Uso lista de strings para roles em vez de enum para permitir mais flexibilidade.
 * Isso facilita adicionar novas roles no futuro sem alterar o modelo.
 */