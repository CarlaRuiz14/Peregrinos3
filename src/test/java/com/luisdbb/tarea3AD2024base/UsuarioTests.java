package com.luisdbb.tarea3AD2024base;

import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.luisdbb.tarea3AD2024base.modelo.Perfil;
import com.luisdbb.tarea3AD2024base.modelo.Usuario;
import com.luisdbb.tarea3AD2024base.repositorios.UsuarioRepository;
import com.luisdbb.tarea3AD2024base.services.PasswordService;
import com.luisdbb.tarea3AD2024base.services.UsuarioService;

class UsuarioTests {

	@Mock
	private UsuarioRepository usuarioRepository;

	@Mock
	private PasswordService passwordService;

	@InjectMocks
	private UsuarioService usuarioService;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	void testLoginAdminExitoso() {

		when(passwordService.verificar("admin", "$2b$12$hsIFnfqJ0IHddF9mSAXWCuUSKG6hO28Vb66aDi2BnFBrOFPgPxylW"))
				.thenReturn(true);

		Perfil resultado = usuarioService.loguear("admin", "admin");

		assertNotNull(resultado, "El login debería devolver un perfil válido");
		assertEquals(Perfil.ADMINISTRADOR, resultado, "El perfil debería ser ADMINISTRADOR");
	}

	@Test
	void testLoginPeregrinoExitoso() {
		Usuario usuarioMock = new Usuario();
		usuarioMock.setNombreUsuario("peregrino");
		usuarioMock.setContraseña("Moto_1990");
		usuarioMock.setPerfil(Perfil.PEREGRINO);

		when(usuarioRepository.findByNombreUsuario("peregrino")).thenReturn(usuarioMock);
		when(passwordService.verificar("Moto_1990", "Moto_1990")).thenReturn(true);

		Perfil resultado = usuarioService.loguear("peregrino", "Moto_1990");

		assertNotNull(resultado, "El login debería devolver un perfil válido");
		assertEquals(Perfil.PEREGRINO, resultado, "El perfil debería ser PEREGRINO");
	}

	@Test
	void testLoginParadaExitoso() {
		Usuario usuarioMock = new Usuario();
		usuarioMock.setNombreUsuario("parada");
		usuarioMock.setContraseña("Moto_1990");
		usuarioMock.setPerfil(Perfil.PARADA);

		when(usuarioRepository.findByNombreUsuario("parada")).thenReturn(usuarioMock);
		when(passwordService.verificar("Moto_1990", "Moto_1990")).thenReturn(true);

		Perfil resultado = usuarioService.loguear("parada", "Moto_1990");

		assertNotNull(resultado, "El login debería devolver un perfil válido");
		assertEquals(Perfil.PARADA, resultado, "El perfil debería ser PARADA");
	}

	@Test
	void testLoginFallidoPorUsuarioInexistente() {
		when(usuarioRepository.findByNombreUsuario("inexistente")).thenReturn(null);

		Perfil resultado = usuarioService.loguear("inexistente", "Moto_1990");

		assertNull(resultado, "El login debería fallar porque el usuario no existe");
	}

	@Test
	void testLoginFallidoPorContraseñaIncorrecta() {
		Usuario usuarioMock = new Usuario();
		usuarioMock.setNombreUsuario("admin");
		usuarioMock.setContraseña("12345");
		usuarioMock.setPerfil(Perfil.ADMINISTRADOR);

		when(usuarioRepository.findByNombreUsuario("admin")).thenReturn(usuarioMock);
		when(passwordService.verificar("wrongPassword", "12345")).thenReturn(false);

		Perfil resultado = usuarioService.loguear("admin", "wrongPassword");

		assertNull(resultado, "El login debería fallar si la contraseña es incorrecta");
	}
}
