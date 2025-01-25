package com.luisdbb.tarea3AD2024base.controller;

import org.springframework.stereotype.Component;

import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Tooltip;

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
