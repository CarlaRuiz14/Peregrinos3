package com.luisdbb.tarea3AD2024base.modelo;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "backupCarnets") 
public class CarnetMongo {

	    @Id
	    private String id;
	    private String contenidoXML; 

	    public CarnetMongo() {}

	    public CarnetMongo(String contenidoXML) {
	        this.contenidoXML = contenidoXML;
	    }

	    public String getId() {
	        return id;
	    }

	    public void setId(String id) {
	        this.id = id;
	    }

	    public String getContenidoXML() {
	        return contenidoXML;
	    }

	    public void setContenidoXML(String contenidoXML) {
	        this.contenidoXML = contenidoXML;
	    }
}
