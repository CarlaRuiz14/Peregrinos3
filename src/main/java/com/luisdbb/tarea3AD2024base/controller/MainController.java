package com.luisdbb.tarea3AD2024base.controller;


import java.net.URL;
import java.util.ResourceBundle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Controller;

import com.luisdbb.tarea3AD2024base.config.StageManager;
import com.luisdbb.tarea3AD2024base.services.MainService;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

/**
 * @author Carla Ruiz
 * @since 28/12/2024
 */

@Controller
public class MainController implements Initializable{

	@FXML
    private Label lblEstela;

    @FXML
    private Button btnFlecha;

    @FXML
    private Button btnAdmin;

    @FXML
    private Button btnSalir;
    
    //inyecta autamaticamente los beans
    @Autowired
    private MainService mainService;
    
    //controla el cambio de escenas
    @Lazy //solo cuando sea necesario, no inmediatamente
    @Autowired
    private StageManager stageManager;        
	
	

    //automaticamente cuando se carga el fxml asociado
    //(ubicacion de fxml, recursos bundle)
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
	}

}
