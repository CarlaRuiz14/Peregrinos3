package com.luisdbb.tarea3AD2024base.services;

import org.springframework.stereotype.Service;

import com.luisdbb.tarea3AD2024base.modelo.Perfil;
import com.luisdbb.tarea3AD2024base.modelo.Usuario;

import jakarta.annotation.PostConstruct;

@Service
public class AdminService {

    private final UsuarioService usuarioService;
   

    public AdminService(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
       
    }

    @PostConstruct // lo lanza despues del constructor de la aplicacion
    public void crearAdminSiNoExiste() {
        String nombre = "admin";
        String email ="admin@admin.com";
        String contraseña = "Admin_123";
        Perfil perfil = Perfil.ADMINISTRADOR;

        if (!usuarioService.existsByNombreUsuario(nombre)) {            
            Usuario admin = new Usuario(nombre,email,contraseña,perfil);            
            usuarioService.save(admin);
            System.out.println("Administrador creado.");
        } else {
            System.out.println("El administrador ya existe.");
        }
    }
}

