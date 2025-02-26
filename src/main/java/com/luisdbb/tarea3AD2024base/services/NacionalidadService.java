package com.luisdbb.tarea3AD2024base.services;

import java.io.File;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

/**
 * Servicio para la gestión de nacionalidades a partir del archivo XML de
 * países.
 * 
 * @author Carla Ruiz
 * @since 28/12/2024
 */
@Service
public class NacionalidadService {

	/**
	 * Método que SELECCIONA los paises de paises.xml y los almacena en un mapa
	 * 
	 * @return Map de clave-siglas,valor-nacionalidad
	 * @throws ParserConfigurationException lanzdada si hay error de configuración
	 *                                      del parser XML
	 * @throws IOException                  lanzada si hay error al acceder al
	 *                                      archivo
	 * @throws Exception                    lanzada si hay error general al procesar
	 *                                      el archivo
	 * 
	 */
	public LinkedHashMap<String, String> mapaNacionalidades() {

		LinkedHashMap<String, String> nacionalidades = new LinkedHashMap<>();

		try {
			DocumentBuilderFactory fabricaConstructorDocumento = DocumentBuilderFactory.newInstance();
			DocumentBuilder constructorDocumento = fabricaConstructorDocumento.newDocumentBuilder();

			File fichero = new File("src/main/resources/paises.xml");
			Document arbol = constructorDocumento.parse(fichero);

			NodeList listaPaises = arbol.getElementsByTagName("paises");
			int indicePaises = 0;

			while (indicePaises < listaPaises.getLength()) {

				Element paises = (Element) listaPaises.item(indicePaises);
				NodeList listaPais = paises.getElementsByTagName("pais");

				int indicePais = 0;

				while (indicePais < listaPais.getLength()) {
					Element pais = (Element) listaPais.item(indicePais);

					String id = pais.getElementsByTagName("id").item(0).getTextContent();
					String nombre = pais.getElementsByTagName("nombre").item(0).getTextContent();

					nacionalidades.put(id, nombre);
					indicePais++;
				}
				indicePaises++;
			}
		} catch (ParserConfigurationException e) {
			System.out.println("Error de configuración del parser XML: " + e.getMessage());
		} catch (IOException e) {
			System.out.println("Error al acceder al archivo: " + e.getMessage());
		} catch (Exception e) {
			System.out.println("Error general al procesar el archivo: " + e.getMessage());
		}
		return nacionalidades;
	}

	/**
	 * Busca la clave asociada a una nacionalidad dentro del mapa de nacionalidades.
	 * 
	 * @param mapa  Mapa con nacionalidades.
	 * @param valor Nombre de la nacionalidad a buscar.
	 * @return Clave asociada a la nacionalidad o {@code null} si no se encuentra.
	 */
	public String buscarClavePorValor(LinkedHashMap<String, String> mapa, String valor) {
		for (Map.Entry<String, String> entrada : mapa.entrySet()) {
			if (entrada.getValue().equals(valor)) {
				return entrada.getKey();
			}
		}
		return null;
	}

	/**
	 * Obtiene la clave de la nacionalidad seleccionada en función del valor
	 * proporcionado.
	 * 
	 * @param seleccion Nombre de la nacionalidad seleccionada.
	 * @return Clave de la nacionalidad o {@code null} si no se encuentra.
	 */
	public String obtenerNacionalidadSeleccionada(String seleccion) {
		return buscarClavePorValor(mapaNacionalidades(), seleccion);
	}

}
