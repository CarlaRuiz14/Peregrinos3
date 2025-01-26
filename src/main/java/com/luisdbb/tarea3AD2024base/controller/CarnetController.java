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
import com.luisdbb.tarea3AD2024base.modelo.Carnet;
import com.luisdbb.tarea3AD2024base.modelo.Estancia;
import com.luisdbb.tarea3AD2024base.modelo.Parada;
import com.luisdbb.tarea3AD2024base.modelo.Peregrino;
import com.luisdbb.tarea3AD2024base.modelo.Sesion;
import com.luisdbb.tarea3AD2024base.modelo.Usuario;
import com.luisdbb.tarea3AD2024base.services.CarnetService;
import com.luisdbb.tarea3AD2024base.services.EstanciaService;
import com.luisdbb.tarea3AD2024base.services.NacionalidadService;
import com.luisdbb.tarea3AD2024base.services.ParadaService;
import com.luisdbb.tarea3AD2024base.services.PeregrinoService;
import com.luisdbb.tarea3AD2024base.view.FxmlView;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
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
public class CarnetController implements Initializable {

	@FXML
	private Hyperlink hpInfo;

	@FXML
	private ImageView imgCarnet;

	@FXML
	private Label lblIdCarnet;

	@FXML
	private Label lblExpedicion;

	@FXML
	private Label lblIdPeregrino;

	@FXML
	private Label lblNombre;

	@FXML
	private Label lblApellidos;

	@FXML
	private Label lblNacionalidad;

	@FXML
	private Label lblDistancia;

	@FXML
	private Label lblVips;

	@FXML
	private TableView<Parada> tblParadas = new TableView<>();

	@FXML
	private TableColumn<Parada, Long> colIdParada;

	@FXML
	private TableColumn<Parada, String> colNombreParada;

	@FXML
	private TableColumn<Parada, Character> colRegion;

	@FXML
	private TableView<Estancia> tblEstancias = new TableView<>();

	@FXML
	private TableColumn<Estancia, Long> colIdEstancia;

	@FXML
	private TableColumn<Estancia, LocalDate> colFecha;

	@FXML
	private TableColumn<Estancia, Boolean> colVip;

	@FXML
	private Button btnInforme;

	@FXML
	private Button btnExportar;

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
	private Sesion sesion;

	@Autowired
	private Alertas alertas;

	@Autowired
	private PeregrinoService peregrinoService;

	@Autowired
	private ParadaService paradaService;

	@Autowired
	private CarnetService carnetService;

	@Autowired
	private EstanciaService estanciaService;

	@Autowired
	private NacionalidadService nacionalidadService;

	@Autowired
	private Mnemonic mnemonicConfig;

	@Autowired
	private Tooltips tooltipConfig;

	Usuario usuarioActivo;
	Peregrino peregrinoActivo;
	Carnet carnetActivo;

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		// sesion activa
		usuarioActivo = sesion.getUsuarioActivo();
		peregrinoActivo = peregrinoService.findByIdUsuario(usuarioActivo.getId());
		carnetActivo = carnetService.findById(peregrinoActivo.getCarnet().getId());

		// cargar datos
		lblIdCarnet.setText(String.valueOf(carnetActivo.getId()));
		lblExpedicion.setText(String.valueOf(carnetActivo.getFechaExp()));
		lblIdPeregrino.setText(String.valueOf(peregrinoActivo.getId()));
		lblNombre.setText(peregrinoActivo.getNombre());
		lblApellidos.setText(peregrinoActivo.getApellidos() != null ? peregrinoActivo.getApellidos() : "Sin apellidos");

		String claveNac = peregrinoActivo.getNacionalidad();
		String valorNac = nacionalidadService.mapaNacionalidades().get(claveNac);

		lblNacionalidad.setText(valorNac != null ? valorNac : "Sin nacionalidad");

		lblDistancia.setText(String.valueOf(carnetActivo.getDistancia()));
		lblVips.setText(String.valueOf(carnetActivo.getnVips()));

		// config tabla Paradas
		colIdParada.setCellValueFactory(new PropertyValueFactory<>("id"));
		colNombreParada.setCellValueFactory(new PropertyValueFactory<>("nombre"));
		colRegion.setCellValueFactory(new PropertyValueFactory<>("region"));

		List<Parada> lista = paradaService.obtenerParadasPorPeregrino(peregrinoActivo.getId());
		ObservableList<Parada> listParadas = FXCollections.observableArrayList(lista);
		tblParadas.setItems(listParadas);		
		tblParadas.setPlaceholder(new Label("Sin paradas"));

		
		// config tabla Estancias
		colIdEstancia.setCellValueFactory(new PropertyValueFactory<>("id"));
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
		List<Estancia> listaE = estanciaService.findByPeregrinoId(peregrinoActivo.getId());
		ObservableList<Estancia> listEstancias = FXCollections.observableArrayList(listaE);

		tblEstancias.setItems(listEstancias);
		tblEstancias.setPlaceholder(new Label("Sin estancias"));

		
		// config info
		ayuda.configImgInfo(hpInfo);

		// config img peregrino
		String rutaPer = resources.getString("carnet.icon");
		imgCarnet.setImage(new Image(getClass().getResourceAsStream(rutaPer)));

		// config img btn Informe
		String rutaInforme = resources.getString("btnInforme.icon");
		Image imgInforme = new Image(getClass().getResourceAsStream(rutaInforme));
		btnInforme.setGraphic(botones.createImageView(imgInforme));

		// config img btn Exportar
		botones.imgFlecha(btnExportar);

		// config img btn Volver
		botones.imgVolver(btnVolver);

		// config img btn Salir
		botones.imgSalir(btnSalir);

		// mnemónicos
		mnemonicConfig.infoMnemonic(hpInfo);

		mnemonicConfig.informeMnemonic(btnInforme);

		btnExportar.addEventFilter(KeyEvent.KEY_PRESSED, event -> {
			if (event.isAltDown() && event.getCode() == KeyCode.X) {
				btnExportar.fire();
				event.consume();
			}
		});

		mnemonicConfig.volverMnemonic(btnVolver);

		mnemonicConfig.salirMnemonic(btnSalir);

		// tooltips
		tooltipConfig.infoTooltip(hpInfo);
		tooltipConfig.informeTooltip(btnInforme);
		btnExportar.setTooltip(new Tooltip("Exportar (Alt+X)"));
		tooltipConfig.volverTooltip(btnVolver);
		tooltipConfig.salirTooltip(btnSalir);
	}

	@FXML
	private void handlerInfo(ActionEvent event) throws IOException {
		ayuda.configInfo("/help/expCarnet.html");
	}

	@FXML
	private void handlerInforme(ActionEvent event) throws IOException {

	}

	@FXML
	private void handlerExportar(ActionEvent event) throws IOException {
		carnetService.exportarCarnet(peregrinoActivo);
		alertas.alertaInformacion("Carnet exportado",
				"Su carnet ha sido exportado correctamente en formato xml.\n\nVolviendo al menú. ");
		stageManager.switchScene(FxmlView.PEREGRINO);
	}

	@FXML
	private void handlerVolver(ActionEvent event) throws IOException {
		stageManager.switchScene(FxmlView.PEREGRINO);
	}

	@FXML
	private void handlerSalir(ActionEvent event) throws IOException {
		botones.salirConfig();
	}
}
