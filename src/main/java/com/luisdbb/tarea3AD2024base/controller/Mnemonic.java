package com.luisdbb.tarea3AD2024base.controller;

import org.springframework.stereotype.Component;

import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

@Component
public class Mnemonic {
	
	public void infoMnemonic(Hyperlink hp) {		
		hp.addEventFilter(KeyEvent.KEY_PRESSED, event -> {
			if (event.getCode() == KeyCode.F1) {
				hp.fire();
				event.consume();
			}
		});		
	}

	
	public void logoutMnemonic(Button btn) {
		btn.addEventFilter(KeyEvent.KEY_PRESSED, event -> {
			if (event.isAltDown() && event.getCode() == KeyCode.L) {
				btn.fire();
				event.consume();
			}
		});
	}
	
	public void salirMnemonic(Button btn) {		
		btn.addEventFilter(KeyEvent.KEY_PRESSED, event -> {
			if (event.isControlDown() && event.getCode() == KeyCode.S) {
				btn.fire();
				event.consume();
			}
		});
	}
	
	public void volverMnemonic(Button btn) {		
		btn.addEventFilter(KeyEvent.KEY_PRESSED, event -> {
			if (event.isAltDown() && event.getCode() == KeyCode.V) {
				btn.fire();
				event.consume();
			}
		});
	}
	
	public void informeMnemonic(Button btn) {
	
		btn.addEventFilter(KeyEvent.KEY_PRESSED, event -> {
			if (event.isAltDown() && event.getCode() == KeyCode.I) {
				btn.fire();
				event.consume();
			}
		});
	}
	
	public void limpiarMnemonic(Button btn) {
		
		btn.addEventFilter(KeyEvent.KEY_PRESSED, event -> {
			if (event.isAltDown() && event.getCode() == KeyCode.L) {
				btn.fire();
				event.consume();
			}
		});
		
	}
	
	public void visibleMnemonic(Hyperlink hp) {
		
		hp.addEventFilter(KeyEvent.KEY_PRESSED, event -> {
			if (event.isAltDown() && event.getCode() == KeyCode.M) {
				hp.fire();
				event.consume();
			}
		});
	}
	

	
	
	
}
