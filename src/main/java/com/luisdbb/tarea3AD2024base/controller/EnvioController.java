package com.luisdbb.tarea3AD2024base.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Controller;

import com.luisdbb.tarea3AD2024base.config.StageManager;
import com.luisdbb.tarea3AD2024base.modelo.Direccion;
import com.luisdbb.tarea3AD2024base.modelo.EnvioACasa;
import com.luisdbb.tarea3AD2024base.modelo.Sesion;
import com.luisdbb.tarea3AD2024base.services.EnvioService;
import com.luisdbb.tarea3AD2024base.services.ParadaService;
import com.luisdbb.tarea3AD2024base.services.Validaciones;
import com.luisdbb.tarea3AD2024base.view.FxmlView;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

@Controller
public class EnvioController implements Initializable {

	@FXML
	private Hyperlink hpInfo;

	@FXML
	private TextField txtDireccion;

	@FXML
	private TextField txtLocalidad;

	@FXML
	private CheckBox chUrgente;

	@FXML
	private TextField txtPeso;

	@FXML
	private TextField txtAlto;

	@FXML
	private TextField txtAncho;

	@FXML
	private TextField txtFondo;

	@FXML
	private Button btnEnviar;

	@FXML
	private Button btnVolver;

	@FXML
	private Label lblFeed;

	@FXML
	private Button btnSalir;

	@Lazy
	@Autowired
	private StageManager stageManager;

	@Autowired
	private Ayuda ayuda;

	@Autowired
	private Botones botones;

	@Autowired
	private Tooltips tooltipConfig;

	@Autowired
	private Mnemonic mnemonicConfig;

	@Autowired
	private Sesion sesion;

	@Autowired
	private ParadaService paradaService;

	@Autowired
	private EnvioService envioService;

	@Autowired
	private Alertas alertas;

	@Autowired
	private Validaciones validaciones;

	@Autowired
	private LabelFeed label;

	private final StringProperty direccionProperty = new SimpleStringProperty();
	private final StringProperty localidadProperty = new SimpleStringProperty();
	private final StringProperty pesoProperty = new SimpleStringProperty();
	private final StringProperty altoProperty = new SimpleStringProperty();
	private final StringProperty anchoProperty = new SimpleStringProperty();
	private final StringProperty fondoProperty = new SimpleStringProperty();

	private boolean direccionCheck = false;
	private boolean localidadCheck = false;
	private boolean pesoCheck = false;
	private boolean altoCheck = false;
	private boolean anchoCheck = false;
	private boolean fondoCheck = false;

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		validarEntradas();

		ayuda.configImgInfo(hpInfo);
		botones.imgFlecha(btnEnviar);
		botones.imgVolver(btnVolver);
		botones.imgSalir(btnSalir);

		mnemonicConfig.infoMnemonic(hpInfo);
		btnEnviar.addEventFilter(KeyEvent.KEY_PRESSED, event -> {
			if (event.isAltDown() && event.getCode() == KeyCode.ENTER) {
				btnEnviar.fire();
				event.consume();
			}
		});
		mnemonicConfig.volverMnemonic(btnVolver);
		mnemonicConfig.salirMnemonic(btnSalir);

		tooltipConfig.salirTooltip(btnSalir);
		btnEnviar.setTooltip(new Tooltip("Enviar (Alt+Enter)"));
		tooltipConfig.volverTooltip(btnVolver);
		tooltipConfig.salirTooltip(btnSalir);
	}

	@FXML
	private void handlerInfo(ActionEvent event) throws IOException {
		Stage stage = (Stage) ((Hyperlink) event.getSource()).getScene().getWindow();
		ayuda.configInfo("/help/envio.html", stage);
	}

	@FXML
	private void handlerEnviar(ActionEvent event) throws IOException {
		try {
			if (txtDireccion.getText().isEmpty() || txtLocalidad.getText().isEmpty() || txtAlto.getText().isEmpty()
					|| txtAncho.getText().isEmpty() || txtFondo.getText().isEmpty() || txtPeso.getText().isEmpty()) {

				alertas.alertaError("Error", "Los campos no deben estar vacíos.");
				return;
			}

			if (!direccionCheck) {
				alertas.alertaError("Error", "La dirección no es válida.");
				return;
			}

			if (!localidadCheck) {
				alertas.alertaError("Error", "La localidad no es válida.");
				return;
			}

			if (!pesoCheck) {
				alertas.alertaError("Error", "El peso no es válido.");
				return;
			}

			if (!altoCheck) {
				alertas.alertaError("Error", "El alto no es válido.");
				return;
			}

			if (!anchoCheck) {
				alertas.alertaError("Error", "El ancho no es válido.");
				return;
			}

			if (!fondoCheck) {
				alertas.alertaError("Error", "El fondo no es válido.");
				return;
			}

			long idParada = paradaService.findByUsuario(sesion.getUsuarioActivo().getId()).getId();

			Direccion direccion = new Direccion(txtDireccion.getText(), txtLocalidad.getText());

			double[] volumen = new double[3];
			volumen[0] = Double.parseDouble(txtAlto.getText());
			volumen[1] = Double.parseDouble(txtAncho.getText());
			volumen[2] = Double.parseDouble(txtFondo.getText());

			EnvioACasa envio = new EnvioACasa(Double.parseDouble(txtPeso.getText()), volumen, chUrgente.isSelected(),
					direccion, idParada);			
			
			EnvioACasa nuevoEnvio=envioService.saveEnvio(envio);
			
			if(nuevoEnvio!=null) {
				alertas.alertaInformacion("Envío a casa exitoso",
						"Se ha contratado el envío a casa correctamente.\nVolviendo al menú...");
				stageManager.switchScene(FxmlView.PARADA);				
			}else {
				alertas.alertaError("Error", "Error al guardar el envío, no se pudo guardar la información.");				
			}	

		} catch (NumberFormatException e) {
			e.printStackTrace();
			alertas.alertaError("Error", "Ingrese solo números en los campos de peso y volumen.");
		} catch (Exception e) {
			e.printStackTrace();
			alertas.alertaError("Error", "Error al guardar el envío, revise la información.");
		}

	}

	@FXML
	private void handlerVolver(ActionEvent event) throws IOException {
		stageManager.switchScene(FxmlView.ALOJAR);
	}

	@FXML
	private void handlerSalir(ActionEvent event) throws IOException {
		botones.salirConfig();
	}

	private void validarEntradas() {

		lblFeed.setText(" ");

		direccionProperty.bind(txtDireccion.textProperty());
		localidadProperty.bind(txtLocalidad.textProperty());
		pesoProperty.bind(txtPeso.textProperty());
		altoProperty.bind(txtAlto.textProperty());
		anchoProperty.bind(txtAncho.textProperty());
		fondoProperty.bind(txtFondo.textProperty());

		direccionProperty.addListener((observable, oldValue, newValue) -> {
			if (!newValue.isEmpty()) {
				if (!validaciones.esDireccion(newValue)) {
					label.mostrarTxtInvalido(lblFeed, "Dirección no acepta algún caracter introducido.");
					direccionCheck = false;
				} else {
					label.mostrarTxtValido(lblFeed, "Dirección válida");
					direccionCheck = true;
				}
			} else {
				lblFeed.setText(" ");
				direccionCheck = true;
			}
		});

		localidadProperty.addListener((observable, oldValue, newValue) -> {
			if (!newValue.isEmpty()) {
				if (!validaciones.validarNombreYApellidos(newValue)) {
					label.mostrarTxtInvalido(lblFeed, "Localidad acepta: letra y '-'");
					localidadCheck = false;
				} else {
					label.mostrarTxtValido(lblFeed, "Localidad válida");
					localidadCheck = true;
				}
			} else {
				lblFeed.setText(" ");
				localidadCheck = true;
			}
		});

		pesoProperty.addListener((observable, oldValue, newValue) -> {
			if (!newValue.isEmpty()) {
				if (!validaciones.esNumero(newValue)) {
					label.mostrarTxtInvalido(lblFeed, "El peso debe ser un número");
					pesoCheck = false;
				} else {
					label.mostrarTxtValido(lblFeed, "Peso válido");
					pesoCheck = true;
				}
			} else {
				lblFeed.setText(" ");
				pesoCheck = true;
			}
		});

		altoProperty.addListener((observable, oldValue, newValue) -> {
			if (!newValue.isEmpty()) {
				if (!validaciones.esNumero(newValue)) {
					label.mostrarTxtInvalido(lblFeed, "El alto debe ser un número");
					altoCheck = false;
				} else {
					label.mostrarTxtValido(lblFeed, "Alto válido");
					altoCheck = true;
				}
			} else {
				lblFeed.setText(" ");
				altoCheck = true;
			}
		});

		anchoProperty.addListener((observable, oldValue, newValue) -> {
			if (!newValue.isEmpty()) {
				if (!validaciones.esNumero(newValue)) {
					label.mostrarTxtInvalido(lblFeed, "El ancho debe ser un número");
					anchoCheck = false;
				} else {
					label.mostrarTxtValido(lblFeed, "Ancho válido");
					anchoCheck = true;
				}
			} else {
				lblFeed.setText(" ");
				anchoCheck = true;
			}
		});

		fondoProperty.addListener((observable, oldValue, newValue) -> {
			if (!newValue.isEmpty()) {
				if (!validaciones.esNumero(newValue)) {
					label.mostrarTxtInvalido(lblFeed, "El fondo debe ser un número");
					fondoCheck = false;
				} else {
					label.mostrarTxtValido(lblFeed, "Fondo válido");
					fondoCheck = true;
				}
			} else {
				lblFeed.setText(" ");
				fondoCheck = true;
			}
		});
	}
}
