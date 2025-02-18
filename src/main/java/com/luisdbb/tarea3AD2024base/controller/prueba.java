package com.luisdbb.tarea3AD2024base.controller;

import com.db4o.Db4oEmbedded;
import com.db4o.ObjectContainer;
import com.db4o.ObjectSet;
import com.luisdbb.tarea3AD2024base.modelo.SecuenciaId;
import com.luisdbb.tarea3AD2024base.modelo.Servicio;

public class prueba {

    public static void main(String[] args) {
    
        String dbPath = "peregrinos.db4o";  
        ObjectContainer db = Db4oEmbedded.openFile(Db4oEmbedded.newConfiguration(), dbPath);

        try {            
            consultarDatos(db);
            consultarDatosSecuencia(db);
        } finally {
            db.close();
        }
    }   

    private static void consultarDatos(ObjectContainer db) {
        ObjectSet<Servicio> result = db.query(Servicio.class);
        System.out.println("Servicios en la base:");
        for (Servicio s : result) {
            System.out.println(s);
        }
    }
    
    private static void consultarDatosSecuencia(ObjectContainer db) {
        ObjectSet<SecuenciaId> result = db.query(SecuenciaId.class);
        System.out.println("Secuencia en la base:");
        for (SecuenciaId s : result) {
            System.out.println(s);
        }
    }
}
