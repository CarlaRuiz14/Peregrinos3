package com.luisdbb.tarea3AD2024base.view;

import java.util.ResourceBundle;

/**
 * @author Carla Ruiz
 * @since 28/12/2024
 */
public enum FxmlView {
	USER {
		@Override
		public String getTitle() {
			return getStringFromResourceBundle("user.title");
		}

		@Override
		public String getFxmlFile() {
			return "/fxml/User.fxml";
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
