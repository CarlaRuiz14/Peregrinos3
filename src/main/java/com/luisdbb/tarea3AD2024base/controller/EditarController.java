package com.luisdbb.tarea3AD2024base.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Controller;

import com.luisdbb.tarea3AD2024base.config.StageManager;
import com.luisdbb.tarea3AD2024base.modelo.Peregrino;
import com.luisdbb.tarea3AD2024base.modelo.Sesion;
import com.luisdbb.tarea3AD2024base.modelo.Usuario;
import com.luisdbb.tarea3AD2024base.services.NacionalidadService;
import com.luisdbb.tarea3AD2024base.services.PeregrinoService;
import com.luisdbb.tarea3AD2024base.services.UsuarioService;
import com.luisdbb.tarea3AD2024base.services.Validaciones;
import com.luisdbb.tarea3AD2024base.view.FxmlView;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

/**
 * Controlador para editar los datos del peregrino y usuario.
 * 
 * @author Carla Ruiz
 * @since 28/12/2024
 */
@Controller
public class EditarController implements Initializable {

	@FXML
	private Hyperlink hpInfo;

	@FXML
	private Label lblTitulo;

	@FXML
	private TextField txtNombre;

	@FXML
	private TextField txtApellidos;

	@FXML
	private TextField txtEmail;

	private final StringProperty emailProperty = new SimpleStringProperty();

	@FXML
	private ComboBox<String> cmbNacionalidad;

	ObservableList<String> listaNac;

	@FXML
	private Button btnLimpiar;

	@FXML
	private Button btnEditar;

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
	private Alertas alertas;

	@Autowired
	private Validaciones validaciones;

	@Autowired
	private Ayuda ayuda;

	@Autowired
	private NacionalidadService nacionalidadService;

	@Autowired
	private Sesion sesion;

	@Autowired
	private PeregrinoService peregrinoService;

	@Autowired
	private UsuarioService usuarioService;

	@Autowired
	private Botones botones;

	@Autowired
	private Mnemonic mnemonicConfig;

	@Autowired
	private Tooltips tooltipConfig;

	@Autowired
	private LabelFeed label;

	private Usuario usuarioActivo;
	private Peregrino peregrinoActivo;

	private boolean emailCheck = true;

	/**
	 * Inicializa el controlador.
	 * 
	 * Se realizan las siguientes acciones:
	 * <ul>
	 * <li>Cargar los datos del usuario y peregrino activo.</li>
	 * <li>Establecer los valores iniciales en los campos del formulario.</li>
	 * <li>Configurar el ComboBox con la lista de nacionalidades.</li>
	 * <li>Configurar imágenes, tooltips y atajos de teclado.</li>
	 * <li>Iniciar la validación de las entradas del formulario.</li>
	 * </ul>
	 *
	 * @param location  La URL utilizada para resolver el objeto raíz o null.
	 * @param resources Los recursos de localización o null.
	 */
	@Override
	public void initialize(URL location, ResourceBundle resources) {

		usuarioActivo = sesion.getUsuarioActivo();
		peregrinoActivo = peregrinoService.findByIdUsuario(usuarioActivo.getId());

		txtNombre.setText(peregrinoActivo.getNombre());
		txtApellidos.setText(peregrinoActivo.getApellidos());
		txtEmail.setText(usuarioActivo.getEmail());

		String nac = nacionalidadService.mapaNacionalidades().get(peregrinoActivo.getNacionalidad());
		cmbNacionalidad.getSelectionModel().select(nac);

		validarEntradas();

		List<String> listaValores = new ArrayList<>(nacionalidadService.mapaNacionalidades().values());
		listaNac = FXCollections.observableArrayList(listaValores);
		cmbNacionalidad.setItems(listaNac);

		ayuda.configImgInfo(hpInfo);
		botones.imgLimpiar(btnLimpiar);
		botones.imgFlecha(btnEditar);
		botones.imgVolver(btnVolver);
		botones.imgSalir(btnSalir);

		mnemonicConfig.infoMnemonic(hpInfo);
		mnemonicConfig.limpiarMnemonic(btnLimpiar);
		btnEditar.addEventFilter(KeyEvent.KEY_PRESSED, event -> {
			if (event.isAltDown() && event.getCode() == KeyCode.ENTER) {
				btnEditar.fire();
				event.consume();
			}
		});
		mnemonicConfig.volverMnemonic(btnVolver);
		mnemonicConfig.salirMnemonic(btnSalir);

		tooltipConfig.infoTooltip(hpInfo);
		tooltipConfig.limpiarTooltip(btnLimpiar);
		btnEditar.setTooltip(new Tooltip("Editar (Alt+Enter)"));
		tooltipConfig.volverTooltip(btnVolver);
		tooltipConfig.salirTooltip(btnSalir);
	}

	/**
	 * Muestra la ayuda relacionada con la edición.
	 *
	 * @param event Evento disparado al hacer clic en el enlace de información.
	 * @throws IOException Si ocurre un error al cargar el recurso de ayuda.
	 */
	@FXML
	private void handlerInfo(ActionEvent event) throws IOException {
		Stage stage = (Stage) ((Hyperlink) event.getSource()).getScene().getWindow();
		ayuda.configInfo("/help/editar.html", stage);
	}

	/**
	 * Limpia el formulario tras confirmar la acción.
	 *
	 * @param event Evento disparado al hacer clic en el botón de limpiar.
	 * @throws IOException Si ocurre un error durante la acción.
	 */
	@FXML
	private void handlerLimpiar(ActionEvent event) throws IOException {

		boolean confirmar = alertas.alertaConfirmacion("Borrado de formulario",
				"Se borrarán los datos introducidos, ¿está de acuerdo?");

		if (confirmar) {
			txtNombre.clear();
			txtApellidos.clear();
			txtEmail.clear();

			cmbNacionalidad.getSelectionModel().clearSelection();
			cmbNacionalidad.setValue(null);
			cmbNacionalidad.setPromptText("Nacionalidad");
			cmbNacionalidad.getParent().requestFocus();

		} else {
			alertas.alertaInformacion("Acción cancelada", "Por favor, rellene el formulario.");
		}
	}

	/**
	 * Actualiza los datos del peregrino y usuario tras validar la información.
	 *
	 * @param event Evento disparado al hacer clic en el botón de editar.
	 * @throws IOException Si ocurre un error durante la actualización.
	 */
	@FXML
	private void handlerEditar(ActionEvent event) throws IOException {

		if (!emailCheck) {
			alertas.alertaError("Error", "El email no es válido.");
			return;
		}

		if (!validarRegistro()) {
			return;
		}

		peregrinoActivo.setNombre(txtNombre.getText());
		peregrinoActivo.setApellidos(txtApellidos.getText());
		peregrinoActivo
				.setNacionalidad(nacionalidadService.obtenerNacionalidadSeleccionada(cmbNacionalidad.getValue()));
		peregrinoService.actualizarDatosPeregrino(peregrinoActivo);

		usuarioActivo.setEmail(txtEmail.getText());
		usuarioService.actualizarDatosUsuario(usuarioActivo);

		alertas.alertaInformacion("Actualización exitosa",
				"Los datos han sido guardados correctamente.\n\nVolviendo al menú.");
		stageManager.switchScene(FxmlView.PEREGRINO);
	}

	/**
	 * Regresa a la pantalla del menú principal.
	 *
	 * @param event Evento disparado al hacer clic en el botón de volver.
	 * @throws IOException Si ocurre un error al cambiar de escena.
	 */
	@FXML
	private void handlerVolver(ActionEvent event) throws IOException {
		stageManager.switchScene(FxmlView.PEREGRINO);
	}

	/**
	 * Sale de la aplicación.
	 *
	 * @param event Evento disparado al hacer clic en el botón de salir.
	 * @throws IOException Si ocurre un error durante la configuración de salida.
	 */
	@FXML
	private void handlerSalir(ActionEvent event) throws IOException {
		botones.salirConfig();
	}

	/**
	 * Configura la validación de las entradas del formulario.
	 * 
	 */
	private void validarEntradas() {
		lblFeed.setText(" ");

		emailProperty.bind(txtEmail.textProperty());

		emailProperty.addListener((observable, oldValue, newValue) -> {
			if (!newValue.isEmpty()) {
				if (!validaciones.validarEspacios(newValue)) {//
					label.mostrarTxtInvalido(lblFeed, "Email sin espacios en blanco");
					emailCheck = false;
				} else if (!validaciones.validarEmail(newValue)) {
					label.mostrarTxtInvalido(lblFeed, "Formato no válido. Formato email: __@__.__");
					emailCheck = false;
				} else {
					label.mostrarTxtValido(lblFeed, "Email válido.");
					emailCheck = true;
				}
			} else {
				lblFeed.setText(" ");
				emailCheck = true;

			}
		});
	}

	/**
	 * Valida que los campos obligatorios del formulario estén completos.
	 *
	 * @return true si la validación es exitosa, false en caso contrario.
	 */
	private boolean validarRegistro() {

		if (txtNombre.getText() == null || txtNombre.getText().isEmpty()) {
			alertas.alertaError("Error de validación", "El nombre de peregrino no puede estar vacío.");
			return false;
		}

		if (txtEmail.getText() == null || txtEmail.getText().isEmpty()) {
			alertas.alertaError("Error de validación", "El email no puede estar vacío.");
			return false;
		}

		return true;
	}
}
