package com.luisdbb.tarea3AD2024base.services;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.luisdbb.tarea3AD2024base.modelo.Parada;
import com.luisdbb.tarea3AD2024base.modelo.Perfil;
import com.luisdbb.tarea3AD2024base.modelo.Sesion;
import com.luisdbb.tarea3AD2024base.modelo.Usuario;
import com.luisdbb.tarea3AD2024base.repositorios.UsuarioRepository;

import jakarta.transaction.Transactional;

/**
 * @author Carla Ruiz
 * @since 28/12/2024
 */
@Service
public class UsuarioService {

	@Autowired
	private UsuarioRepository usuarioRepository;

	@Autowired
	private ParadaService paradaService;

	@Autowired
	private PasswordService passwordService;

	@Autowired
	private Sesion sesion;

	public Usuario saveConPassword(Usuario entidad) {
		String passwordEncriptada = passwordService.encriptar(entidad.getContraseña());
		entidad.setContraseña(passwordEncriptada);
		return usuarioRepository.save(entidad);
	}

	public void actualizarDatosUsuario(Usuario usuarioActivo) {
		Usuario user = findById(usuarioActivo.getId());
		user.setEmail(usuarioActivo.getEmail());
		usuarioRepository.save(user);
	}

	public Usuario findByUsuario(String usuario) {
		return usuarioRepository.findByNombreUsuario(usuario);
	}

	public Usuario findById(long id) {
		return usuarioRepository.findById(id)
				.orElseThrow(() -> new RuntimeException("Usuario con ID " + id + " no encontrado"));
	}

	public boolean existsByNombreUsuario(String nombre) {
		return usuarioRepository.existsByNombreUsuario(nombre);
	}

	public Perfil loguear(String usuario, String contraseña) {
	    Perfil perfil = null;
	    boolean check = false;

	    Properties p = new Properties();

	    try (InputStream is = getClass().getClassLoader().getResourceAsStream("application.properties")) {
	       
	        p.load(is);
	        String user = p.getProperty("usuarioadmin");
	        String pass = p.getProperty("passadmin");

	        if (usuario.equalsIgnoreCase(user) && passwordService.verificar(contraseña, pass)) {
	            perfil = Perfil.ADMINISTRADOR;
	            check = true;
	        }
	    } catch (IOException e) {
	    	e.printStackTrace();
	    	return null;
	    }

	    if (!check) {
	        Usuario us = usuarioRepository.findByNombreUsuario(usuario);
	        if (us != null && passwordService.verificar(contraseña, us.getContraseña())) {
	            perfil = us.getPerfil();
	        }
	    }
	    return perfil;
	}


	public void configurarSesion(String usuario, Perfil perfil) {
		sesion.setUsuarioActivo(findByUsuario(usuario));
		sesion.setPerfilActivo(perfil);
	}

	@Transactional
	public void registrarUsuarioYParada(String usuario, String email, String contraseña, String nombreParada,
			char region) {

		Usuario user = new Usuario(usuario, email, contraseña, Perfil.PARADA);

		Parada parada = new Parada(nombreParada, region, user);

		this.saveConPassword(user);
		paradaService.save(parada);
	}
}
