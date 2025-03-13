package com.luisdbb.tarea3AD2024base.Connection;

import org.xmldb.api.DatabaseManager;
import org.xmldb.api.base.Collection;
import org.xmldb.api.base.Database;
import org.xmldb.api.base.Resource;
import org.xmldb.api.base.XMLDBException;
import org.xmldb.api.modules.CollectionManagementService;
import org.xmldb.api.modules.XMLResource;

import java.io.File;

import org.exist.xmldb.DatabaseInstanceManager;
import org.exist.xmldb.EXistResource;

public class pruebaBorrar {
	public static void main(String args[]) {
//		// URI colección
//		String URI = "xmldb:exist://localhost:8080/exist/xmlrpc/db/objetos";
//
//		Collection col = null;
//		XMLResource res = null;
//		try {
//			/// inicializar driver
//			Class cl = Class.forName("org.exist.xmldb.DatabaseImpl");
//			Database database = (Database) cl.newInstance();
//			database.setProperty("create-database", "true");
//			DatabaseManager.registerDatabase(database);
//			/// conectar con una coleccion de existDB
//			col = DatabaseManager.getCollection(URI, "admin", "");
//			/// listar los elementos de la coleccion
//			String resources[] = col.listResources();
//			System.out.println("Resources:");
//			for (int i = 0; i < resources.length; i++) {
//				System.out.println(resources[i]);
//			}
//
//			/// Crear nueva collecion dentro de la coleccion en la que estemos
//			CollectionManagementService mgtService = (CollectionManagementService) col
//					.getService("CollectionManagementService", "1.0");
//			mgtService.createCollection("NUEVA");
//			/// Borrar coleccion
//			mgtService.removeCollection("NUEVA");
//			
//			
//			
//			
//			
//			
//			
//			// create new XMLResource dentro de la coleccion en la que estemos
//			File f = new File("prueba.xml");
//			res = (XMLResource) col.createResource(f.getName(), "XMLResource");
//			res.setContent(f);
		
		
		
		
//			System.out.print("storing document " + res.getId() + "...");
//			col.storeResource(res);
//			System.out.println("ok.");
//			
//			
//			
//			
//			
//			
//			try {
//				/// borrar un XMLResource de una coleccion
//				Resource recursoParaBorrar = col.getResource("prueba.xml");
//				col.removeResource(recursoParaBorrar);
//				System.out.println(recursoParaBorrar.getId() + " borrado.");
//			} catch (NullPointerException e) {
//				System.out.println("No se puede borrar. No se encuentra." + res.getId());
//			}
//		} catch (XMLDBException e1) {
//			e1.printStackTrace();
//		} catch (ClassNotFoundException e1) {
//			e1.printStackTrace();
//		} catch (InstantiationException e1) {
//			e1.printStackTrace();
//		} catch (IllegalAccessException e1) {
//			e1.printStackTrace();
//		} finally {
//			/// limpiar resultados
//			if (res != null) {
//				try {
//					((EXistResource) res).freeResources();
//				} catch (XMLDBException xe) {
//					xe.printStackTrace();
//				}
//			}
//
//			if (col != null) {
//				try {
//
//					col.close();
//					/// Apagar el servidor ExistDB
//					// DatabaseInstanceManager manager = (DatabaseInstanceManager)
//					/// col.getService("DatabaseInstanceManager", "1.0");
//					// manager.shutdown();
//				} catch (XMLDBException xe) {
//					xe.printStackTrace();
//				}
//			}
//		}

		
//		        try {
//		            Class<?> cl = Class.forName("org.exist.xmldb.DatabaseImpl");
//		            System.out.println("✅ Driver encontrado: " + cl.getName());
//		        } catch (ClassNotFoundException e) {
//		            System.err.println("❌ Driver de eXistDB no encontrado.");
//		        }
		
		 String driver = "org.exist.xmldb.DatabaseImpl";
	        String uri = "xmldb:exist://localhost:8080/exist/xmlrpc/db";
	        String user = "admin";
	        String password = "";

	        try {
	            Class<?> cl = Class.forName(driver);
	            Database database = (Database) cl.getDeclaredConstructor().newInstance();
	            DatabaseManager.registerDatabase(database);

	            Collection col = DatabaseManager.getCollection(uri, user, password);
	            if (col != null) {
	                System.out.println("✅ Conexión exitosa a eXistDB.");
	                col.close();
	            } else {
	                System.out.println("❌ No se pudo conectar a la colección.");
	            }
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	    
		    
		
	}

}
