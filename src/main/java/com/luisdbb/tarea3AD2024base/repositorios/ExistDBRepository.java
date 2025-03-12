package com.luisdbb.tarea3AD2024base.repositorios;

import java.io.File;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.xmldb.api.base.Collection;
import org.xmldb.api.modules.CollectionManagementService;
import org.xmldb.api.modules.XMLResource;

import com.luisdbb.tarea3AD2024base.Connection.ExistDBConnection;

@Repository
public class ExistDBRepository {

	@Autowired
	private ExistDBConnection existDBConnection;

	public void crearColeccionEnParadas(String nombreParada) throws Exception {
		existDBConnection.abrirConexion();
		Collection rootCol = existDBConnection.getCollection();
		try {

			if (rootCol == null) {
				throw new Exception("No se pudo conectar a la colección base.");
			}

			Collection paradasCol = rootCol.getChildCollection("paradas");
			if (paradasCol == null) {
				throw new Exception("La colección 'paradas' no existe. Asegúrate de crearla primero.");
			}

			CollectionManagementService mgtService = (CollectionManagementService) paradasCol
					.getService("CollectionManagementService", "1.0");

			mgtService.createCollection(nombreParada);

		} catch (Exception e) {
			throw new Exception("Error al crear la subcolección en eXistDB");
		} finally {
			existDBConnection.cerrarConexion();
		}
	}

	public void almacenarCarnet(String paradaInicial, File carnetXml) throws Exception {
		existDBConnection.abrirConexion();
		Collection colBase = existDBConnection.getCollection();
		try {
			if (colBase == null) {
				throw new Exception("No se pudo conectar a la colección base en eXistDB.");
			}

			Collection paradasCollection = colBase.getChildCollection("paradas");
			if (paradasCollection == null) {
				throw new Exception("La colección paradas no existe en eXistDB. Debes crearla primero.");
			}

			Collection paradaCollection = paradasCollection.getChildCollection(paradaInicial);
			if (paradaCollection == null) {
				throw new Exception("La colección paradas/ " + paradaInicial + " no existe en eXistDB.");
			}

			XMLResource resource = (XMLResource) paradaCollection.createResource(carnetXml.getName(), "XMLResource");
		
			resource.setContent(carnetXml);

			paradaCollection.storeResource(resource);

		} catch (Exception e) {
			throw new Exception("Error al guardar el carnet en eXistDB");
		} finally {
			existDBConnection.cerrarConexion();
		}
	}

}