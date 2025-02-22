package com.luisdbb.tarea3AD2024base.ObjectDB;


import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.springframework.stereotype.Component;

@Component
public class ObjectDBConnection {

    private EntityManagerFactory emf = 
        Persistence.createEntityManagerFactory("objectdb:ODBB_Peregrinos.odb");
  
    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void cerrarConexion() {
        if (emf != null && emf.isOpen()) {
            emf.close();
            System.out.println("Conexión con ObjectDB cerrada correctamente.");
        }
    }
}