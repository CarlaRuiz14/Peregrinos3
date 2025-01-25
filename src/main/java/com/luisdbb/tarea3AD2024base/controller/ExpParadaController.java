package com.luisdbb.tarea3AD2024base.controller;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Controller;

import com.luisdbb.tarea3AD2024base.config.StageManager;
import com.luisdbb.tarea3AD2024base.modelo.Estancia;
import com.luisdbb.tarea3AD2024base.modelo.Parada;
import com.luisdbb.tarea3AD2024base.modelo.Sesion;
import com.luisdbb.tarea3AD2024base.services.EstanciaService;
import com.luisdbb.tarea3AD2024base.services.ParadaService;
import com.luisdbb.tarea3AD2024base.view.FxmlView;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Tooltip;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

/**
 * @author Carla Ruiz
 * @since 28/12/2024
 */
@Controller
public class ExpParadaController implements Initializable {

	@FXML
	private Hyperlink hpInfo;

	@FXML
	private Label lblIdPar;

	@FXML
	private Label lblNombrePar;

	@FXML
	private Label lblRegionPar;

	@FXML
	private Label lblIdResp;

	@FXML
	private Label lblUsuario;

	@FXML
	private Label lblEmail;

	@FXML
	private DatePicker dateFechaI;

	@FXML
	private DatePicker dateFechaF;

	@FXML
	private Button btnBuscar;

	@FXML
	private TableView<Estancia> tblEstancias;

	@FXML
	private TableColumn<Estancia, Long> colIdEstancia;

	@FXML
	private TableColumn<Estancia, String> colPeregrino;

	@FXML
	private TableColumn<Estancia, LocalDate> colFecha;

	@FXML
	private TableColumn<Estancia, Boolean> colVip;

	@FXML
	private Button btnInforme;

	@FXML
	private Button btnVolver;

	@FXML
	private Button btnSalir;

	@Lazy
	@Autowired
	private StageManager stageManager;

	@Autowired
	private Sesion sesion;

	@Autowired
	private Alertas alertas;

	@Autowired
	private Ayuda ayuda;

	@Autowired
	private Botones botones;

	@Autowired
	private ParadaService paradaService;

	@Autowired
	private EstanciaService estanciaService;
	
	@Autowired
	private Mnemonic mnemonicConfig;
	
	@Autowired
	private Tooltips tooltipConfig;	

	Parada parada;
	LocalDate fechaInicio;
	LocalDate fechaFin;

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		parada = paradaService.findByUsuario(sesion.getUsuarioActivo().getId());

		// cargar datos parada
		lblIdPar.setText(String.valueOf(parada.getId()));
		lblNombrePar.setText(parada.getNombre());
		lblRegionPar.setText(String.valueOf(parada.getRegion()));
		lblIdResp.setText(String.valueOf(sesion.getUsuarioActivo().getId()));
		lblUsuario.setText(sesion.getUsuarioActivo().getNombreUsuario());
		lblEmail.setText(sesion.getUsuarioActivo().getEmail());

		// config tabla estancias
		colIdEstancia.setCellValueFactory(new PropertyValueFactory<>("id"));
		colPeregrino.setCellValueFactory(new PropertyValueFactory<>("nombrePeregrino"));
		colFecha.setCellValueFactory(new PropertyValueFactory<>("fecha"));
		colVip.setCellValueFactory(new PropertyValueFactory<>("vip"));
		colVip.setCellFactory(tc -> new TableCell<Estancia, Boolean>() {
			@Override
			protected void updateItem(Boolean vip, boolean empty) {
				super.updateItem(vip, empty);
				if (empty || vip == null) {
					setText(null);
				} else {
					setText(vip ? "Sí" : "No");
				}
			}
		});

		// config img info
		ayuda.configImgInfo(hpInfo);

		// config img btn Buscar
		String rutaBuscar = resources.getString("btnBuscar.icon");
		Image imgBuscar = new Image(getClass().getResourceAsStream(rutaBuscar));
		ImageView viewBuscar = new ImageView(imgBuscar);
		viewBuscar.setFitWidth(25);
		viewBuscar.setFitHeight(25);
		btnBuscar.setGraphic(viewBuscar);

		// config img btn Informe
		String rutaInforme = resources.getString("btnInforme.icon");
		Image imgInforme = new Image(getClass().getResourceAsStream(rutaInforme));
		btnInforme.setGraphic(botones.createImageView(imgInforme));

		// config img btn Volver
		botones.imgVolver(btnVolver);

		// config img btn Salir
		botones.imgSalir(btnSalir);

		// mnemónicos
		mnemonicConfig.infoMnemonic(hpInfo);

		btnBuscar.addEventFilter(KeyEvent.KEY_PRESSED, event -> {
			if (event.isAltDown() && event.getCode() == KeyCode.ENTER) {
				btnBuscar.fire();
				event.consume();
			}
		});

		mnemonicConfig.informeMnemonic(btnInforme);


		mnemonicConfig.volverMnemonic(btnVolver);


		mnemonicConfig.salirMnemonic(btnSalir);


		// tooltips
		tooltipConfig.infoTooltip(hpInfo);
		btnBuscar.setTooltip(new Tooltip("Buscar (Alt+B)"));
		tooltipConfig.informeTooltip(btnInforme);
		tooltipConfig.volverTooltip(btnVolver);
		tooltipConfig.salirTooltip(btnSalir);
	}

	@FXML
	private void handlerInfo(ActionEvent event) throws IOException {
		ayuda.configInfo("/help/help.html");
	}

	@FXML
	private void handlerBuscar(ActionEvent event) throws IOException {

		fechaInicio = dateFechaI.getValue();
		fechaFin = dateFechaF.getValue();

		List<Estancia> estancias = estanciaService.getEstanciasForParada(parada.getId(), fechaInicio, fechaFin);

		if(estancias==null) {
			return;
		}
		
		
		if (estancias.isEmpty()) {
			alertas.alertaInformacion("Sin estancias",
					"No se encontraron estancias registradas entre las fechas seleccionadas.");
			tblEstancias.setItems(null);
			return;
		}
		
		ObservableList<Estancia> listEstancias = FXCollections.observableArrayList(estancias);

		tblEstancias.setItems(listEstancias);

	}

	@FXML
	private void handlerInforme(ActionEvent event) throws IOException {

	}

	@FXML
	private void handlerVolver(ActionEvent event) throws IOException {
		stageManager.switchScene(FxmlView.PARADA);
	}

	@FXML
	private void handlerSalir(ActionEvent event) throws IOException {
		botones.salirConfig();
		
		
		
		
	}
}
