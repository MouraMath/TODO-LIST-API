package com.mouramath.todolistapi.infrastructure.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
public class DynamoDBConfig {

 //ambiente de desenvolvimento local/offline

    @Bean
    @Profile("dev")
    public DynamoDbClient dynamoDbClientDev(){


    }


}

/**
 * Configuração para ambiente de desenvolvimento usando DynamoDB local.
 * Uso o DynamoDB local para desenvolvimento porque:
 * 1. Evita custos desnecessários durante o desenvolvimento
 * 2. Permite desenvolvimento offline
 * 3. Oferece tempos de resposta mais rápidos para testes
 *
 * Complexidade: O(1) para operações de configuração, pois são executadas apenas uma vez na inicialização
 */