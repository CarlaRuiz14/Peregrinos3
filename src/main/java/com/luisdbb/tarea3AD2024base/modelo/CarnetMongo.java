package com.luisdbb.tarea3AD2024base.modelo;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "backupCarnets") 
public class CarnetMongo {

    @Id  
    private String nombreDocumento; // Nuevo campo para el nombre
    private String contenidoXML; 

    public CarnetMongo() {}

    public CarnetMongo(String nombreDocumento, String contenidoXML) {
        this.nombreDocumento = nombreDocumento;
        this.contenidoXML = contenidoXML;
    }  

    public String getNombreDocumento() {
        return nombreDocumento;
    }

    public void setNombreDocumento(String nombreDocumento) {
        this.nombreDocumento = nombreDocumento;
    }

    public String getContenidoXML() {
        return contenidoXML;
    }

    public void setContenidoXML(String contenidoXML) {
        this.contenidoXML = contenidoXML;
    }
}
