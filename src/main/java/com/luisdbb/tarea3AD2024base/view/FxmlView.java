package com.luisdbb.tarea3AD2024base.view;

import java.util.ResourceBundle;

/**
 * Enumeración que representa las vistas disponibles en la aplicación.
 * 
 * <h2>Responsabilidades principales:</h2>
 * <ul>
 * <li>Proveer el título de la vista desde el archivo `Bundle.properties`.</li>
 * <li>Proveer la ruta del archivo FXML correspondiente a cada vista.</li>
 * </ul>
 * 
 * <h2>Constantes:</h2>
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
 * <li><b>VIP:</b> Vista para gestionar estancias VIP.</li>
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
	VIP {
		@Override
		public String getTitle() {
			return getStringFromResourceBundle("vip.title");
		}

		@Override
		public String getFxmlFile() {
			return "/fxml/Vip.fxml";
		}
	};

	// para asegurar consistencia, metodos abstractos
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
