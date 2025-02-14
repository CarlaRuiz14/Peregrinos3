package com.luisdbb.tarea3AD2024base.controller;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
import com.luisdbb.tarea3AD2024base.services.Validaciones;
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
import javafx.stage.Stage;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;

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

	@Autowired
	private Validaciones validaciones;

	private Parada parada;
	private LocalDate fechaInicio;
	private LocalDate fechaFin;
	private boolean checkBuscar = false;

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		parada = paradaService.findByUsuario(sesion.getUsuarioActivo().getId());

		lblIdPar.setText(String.valueOf(parada.getId()));
		lblNombrePar.setText(parada.getNombre());
		lblRegionPar.setText(String.valueOf(parada.getRegion()));
		lblIdResp.setText(String.valueOf(sesion.getUsuarioActivo().getId()));
		lblUsuario.setText(sesion.getUsuarioActivo().getNombreUsuario());
		lblEmail.setText(sesion.getUsuarioActivo().getEmail());

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

		tblEstancias.setPlaceholder(new Label("Estancias"));

		ayuda.configImgInfo(hpInfo);

		String rutaBuscar = resources.getString("btnBuscar.icon");
		Image imgBuscar = new Image(getClass().getResourceAsStream(rutaBuscar));
		ImageView viewBuscar = new ImageView(imgBuscar);
		viewBuscar.setFitWidth(25);
		viewBuscar.setFitHeight(25);
		btnBuscar.setGraphic(viewBuscar);

		String rutaInforme = resources.getString("btnInforme.icon");
		Image imgInforme = new Image(getClass().getResourceAsStream(rutaInforme));
		btnInforme.setGraphic(botones.createImageView(imgInforme));

		botones.imgVolver(btnVolver);
		botones.imgSalir(btnSalir);

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

		tooltipConfig.infoTooltip(hpInfo);
		btnBuscar.setTooltip(new Tooltip("Buscar (Alt+B)"));
		tooltipConfig.informeTooltip(btnInforme);
		tooltipConfig.volverTooltip(btnVolver);
		tooltipConfig.salirTooltip(btnSalir);
	}

	@FXML
	private void handlerInfo(ActionEvent event) throws IOException {
		Stage stage = (Stage) ((Hyperlink) event.getSource()).getScene().getWindow();
		ayuda.configInfo("/help/expParada.html", stage);
	}

	@FXML
	private void handlerBuscar(ActionEvent event) throws IOException {

		fechaInicio = dateFechaI.getValue();
		fechaFin = dateFechaF.getValue();

		int check = validaciones.validarFechas(fechaInicio, fechaFin);

		if (check != 0) {
			checkBuscar = false;

			switch (check) {

			case 1:
				alertas.alertaError("Error", "Debes seleccionar ambas fechas.");
				return;
			case 2:
				alertas.alertaError("Error", "La fecha de inicio debe ser anterior al día de hoy.");
				return;
			case 3:
				alertas.alertaError("Error", "La fecha de fin debe ser anterior al día de hoy.");
				return;
			case 4:
				alertas.alertaError("Error", "La fecha de inicio debe ser anterior a la fecha de fin.");
				return;
			}
		}

		List<Estancia> estancias = estanciaService.getEstanciasForParada(parada.getId(), fechaInicio, fechaFin);

		if (estancias == null) {
			checkBuscar = false;
			return;
		}

		if (estancias.isEmpty()) {
			alertas.alertaInformacion("Sin estancias", "No hay estancias registradas entre las fechas seleccionadas");
			tblEstancias.setItems(null);
			tblEstancias.setPlaceholder(new Label("Sin estancias registradas"));
			checkBuscar = false;
			return;
		}

		ObservableList<Estancia> listEstancias = FXCollections.observableArrayList(estancias);

		tblEstancias.setItems(listEstancias);
		checkBuscar = true;

	}

	@FXML
	private void handlerInforme(ActionEvent event) throws IOException {
		if (!checkBuscar) {
			alertas.alertaError("Error", "Por favor, introduzca fechas válidas para exportar las estancias");
			return;
		}

		try {
			InputStream jasperStream = getClass().getResourceAsStream("/reports/InformeEstancias.jasper");
			if (jasperStream == null) {
				alertas.alertaError("Error", "No se encontró el informe Jasper en el classpath.");
				return;
			}

			String outputPath = System.getProperty("user.home") + "/Desktop/Informe" + parada.getNombre() + ".pdf";

			Map<String, Object> parameters = new HashMap<>();
			parameters.put("PARADA", parada.getNombre());
			parameters.put("FECHA_INICIO", java.sql.Date.valueOf(fechaInicio));
			parameters.put("FECHA_FIN", java.sql.Date.valueOf(fechaFin));
			parameters.put("ID_PARADA", parada.getId());

			Connection connection = DriverManager.getConnection(
					"jdbc:mysql://localhost:3306/bdtarea3ad_carlaruiz?useSSL=false&serverTimezone=UTC", "root", "");

			JasperPrint jasperPrint = JasperFillManager.fillReport(jasperStream, parameters, connection);

			JasperExportManager.exportReportToPdfFile(jasperPrint, outputPath);

			connection.close();

			Boolean respuesta = alertas.alertaConfirmacion("Informe",
					"Su informe se ha exportado correctamente en:\n" + outputPath + "\n¿Quiere verlo en pantalla?");
			if (respuesta) {
				try {
					File pdfFile = new File(outputPath);
					if (pdfFile.exists()) {
						if (Desktop.isDesktopSupported()) {
							Desktop.getDesktop().open(pdfFile);
						} else {
							alertas.alertaError("Error", "La apertura de archivos no está soportada en este sistema.");
						}
					} else {
						alertas.alertaError("Error", "El archivo PDF no fue encontrado.");
					}
				} catch (IOException ex) {
					ex.printStackTrace();
					alertas.alertaError("Error", "Ocurrió un error al intentar abrir el PDF.");
				}

			} else {
				alertas.alertaInformacion("Informe", "Informe exportado. \nVolviendo...");
				stageManager.switchScene(FxmlView.EXPPARADA);
			}

		} catch (Exception e) {
			e.printStackTrace();
			alertas.alertaError("Error", "El informe no se ha podido exportar correctamente.");
			return;
		}

		checkBuscar = false;
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
