package com.luisdbb.tarea3AD2024base.ObjectDB;


import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.springframework.stereotype.Component;

@Component
public class ObjectDBConnection {

    private static final String objectdbPath = "objectdb:ODBB_Peregrinos.odb";
    private EntityManagerFactory emf;
  
    public void abrirConexion() {
        if (emf == null || !emf.isOpen()) {
            emf = Persistence.createEntityManagerFactory(objectdbPath);        }
    }
    
    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void cerrarConexion() {
        if (emf != null && emf.isOpen()) {
            emf.close();
        }
    }
}