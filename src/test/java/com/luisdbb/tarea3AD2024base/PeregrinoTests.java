package com.luisdbb.tarea3AD2024base;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.luisdbb.tarea3AD2024base.modelo.Carnet;
import com.luisdbb.tarea3AD2024base.modelo.Parada;
import com.luisdbb.tarea3AD2024base.modelo.Peregrino;
import com.luisdbb.tarea3AD2024base.modelo.Perfil;
import com.luisdbb.tarea3AD2024base.modelo.Usuario;
import com.luisdbb.tarea3AD2024base.repositorios.PeregrinoRepository;
import com.luisdbb.tarea3AD2024base.services.PeregrinoService;
import com.luisdbb.tarea3AD2024base.services.UsuarioService;

import jakarta.transaction.Transactional;

class PeregrinoTests {

	@Mock
	private PeregrinoRepository peregrinoRepository;

	@Mock
	private UsuarioService usuarioService;

	@InjectMocks
	private PeregrinoService peregrinoService;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	@Transactional
	void testRegistroPeregrinoExitoso() {
		String usuario = "juan123";
		String email = "juan@gmail.com";
		String contraseña = "securePass";
		Parada paradaInicial = new Parada("Santiago", 'N',
				new Usuario("responsable", "resp@gmail.com", "password", Perfil.PEREGRINO));
		String nombrePer = "Juan";
		String apellidos = "Pérez";
		String nacionalidad = "Española";

		Usuario usuarioMock = new Usuario(usuario, email, contraseña, Perfil.PEREGRINO);
		Carnet carnetMock = new Carnet(paradaInicial);
		Peregrino peregrinoMock = new Peregrino(nombrePer, apellidos, nacionalidad, usuarioMock, carnetMock);

		when(usuarioService.saveConPassword(any(Usuario.class))).thenReturn(new Usuario());
		when(peregrinoRepository.save(any(Peregrino.class))).thenReturn(peregrinoMock);

		assertDoesNotThrow(() -> peregrinoService.registrarUsuarioCarnetYPeregrino(usuario, email, contraseña,
				paradaInicial, nombrePer, apellidos, nacionalidad));
	}

	@Test
	void testRegistroFallidoPorUsuarioExistente() {
		String usuario = "juan123";
		String email = "juan@gmail.com";
		String contraseña = "securePass";
		Parada paradaInicial = new Parada("Santiago", 'N',
				new Usuario("responsable", "resp@gmail.com", "password", Perfil.PEREGRINO));
		String nombrePer = "Juan";
		String apellidos = "Pérez";
		String nacionalidad = "Española";

		when(usuarioService.existsByNombreUsuario(usuario)).thenReturn(true);

		assertThrows(
				RuntimeException.class, () -> peregrinoService.registrarUsuarioCarnetYPeregrino(usuario, email,
						contraseña, paradaInicial, nombrePer, apellidos, nacionalidad),
				"Debe lanzar excepción si el usuario ya existe");
	}

	@Test
	void testActualizarPeregrinoExitoso() {
		Peregrino peregrinoMock = new Peregrino();
		peregrinoMock.setId(1L);
		peregrinoMock.setNombre("Juan");
		peregrinoMock.setApellidos("Pérez");
		peregrinoMock.setNacionalidad("Española");

		when(peregrinoRepository.save(any(Peregrino.class))).thenReturn(peregrinoMock);

		assertDoesNotThrow(() -> peregrinoService.actualizarDatosPeregrino(peregrinoMock));
	}

	@Test
	void testActualizarPeregrinoFallidoPorNulo() {
		assertThrows(RuntimeException.class, () -> peregrinoService.actualizarDatosPeregrino(null),
				"Debe lanzar excepción si el peregrino es nulo");
	}

}
