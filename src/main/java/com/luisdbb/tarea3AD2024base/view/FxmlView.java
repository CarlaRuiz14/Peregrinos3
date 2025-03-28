package com.luisdbb.tarea3AD2024base.view;

import java.util.ResourceBundle;

/**
 * Enumeración que representa las vistas disponibles en la aplicación.
 * 
 * Responsabilidades principales:
 * <ul>
 * <li>Proveer el título de la vista desde el archivo `Bundle.properties`.</li>
 * <li>Proveer la ruta del archivo FXML correspondiente a cada vista.</li>
 * </ul>
 * 
 * Constantes:
 * <ul>
 * <li><b>ADMIN:</b> Vista para la administración general.</li> *
 * <li><b>CARNET:</b> Vista para la gestión del carnet.</li>
 * <li><b>EXPPARADA:</b> Vista para exportar información de una parada.</li>
 * <li><b>LOGIN:</b> Vista para el inicio de sesión.</li>
 * <li><b>MAIN:</b> Vista principal de la aplicación.</li>
 * <li><b>PARADA:</b> Vista para gestionar paradas.</li>
 * <li><b>PEREGRINO:</b> Vista para gestionar peregrinos.</li>
 * <li><b>EDITAR:</b> Vista para editar información de usuario.</li>
 * <li><b>RECUPERACION:</b> Vista para recuperación de contraseñas.</li>
 * <li><b>REGPARADA:</b> Vista para registrar paradas.</li>
 * <li><b>REGPEREGRINO:</b> Vista para registrar peregrinos.</li>
 * <li><b>SELLAR:</b> Vista para sellar un carnet.</li>
 * <li><b>ALOJAR:</b> Vista para gestionar el alojamiento.</li>
 * <li><b>CONJUNTO:</b> Vista para contratación de servicios.</li>
 * <li><b>SERVICIO:</b> Vista para la creación o edición de servicios.</li>
 * <li><b>ENVIO:</b> Vista para la gestión de envíos.</li>
 * <li><b>LISTAENVIOS:</b> Vista para visualizar la lista de envíos.</li>
 * </ul>
 * 
 * @author Carla Ruiz
 * @since 28/12/2024
 */

public enum FxmlView {

	ADMIN {
		@Override
		public String getTitle() {
			return getStringFromResourceBundle("admin.title");
		}

		@Override
		public String getFxmlFile() {
			return "/fxml/Admin.fxml";
		}
	},
	CARNET {
		@Override
		public String getTitle() {
			return getStringFromResourceBundle("carnet.title");
		}

		@Override
		public String getFxmlFile() {
			return "/fxml/Carnet.fxml";
		}
	},
	EXPPARADA {
		@Override
		public String getTitle() {
			return getStringFromResourceBundle("expParada.title");
		}

		@Override
		public String getFxmlFile() {
			return "/fxml/ExpParada.fxml";
		}
	},
	LOGIN {
		@Override
		public String getTitle() {
			return getStringFromResourceBundle("login.title");
		}

		@Override
		public String getFxmlFile() {
			return "/fxml/Login.fxml";
		}
	},
	MAIN {
		@Override
		public String getTitle() {
			return getStringFromResourceBundle("main.title");
		}

		@Override
		public String getFxmlFile() {
			return "/fxml/Main.fxml";
		}
	},
	PARADA {
		@Override
		public String getTitle() {
			return getStringFromResourceBundle("parada.title");
		}

		@Override
		public String getFxmlFile() {
			return "/fxml/Parada.fxml";
		}
	},
	PEREGRINO {
		@Override
		public String getTitle() {
			return getStringFromResourceBundle("peregrino.title");
		}

		@Override
		public String getFxmlFile() {
			return "/fxml/Peregrino.fxml";
		}
	},
	EDITAR {
		@Override
		public String getTitle() {
			return getStringFromResourceBundle("editar.title");
		}

		@Override
		public String getFxmlFile() {
			return "/fxml/Editar.fxml";
		}
	},
	RECUPERACION {
		@Override
		public String getTitle() {
			return getStringFromResourceBundle("recuperacion.title");
		}

		@Override
		public String getFxmlFile() {
			return "/fxml/Recuperacion.fxml";
		}
	},
	REGPARADA {
		@Override
		public String getTitle() {
			return getStringFromResourceBundle("regParada.title");
		}

		@Override
		public String getFxmlFile() {
			return "/fxml/RegParada.fxml";
		}
	},
	REGPEREGRINO {
		@Override
		public String getTitle() {
			return getStringFromResourceBundle("regPeregrino.title");
		}

		@Override
		public String getFxmlFile() {
			return "/fxml/RegPeregrino.fxml";
		}
	},
	SELLAR {
		@Override
		public String getTitle() {
			return getStringFromResourceBundle("sellar.title");
		}

		@Override
		public String getFxmlFile() {
			return "/fxml/Sellar.fxml";
		}
	},
	ALOJAR {
		@Override
		public String getTitle() {
			return getStringFromResourceBundle("alojar.title");
		}

		@Override
		public String getFxmlFile() {
			return "/fxml/Alojar.fxml";
		}
	},
	CONJUNTO {
		@Override
		public String getTitle() {
			return getStringFromResourceBundle("conjunto.title");
		}

		@Override
		public String getFxmlFile() {
			return "/fxml/Conjunto.fxml";
		}
	},
	SERVICIO {
		@Override
		public String getTitle() {
			return getStringFromResourceBundle("servicio.title");
		}

		@Override
		public String getFxmlFile() {
			return "/fxml/Servicio.fxml";
		}
	},
	ENVIO {
		@Override
		public String getTitle() {
			return getStringFromResourceBundle("envio.title");
		}

		@Override
		public String getFxmlFile() {
			return "/fxml/envio.fxml";
		}
	},
	LISTAENVIOS {
		@Override
		public String getTitle() {
			return getStringFromResourceBundle("listaenvios.title");
		}

		@Override
		public String getFxmlFile() {
			return "/fxml/listaenvios.fxml";
		}
	},
	CARNETSLISTA {
		@Override
		public String getTitle() {
			return getStringFromResourceBundle("carnets.title");
		}

		@Override
		public String getFxmlFile() {
			return "/fxml/CarnetsLista.fxml";
		}
	};

	public abstract String getTitle();

	public abstract String getFxmlFile();

	/**
	 * Método que devuelve el título de Bundle.properties pasando como parámetro la
	 * key
	 * 
	 * @param key
	 * @return String
	 */
	String getStringFromResourceBundle(String key) {
		return ResourceBundle.getBundle("Bundle").getString(key);
	}
}
