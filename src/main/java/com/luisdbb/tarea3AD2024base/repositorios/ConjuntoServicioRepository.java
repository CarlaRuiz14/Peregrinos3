package com.luisdbb.tarea3AD2024base.repositorios;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.db4o.ObjectContainer;
import com.db4o.ObjectSet;
import com.luisdbb.tarea3AD2024base.config.DataConnection;
import com.luisdbb.tarea3AD2024base.modelo.SecuenciaId;
import com.luisdbb.tarea3AD2024base.modelo.Servicio;

@Repository
public class ConjuntoServicioRepository {
	
	@Autowired
    private DataConnection dataConnection;
   

	public List<Servicio> findAllServicios() {
	    ObjectContainer db = dataConnection.getDb();

	    Servicio ejemplo = new Servicio(); 
	    // consulta con ejemplo vacio para sacar todos 
	    ObjectSet<Servicio> result = db.queryByExample(ejemplo);

	    return new ArrayList<>(result);
	}

	
	// METODOS SECUENCIAID
	public void crearSecuenciaId() {
        ObjectContainer db = dataConnection.getDb();

        SecuenciaId ejemplo = new SecuenciaId();
        ObjectSet<SecuenciaId> result = db.queryByExample(ejemplo);

        if (result.isEmpty()) {           
            SecuenciaId nueva = new SecuenciaId(0);
            db.store(nueva);
            db.commit();             
        }
    }
}
