package com.luisdbb.tarea3AD2024base.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.db4o.Db4oEmbedded;
import com.db4o.ObjectContainer;
import com.db4o.config.EmbeddedConfiguration;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;

@Component
public class DataConnection {

	private ObjectContainer db;
	
	@Value("${db4o.path}")
	private String path;

	@PostConstruct
	public void openConnection() {
		if (db == null || db.ext().isClosed()) {
			EmbeddedConfiguration config = Db4oEmbedded.newConfiguration();
			db = Db4oEmbedded.openFile(config, path);
		}
	}	
	
	public ObjectContainer getDb() {
		if (db == null || db.ext().isClosed()) {
			throw new IllegalStateException("La conexión a db4o no está abierta. Llama a openConnection() primero.");
		}
		return db;
	}

	@PreDestroy
	public void closeConnection() {
		if (db != null && !db.ext().isClosed()) {
			db.close();
		}
	}

}