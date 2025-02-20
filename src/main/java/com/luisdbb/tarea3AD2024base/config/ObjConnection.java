package com.luisdbb.tarea3AD2024base.config;



import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

@Component
public class ObjConnection {

    private EntityManagerFactory emf;

    @Value("${objectdb.path}")
    private String dbPath;

    @PostConstruct
    public void openConnection() {
        if (emf == null) {
            emf = Persistence.createEntityManagerFactory("objectdb:" + dbPath);
        }
    }

    public EntityManager getEntityManager() {
        if (emf == null) {
            throw new IllegalStateException("La conexión a ObjectDB no está abierta. Llama a openConnection() primero.");
        }
        return emf.createEntityManager();
    }

    @PreDestroy
    public void closeConnection() {
        if (emf != null) {
            emf.close();
        }
    }
}
