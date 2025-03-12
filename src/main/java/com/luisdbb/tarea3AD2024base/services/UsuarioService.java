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
 * Servicio para la gestión de usuarios en la aplicación.
 * 
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
	private ExistDBService existDBService;

	@Autowired
	private Sesion sesion;

	/**
	 * Guarda un usuario en la base de datos con la contraseña encriptada.
	 * 
	 * @param entidad Usuario a guardar.
	 * @return Usuario guardado o {@code null} si no se guarda correctamente.
	 */
	public Usuario saveConPassword(Usuario entidad) {
		String passwordEncriptada = passwordService.encriptar(entidad.getContraseña());
		entidad.setContraseña(passwordEncriptada);
		return usuarioRepository.save(entidad);
	}

	/**
	 * Actualiza los datos de un usuario en la base de datos.
	 * 
	 * @param usuarioActivo Usuario con los datos actualizados.
	 */
	public void actualizarDatosUsuario(Usuario usuarioActivo) {
		Usuario user = findById(usuarioActivo.getId());
		user.setEmail(usuarioActivo.getEmail());
		usuarioRepository.save(user);
	}

	/**
	 * Busca un usuario por su nombre de usuario.
	 * 
	 * @param usuario Nombre de usuario a buscar.
	 * @return Usuario encontrado o {@code null} si no existe.
	 */
	public Usuario findByUsuario(String usuario) {
		return usuarioRepository.findByNombreUsuario(usuario);
	}

	/**
	 * Busca un usuario por su ID.
	 * 
	 * @param id Identificador del usuario.
	 * @return Usuario encontrado o lanza una excepción si no existe.
	 * @throws RuntimeException Si no se encuentra el usuario con el ID dado.
	 */
	public Usuario findById(long id) {
		return usuarioRepository.findById(id)
				.orElseThrow(() -> new RuntimeException("Usuario con ID " + id + " no encontrado"));
	}

	/**
	 * Verifica si un nombre de usuario ya está registrado en la base de datos.
	 * 
	 * @param nombre Nombre de usuario a verificar.
	 * @return {@code true} si el usuario existe, {@code false} en caso contrario.
	 */
	public boolean existsByNombreUsuario(String nombre) {
		return usuarioRepository.existsByNombreUsuario(nombre);
	}

	/**
	 * Realiza el proceso de autenticación de un usuario.
	 * 
	 * <ul>
	 * <li>Si el usuario es administrador, se compara con las credenciales en
	 * "application.properties".</li>
	 * <li>Si no es administrador, se busca en la base de datos.</li>
	 * </ul>
	 * 
	 * @param usuario    Nombre de usuario.
	 * @param contraseña Contraseña del usuario.
	 * @return Perfil del usuario autenticado o {@code null} si las credenciales no
	 *         son válidas.
	 */
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

	/**
	 * Configura la sesión activa del usuario autenticado.
	 * 
	 * @param usuario Nombre de usuario autenticado.
	 * @param perfil  Perfil del usuario autenticado.
	 */
	public void configurarSesion(String usuario, Perfil perfil) {
		sesion.setUsuarioActivo(findByUsuario(usuario));
		sesion.setPerfilActivo(perfil);
	}

	/**
	 * Registra un usuario con perfil de parada, la parada y su coleccion en existDB
	 * para los carnets
	 * 
	 * @param usuario      Nombre de usuario.
	 * @param email        Correo electrónico del usuario.
	 * @param contraseña   Contraseña del usuario.
	 * @param nombreParada Nombre de la parada asociada.
	 * @param region       Código de la región de la parada.
	 * @throws Exception Si ocurre un error en la creación de la colección en
	 *                   eXistDB o en el registro de usuario/parada.
	 */
	@Transactional
	public void registrarUsuarioParadaColeccion(String usuario, String email, String contraseña, String nombreParada,
			char region) throws Exception {

		Usuario user = new Usuario(usuario, email, contraseña, Perfil.PARADA);

		Parada parada = new Parada(nombreParada, region, user);

		existDBService.crearColeccionEnParadas(nombreParada);
		this.saveConPassword(user);
		paradaService.save(parada);
	}
}
