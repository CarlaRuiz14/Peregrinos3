package com.luisdbb.tarea3AD2024base.controller;

import org.springframework.stereotype.Component;

import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Tooltip;

/**
 * Tooltips es un componente que configura los tooltips para diversos controles.
 * <ul>
 * <li>infoTooltip: Configura el tooltip para enlaces de información.</li>
 * <li>salirTooltip: Configura el tooltip para botones de salida.</li>
 * <li>volverTooltip: Configura el tooltip para botones de volver.</li>
 * <li>logoutTooltip: Configura el tooltip para botones de logout.</li>
 * <li>limpiarTooltip: Configura el tooltip para botones de limpiar.</li>
 * <li>visibleTooltip: Configura el tooltip para enlaces de visibilidad.</li>
 * <li>informeTooltip: Configura el tooltip para botones de informe.</li>
 * </ul>
 * 
 * @author
 * @since 28/12/2024
 */
@Component
public class Tooltips {

	public void infoTooltip(Hyperlink hp) {
		hp.setTooltip(new Tooltip("Información (F1)"));
	}

	public void salirTooltip(Button btn) {
		btn.setTooltip(new Tooltip("Salir (Alt+S)"));
	}

	public void volverTooltip(Button btn) {
		btn.setTooltip(new Tooltip("Volver (Alt+V)"));
	}

	public void logoutTooltip(Button btn) {
		btn.setTooltip(new Tooltip("Cerrar sesión (Alt+L)"));
	}

	public void limpiarTooltip(Button btn) {
		btn.setTooltip(new Tooltip("Limpiar (Alt+L)"));
	}

	public void visibleTooltip(Hyperlink hp) {
		hp.setTooltip(new Tooltip("Mostrar (Alt+M)"));
	}

	public void informeTooltip(Button btn) {
		btn.setTooltip(new Tooltip("Informe (Alt+I)"));
	}
}
