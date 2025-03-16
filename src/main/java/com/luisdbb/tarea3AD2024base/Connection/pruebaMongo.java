package com.luisdbb.tarea3AD2024base.Connection;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.MongoIterable;

public class pruebaMongo {

	private static final String URI = "mongodb://localhost:27017"; // URL del servidor MongoDB

	public static void main(String[] args) {
		try (MongoClient mongoClient = MongoClients.create(URI)) {
			System.out.println("✅ Conexión establecida con MongoDB");

			// Listar las bases de datos disponibles
			MongoIterable<String> basesDeDatos = mongoClient.listDatabaseNames();
			System.out.println("📂 Bases de datos en el servidor:");
			for (String dbName : basesDeDatos) {
				System.out.println(" - " + dbName);
			}

			// Seleccionar una base de datos y verificar si existe
			String nombreBase = "backup"; // Cambia esto si usas otra base
			MongoDatabase database = mongoClient.getDatabase(nombreBase);
			System.out.println("✅ Conectado a la base de datos: " + database.getName());

		} catch (Exception e) {
			System.err.println("❌ Error al conectar con MongoDB: " + e.getMessage());
		}
	}
}
