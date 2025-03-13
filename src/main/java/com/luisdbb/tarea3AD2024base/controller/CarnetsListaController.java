package com.luisdbb.tarea3AD2024base.controller;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Controller;

import com.luisdbb.tarea3AD2024base.config.StageManager;
import com.luisdbb.tarea3AD2024base.modelo.Carnet;
import com.luisdbb.tarea3AD2024base.modelo.Sesion;
import com.luisdbb.tarea3AD2024base.services.CarnetService;
import com.luisdbb.tarea3AD2024base.services.ExistDBService;
import com.luisdbb.tarea3AD2024base.services.ParadaService;
import com.luisdbb.tarea3AD2024base.view.FxmlView;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

/**
 * 
 * @author Carla Ruiz
 * @since 28/12/2024
 */
@Controller
public class CarnetsListaController implements Initializable {

	@FXML
	private Hyperlink hpInfo;

	@FXML
	private TableView<Carnet> tblCarnets;

	@FXML
	private TableColumn<Carnet, Long> colIdCarnet;

	@FXML
	private TableColumn<Carnet, LocalDate> colFecha;

	@FXML
	private TableColumn<Carnet, String> colPeregrino;

	@FXML
	private Button btnVolver;

	@FXML
	private Button btnSalir;

	@Lazy
	@Autowired
	private StageManager stageManager;

	@Autowired
	private Botones botones;

	@Autowired
	private Ayuda ayuda;

	@Autowired
	private Mnemonic mnemonicConfig;

	@Autowired
	private Tooltips tooltipConfig;

	@Autowired
	private ExistDBService existDBService;

	@Autowired
	private Sesion sesion;

	@Autowired
	private ParadaService paradaService;

	@Autowired
	private CarnetService carnetService;

	@Autowired
	private Alertas alertas;

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		colIdCarnet.setCellValueFactory(new PropertyValueFactory<>("id"));
		colFecha.setCellValueFactory(new PropertyValueFactory<>("fechaExp"));
		colPeregrino.setCellValueFactory(cellData -> {
			Carnet carnet = cellData.getValue();
			if (carnet != null && carnet.getPeregrino() != null) {
				return new SimpleStringProperty(carnet.getPeregrino().getNombre());
			}
			return new SimpleStringProperty("");
		});

		tblCarnets.setPlaceholder(new Label("No hay carnets"));

		try {
			String nombreParada = paradaService.findByUsuario(sesion.getUsuarioActivo().getId()).getNombre();

			String[] nombresCarnets;

			nombresCarnets = existDBService.listarCarnets(nombreParada);

			List<Carnet> listaCarnets = new ArrayList<Carnet>();

			for (String nombre : nombresCarnets) {
				Carnet carnet = carnetService.getCarnetByUsuarioId(nombre);
				listaCarnets.add(carnet);
			}

			ObservableList<Carnet> listCarnets = FXCollections.observableArrayList(listaCarnets);
			tblCarnets.setItems(listCarnets);

		} catch (Exception e) {
			e.printStackTrace();
			alertas.alertaError("Error", "Error al obtener datos.");
		}

		ayuda.configImgInfo(hpInfo);
		botones.imgVolver(btnVolver);
		botones.imgSalir(btnSalir);

		mnemonicConfig.infoMnemonic(hpInfo);
		mnemonicConfig.volverMnemonic(btnVolver);
		mnemonicConfig.salirMnemonic(btnSalir);

		tooltipConfig.salirTooltip(btnSalir);
		tooltipConfig.volverTooltip(btnVolver);
		tooltipConfig.salirTooltip(btnSalir);
	}

	/**
	 * Muestra la ayuda relacionada con la lista de envíos.
	 *
	 * @param event Evento de acción disparado al hacer clic en el enlace de
	 *              información.
	 * @throws IOException Si ocurre un error al cargar el recurso de ayuda.
	 */
	@FXML
	private void handlerInfo(ActionEvent event) throws IOException {
//		Stage stage = (Stage) ((Hyperlink) event.getSource()).getScene().getWindow();
//		ayuda.configInfo("/help/listaEnvios.html", stage);
	}

	/**
	 * Regresa a la vista de la parada.
	 *
	 * @param event Evento de acción disparado al hacer clic en "Volver".
	 * @throws IOException Si ocurre un error al cambiar de escena.
	 */
	@FXML
	private void handlerVolver(ActionEvent event) throws IOException {
		stageManager.switchScene(FxmlView.PARADA);
	}

	/**
	 * Sale de la aplicación.
	 *
	 * @param event Evento de acción disparado al hacer clic en "Salir".
	 * @throws IOException Si ocurre un error durante la configuración de salida.
	 */
	@FXML
	private void handlerSalir(ActionEvent event) throws IOException {
		botones.salirConfig();
	}

}
