package com.luisdbb.tarea3AD2024base.controller;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.sql.Connection;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import javax.sql.DataSource;

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
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
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
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperPrintManager;

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

	@Autowired
	private DataSource dataSource;

	private Usuario usuarioActivo;
	private Peregrino peregrinoActivo;
	private Carnet carnetActivo;
	private ResourceBundle bundle;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {

		this.bundle=resources;
		
		usuarioActivo = sesion.getUsuarioActivo();
		peregrinoActivo = peregrinoService.findByIdUsuario(usuarioActivo.getId());
		carnetActivo = carnetService.findById(peregrinoActivo.getCarnet().getId());

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

		colIdParada.setCellValueFactory(new PropertyValueFactory<>("id"));
		colNombreParada.setCellValueFactory(new PropertyValueFactory<>("nombre"));
		colRegion.setCellValueFactory(new PropertyValueFactory<>("region"));

		List<Parada> lista = paradaService.obtenerParadasPorPeregrino(peregrinoActivo.getId());
		ObservableList<Parada> listParadas = FXCollections.observableArrayList(lista);
		tblParadas.setItems(listParadas);
		tblParadas.setPlaceholder(new Label("Sin paradas"));

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

		ayuda.configImgInfo(hpInfo);

		String rutaPer = resources.getString("carnet.icon");
		imgCarnet.setImage(new Image(getClass().getResourceAsStream(rutaPer)));

		String rutaInforme = resources.getString("btnInforme.icon");
		Image imgInforme = new Image(getClass().getResourceAsStream(rutaInforme));
		btnInforme.setGraphic(botones.createImageView(imgInforme));

		botones.imgFlecha(btnExportar);
		botones.imgVolver(btnVolver);
		botones.imgSalir(btnSalir);

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

		tooltipConfig.infoTooltip(hpInfo);
		tooltipConfig.informeTooltip(btnInforme);
		btnExportar.setTooltip(new Tooltip("Exportar (Alt+X)"));
		tooltipConfig.volverTooltip(btnVolver);
		tooltipConfig.salirTooltip(btnSalir);
	}

	@FXML
	private void handlerInfo(ActionEvent event) throws IOException {
		Stage stage = (Stage) ((Hyperlink) event.getSource()).getScene().getWindow();
		ayuda.configInfo("/help/expCarnet.html", stage);
	}

	@FXML
	private void handlerInforme(ActionEvent event) {
		try {
			InputStream reportStream = getClass().getResourceAsStream("/reports/InformeCarnet.jasper");
			if (reportStream == null) {
				alertas.alertaError("Error", "No se encontró el archivo del informe.");
				return;
			}

			Map<String, Object> parametros = new HashMap<>();
			parametros.put("id_peregrino", peregrinoActivo.getId());
			parametros.put("imagen_fondo", "images/fondo.png");


			try (Connection conexion = dataSource.getConnection()) {
				JasperPrint jasperPrint = JasperFillManager.fillReport(reportStream, parametros, conexion);
				crearImagenInforme(jasperPrint);
			}
		} catch (Exception e) {
			e.printStackTrace();
			alertas.alertaError("Error", "No se pudo generar el informe");
		}
	}
	
	private void crearImagenInforme(JasperPrint jasperPrint) {
	    try {
	        java.awt.Image awtImage = JasperPrintManager.printPageToImage(jasperPrint, 0, 1.0f);
	        BufferedImage bufferedImage = new BufferedImage(
	            awtImage.getWidth(null),
	            awtImage.getHeight(null),
	            BufferedImage.TYPE_INT_ARGB
	        );
	        Graphics2D g2d = bufferedImage.createGraphics();
	        g2d.drawImage(awtImage, 0, 0, null);
	        g2d.dispose();

	        javafx.scene.image.Image fxImage = SwingFXUtils.toFXImage(bufferedImage, null);
	        ImageView imageView = new ImageView(fxImage);
	        imageView.setPreserveRatio(true);
	        imageView.setSmooth(true);

	        BorderPane pane = new BorderPane(imageView);
	        Scene scene = new Scene(pane, 540, 540);

	        Stage reportStage = new Stage();
	        reportStage.setTitle("Carnet de peregrino");
	        String iconPath = bundle.getString("carnet.icon");
	        reportStage.getIcons().add(new Image(getClass().getResourceAsStream(iconPath)));	        
	        reportStage.initModality(javafx.stage.Modality.APPLICATION_MODAL);
	        
	        reportStage.setScene(scene);
	        reportStage.showAndWait(); 
	    } catch (JRException e) {
	        e.printStackTrace();
	        alertas.alertaError("Error", "Error al cargar el informe");
	    }
	}

	
	
	@FXML
	private void handlerExportar(ActionEvent event) throws IOException {

		int checkCarnet = carnetService.exportarCarnet(peregrinoActivo);

		switch (checkCarnet) {

		case 0 -> {
			alertas.alertaInformacion("Carnet exportado",
					"Su carnet ha sido exportado correctamente en formato xml.\n\nVolviendo al menú. ");
			stageManager.switchScene(FxmlView.PEREGRINO);
			break;
		}
		case 1 -> {
			alertas.alertaError("Error", "Error en la configuración del parser XML");
			break;
		}
		case 2 -> {
			alertas.alertaError("Error", "Error en la configuración del transformador XML");
			break;
		}
		case 3 -> {
			alertas.alertaError("Error", "Error al transformar el documento XML");
			break;
		}
		case 4 -> {
			alertas.alertaError("Error", "Error inesperado: se intentó acceder a un objeto nulo");
			break;
		}
		}
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
