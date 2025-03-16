package com.luisdbb.tarea3AD2024base.services;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.luisdbb.tarea3AD2024base.modelo.CarnetMongo;
import com.luisdbb.tarea3AD2024base.modelo.Peregrino;
import com.luisdbb.tarea3AD2024base.repositorios.CarnetMongoRepository;

@Service
public class CarnetMongoService {

	
	@Autowired
	private PeregrinoService peregrinoService;
	
	@Autowired 
	private CarnetService carnetService;
	
	@Autowired
	private CarnetMongoRepository carnetMongoRepository;

	public void saveBackupCarnets() {		
		
		List<Peregrino> todosPeregrinos = peregrinoService.findAll();
		
		for(Peregrino per:todosPeregrinos) {			
			carnetService.exportarCarnet(per);			
		}		
		
		String xmlUnico = generarXMLUnico(); 
		CarnetMongo documento = new CarnetMongo(xmlUnico);
		carnetMongoRepository.save(documento);

	}
	
	

    private String generarXMLUnico() {
        StringBuilder xmlFinal = new StringBuilder();
        
        xmlFinal.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n");
        xmlFinal.append("<carnets>\n");

        File archivoCarnets = new File("carnets");

        if (!archivoCarnets.exists() || !archivoCarnets.isDirectory()) {
            System.err.println("La carpeta 'carnets' no existe.");
            return "";
        }

        File[] carnets = archivoCarnets.listFiles((dir, name) -> name.endsWith(".xml"));
        if (carnets != null && carnets.length > 0) {
            for (File carnet : carnets) {
                try {
                    String contenido = new String(Files.readAllBytes(Paths.get(carnet.getPath())));

                    xmlFinal.append(contenido).append("\n\n");

                } catch (IOException e) {
                    System.err.println("Error al leer XML " + carnet.getName() + ": " + e.getMessage());
                }
            }
        } else {
            System.out.println("No hay archivos XML en la carpeta.");
        }

        xmlFinal.append("</carnets>"); 
        return xmlFinal.toString();
    }
	
	
	
}
