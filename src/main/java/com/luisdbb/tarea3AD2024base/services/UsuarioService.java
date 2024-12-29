package com.luisdbb.tarea3AD2024base.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.luisdbb.tarea3AD2024base.modelo.Usuario;
import com.luisdbb.tarea3AD2024base.repositorios.UsuarioRepository;

/**
 * @author Carla Ruiz
 * @since 28/12/2024
 */
@Service
public class UsuarioService {

	@Autowired
	private UsuarioRepository usuarioRepository;

	public Usuario save(Usuario entity) {
		return usuarioRepository.save(entity);
	}

	public Usuario update(Usuario entity) {
		return usuarioRepository.save(entity);
	}

	public void delete(Usuario entity) {
		usuarioRepository.delete(entity);
	}

	public void delete(Long id) {
		usuarioRepository.deleteById(id);
	}

	public Usuario find(Long id) {
		return usuarioRepository.findById(id).get();
	}

	public List<Usuario> findAll() {
		return usuarioRepository.findAll();
	}

	public boolean authenticate(String usuario, String contraseña) {
		Usuario user = this.findByUsuario(usuario);
		if (user == null) {
			return false;
		} else {
			if (contraseña.equals(user.getContraseña()))
				return true;
			else
				return false;
		}
	}

	public Usuario findByUsuario(String usuario) {
		return usuarioRepository.findByUsuario(usuario);
	}

	public void deleteInBatch(List<Usuario> users) {
		usuarioRepository.deleteAll(users);
	}

}
