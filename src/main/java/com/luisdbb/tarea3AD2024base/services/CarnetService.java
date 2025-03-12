package com.luisdbb.tarea3AD2024base.services;

import java.io.File;
import java.time.LocalDate;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.w3c.dom.DOMImplementation;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Text;

import com.luisdbb.tarea3AD2024base.modelo.Carnet;
import com.luisdbb.tarea3AD2024base.modelo.Estancia;
import com.luisdbb.tarea3AD2024base.modelo.Parada;
import com.luisdbb.tarea3AD2024base.modelo.Peregrino;
import com.luisdbb.tarea3AD2024base.repositorios.CarnetRepository;

/**
 * Servicio para la gestión de los carnets de peregrinos.
 * 
 * @author Carla Ruiz
 * @since 28/12/2024
 */
@Service
public class CarnetService {

	@Autowired
	private CarnetRepository carnetRepository;

	@Autowired
	private NacionalidadService nacionalidadService;

	@Autowired
	private ParadaService paradaService;

	@Autowired
	private EstanciaService estanciaService;

	/**
	 * Guarda un carnet en la base de datos.
	 * 
	 * @param entidad Carnet a guardar.
	 * @return Carnet guardado o {@code null} si no lo guarda.
	 */
	public Carnet save(Carnet entidad) {
		return carnetRepository.save(entidad);
	}

	/**
	 * Busca un carnet por su identificador único.
	 * 
	 * @param id Identificador del carnet.
	 * @return Carnet encontrado o {@code null} si no existe.
	 */
	public Carnet findById(long id) {
		return carnetRepository.findById(id);
	}

	/**
	 * Exporta la información de un carnet a un archivo XML.
	 * 
	 * @param p Peregrino cuyo carnet será exportado.
	 * @return Código de resultado de la exportación:
	 *         <ul>
	 *         <li>0 - Exportación exitosa.</li>
	 *         <li>1 - Error en la configuración del parser.</li>
	 *         <li>2 - Error en la configuración del transformador XML.</li>
	 *         <li>3 - Error en la transformación de datos a XML.</li>
	 *         <li>4 - Error por datos nulos en la exportación.</li>
	 *         </ul>
	 */
	public int exportarCarnet(Peregrino p) {

		List<Parada> listaParadas = paradaService.obtenerParadasPorPeregrino(p.getId());
		List<Estancia> listaEstancias = estanciaService.findByPeregrinoId(p.getId());

		try {
			DocumentBuilderFactory fabricaConstructorDocumento = DocumentBuilderFactory.newInstance();
			DocumentBuilder constructorDocumento = fabricaConstructorDocumento.newDocumentBuilder();
			DOMImplementation implementacion = constructorDocumento.getDOMImplementation();

			Document documento = implementacion.createDocument(null, "carnet", null);

			Element carnet = documento.getDocumentElement();

			Element id, fechaexp, expedidoen, peregrino, nombre, nacionalidad, hoy, distanciatotal, paradas, parada,
					orden, nombreparada, region, estancias, estancia, idestancia, fecha, paradaestancia, vip;

			Text idValor, fechaexpValor, expedidoValor, nombreValor, nacionalidadValor, hoyValor, distanciatotalValor,
					ordenValor, nombreparadaValor, regionValor, idestanciaValor, fechaValor, paradaestanciaValor,
					vipValor;

			id = documento.createElement("id");
			carnet.appendChild(id);
			idValor = documento.createTextNode(String.valueOf(p.getId()));
			id.appendChild(idValor);

			fechaexp = documento.createElement("fechaexp");
			carnet.appendChild(fechaexp);
			fechaexpValor = documento.createTextNode(String.valueOf(p.getCarnet().getFechaExp()));
			fechaexp.appendChild(fechaexpValor);

			expedidoen = documento.createElement("expedidoen");
			carnet.appendChild(expedidoen);
			expedidoValor = documento.createTextNode(String.valueOf(p.getCarnet().getParadaInicial()));
			expedidoen.appendChild(expedidoValor);

			peregrino = documento.createElement("peregrino");
			carnet.appendChild(peregrino);

			nombre = documento.createElement("nombre");
			peregrino.appendChild(nombre);
			nombreValor = documento.createTextNode(p.getNombre());
			nombre.appendChild(nombreValor);

			String claveNac = p.getNacionalidad();
			String valorNac = nacionalidadService.mapaNacionalidades().get(claveNac);

			nacionalidad = documento.createElement("nacionalidad");
			peregrino.appendChild(nacionalidad);
			nacionalidadValor = documento.createTextNode(valorNac);
			nacionalidad.appendChild(nacionalidadValor);

			hoy = documento.createElement("hoy");
			carnet.appendChild(hoy);
			hoyValor = documento.createTextNode(LocalDate.now().toString());
			hoy.appendChild(hoyValor);

			distanciatotal = documento.createElement("distanciatotal");
			carnet.appendChild(distanciatotal);
			distanciatotalValor = documento.createTextNode(String.valueOf(p.getCarnet().getDistancia()));
			distanciatotal.appendChild(distanciatotalValor);

			paradas = documento.createElement("paradas");
			carnet.appendChild(paradas);

			if (!listaParadas.isEmpty()) {
				for (int i = 0; i < listaParadas.size(); i++) {

					Parada paradaLeida = listaParadas.get(i);

					parada = documento.createElement("parada");
					paradas.appendChild(parada);

					orden = documento.createElement("orden");
					parada.appendChild(orden);
					ordenValor = documento.createTextNode(String.valueOf(i + 1));
					orden.appendChild(ordenValor);

					nombreparada = documento.createElement("nombre");
					parada.appendChild(nombreparada);
					nombreparadaValor = documento.createTextNode(paradaLeida.getNombre());
					nombreparada.appendChild(nombreparadaValor);

					region = documento.createElement("region");
					parada.appendChild(region);
					regionValor = documento.createTextNode(String.valueOf(paradaLeida.getRegion()));
					region.appendChild(regionValor);
				}

			} else {
				paradas = documento.createElement("paradas");
				carnet.appendChild(paradas);
				parada = documento.createElement("parada");
				paradas.appendChild(parada);
				nombreparada = documento.createElement("nombre");
				parada.appendChild(nombreparada);
				nombreparadaValor = documento.createTextNode("No hay paradas");
				parada.appendChild(nombreparadaValor);
			}

			if (!listaEstancias.isEmpty()) {

				estancias = documento.createElement("estancias");
				carnet.appendChild(estancias);

				for (int i = 0; i < listaEstancias.size(); i++) {

					Estancia estanciaLeida = listaEstancias.get(i);

					estancia = documento.createElement("estancia");
					estancias.appendChild(estancia);

					idestancia = documento.createElement("id");
					estancia.appendChild(idestancia);
					idestanciaValor = documento.createTextNode(String.valueOf(estanciaLeida.getId()));
					idestancia.appendChild(idestanciaValor);

					fecha = documento.createElement("fecha");
					estancia.appendChild(fecha);
					fechaValor = documento.createTextNode(estanciaLeida.getFecha().toString());
					fecha.appendChild(fechaValor);

					paradaestancia = documento.createElement("paradaestancia");
					estancia.appendChild(paradaestancia);
					paradaestanciaValor = documento.createTextNode(estanciaLeida.getParada().getNombre());
					paradaestancia.appendChild(paradaestanciaValor);

					if (estanciaLeida.getVip()) {
						vip = documento.createElement("vip");
						estancia.appendChild(vip);
						vipValor = documento.createTextNode("Estancia VIP");
						vip.appendChild(vipValor);
					}
				}
			} else {
				estancias = documento.createElement("estancias");
				carnet.appendChild(estancias);
				estancia = documento.createElement("estancia");
				estancias.appendChild(estancia);
				idestancia = documento.createElement("id");
				estancia.appendChild(idestancia);
				idestanciaValor = documento.createTextNode(String.valueOf("No hay estancias"));
				idestancia.appendChild(idestanciaValor);
			}

			Source fuente = new DOMSource(documento);
			String ruta = "carnets/" + p.getNombre() + ".xml";
			File fichero = new File(ruta);
			Result resultado = new StreamResult(fichero);

			TransformerFactory fabricaTransformador = TransformerFactory.newInstance();
			Transformer transformador = fabricaTransformador.newTransformer();

			transformador.setOutputProperty(javax.xml.transform.OutputKeys.ENCODING, "UTF-8");
			transformador.setOutputProperty(javax.xml.transform.OutputKeys.INDENT, "yes");

			transformador.transform(fuente, resultado);

			return 0;

		} catch (ParserConfigurationException e) {
			return 1;
		} catch (TransformerConfigurationException e) {
			return 2;
		} catch (TransformerException e) {
			return 3;
		} catch (NullPointerException e) {
			return 4;
		}
	}
}
