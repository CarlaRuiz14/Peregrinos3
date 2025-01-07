package com.luisdbb.tarea3AD2024base.view;

import java.util.ResourceBundle;

/**
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
	},
	USER {
		@Override
		public String getTitle() {
			return getStringFromResourceBundle("user.title");
		}

		@Override
		public String getFxmlFile() {
			return "/fxml/User.fxml";
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
