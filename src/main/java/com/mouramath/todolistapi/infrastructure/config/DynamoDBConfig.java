package com.mouramath.todolistapi.infrastructure.config;


import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDB;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;

@Configuration
public class DynamoDBConfig {

 //ambiente de desenvolvimento local/offline

   @Value("${aws.dynamodb.endpoint}")
    private String dynamoDBEndpoint;

   @Value("$aws.region")
    private String awsRegion;

   @Value("$aws.accessKey")
    private String accessKey;

   @Value("$aws.secretKey")
    private String secretKey;




    @Bean
   public AmazonDynamoDB amazonDynamoDB() {
       return AmazonDynamoDBClientBuilder.standard()
               .withEndpointConfiguration(
                       new AwsClientBuilder.EndpointConfiguration(dynamoDBEndpoint, awsRegion))
               .withCredentials(
                       new AWSStaticCredentialsProvider(
                               new BasicAWSCredentials(accessKey, secretKey)))
               .build();


   }

    @Bean
    public DynamoDBMapper dynamoDBMapper(){
        return new DynamoDBMapper(amazonDynamoDB());
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