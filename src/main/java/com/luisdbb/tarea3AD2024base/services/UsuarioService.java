package com.luisdbb.tarea3AD2024base.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.luisdbb.tarea3AD2024base.config.Perfil;
import com.luisdbb.tarea3AD2024base.modelo.Parada;
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

	public Usuario save(Usuario entidad) {
		return usuarioRepository.save(entidad);
	}
//
//	public Usuario update(Usuario entity) {
//		return usuarioRepository.save(entity);
//	}
//
//	public void delete(Usuario entity) {
//		usuarioRepository.delete(entity);
//	}
//
//	public void delete(Long id) {
//		usuarioRepository.deleteById(id);
//	}
//
//	public Usuario find(Long id) {
//		return usuarioRepository.findById(id).get();
//	}
//
//	public List<Usuario> findAll() {
//		return usuarioRepository.findAll();
//	}
//

	public Usuario findByUsuario(String usuario) {
		return usuarioRepository.findByNombreUsuario(usuario);
	}
//
//	public void deleteInBatch(List<Usuario> users) {
//		usuarioRepository.deleteAll(users);
//	}
	
	
	public Perfil loguear(String usuario, String contraseña) {
		Perfil perfil=null;;
		Usuario user=this.findByUsuario(usuario);
		if(user!=null && contraseña.equals(user.getContraseña())) {
			perfil=user.getPerfil();
		}		
		return perfil;
	}
	
	@Transactional
	public void registrarUsuarioParada(Usuario usuario, Parada parada) {
		this.save(usuario);
		paradaService.save(parada);
		
	}
	

}
