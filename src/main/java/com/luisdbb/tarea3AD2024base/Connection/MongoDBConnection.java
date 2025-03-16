package com.luisdbb.tarea3AD2024base.Connection;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;

@Component
public class MongoDBConnection {

    @Value("${mongodb.datasource.url}")
    private String uri;

    @Value("${mongodb.database.name}")
    private String nombreBase;

    private MongoClient mongoClient; 

    public MongoDatabase conectar() {
        try {
            if (mongoClient == null) { 
                mongoClient = MongoClients.create(uri);
            }
            return mongoClient.getDatabase(nombreBase);
        } catch (Exception e) {
            System.out.println("Error al conectar con MongoDB: " + e.getMessage());
            return null;
        }
    }
   
    public void cerrarConexion() {
        if (mongoClient != null) {
            mongoClient.close();
            mongoClient = null;
        }
    }
}
