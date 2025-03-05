package com.luisdbb.tarea3AD2024base;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.luisdbb.tarea3AD2024base.modelo.Carnet;
import com.luisdbb.tarea3AD2024base.modelo.Parada;
import com.luisdbb.tarea3AD2024base.modelo.ParadasPeregrino;
import com.luisdbb.tarea3AD2024base.modelo.Peregrino;
import com.luisdbb.tarea3AD2024base.modelo.Usuario;
import com.luisdbb.tarea3AD2024base.repositorios.ParadaRepository;
import com.luisdbb.tarea3AD2024base.repositorios.ParadasPeregrinoRepository;
import com.luisdbb.tarea3AD2024base.services.CarnetService;
import com.luisdbb.tarea3AD2024base.services.ParadaService;
import com.luisdbb.tarea3AD2024base.services.ParadasPeregrinoService;
import com.luisdbb.tarea3AD2024base.services.PeregrinoService;

class ParadaTests {

	@Mock
	private ParadasPeregrinoRepository paradasPeregrinoRepository;

	@Mock
	private CarnetService carnetService;

	@Mock
	private PeregrinoService peregrinoService;

	@InjectMocks
	private ParadasPeregrinoService paradasPeregrinoService;

	@Mock
	private ParadaRepository paradaRepository;

	@InjectMocks
	private ParadaService paradaService;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	void testSelladoExitoso() {
		Peregrino peregrinoMock = new Peregrino();
		peregrinoMock.setId(1L);

		Parada paradaMock = new Parada();
		paradaMock.setId(1L);

		Carnet carnetMock = new Carnet(paradaMock);
		carnetMock.setDistancia(30.0);

		when(peregrinoService.findByIdUsuario(1L)).thenReturn(peregrinoMock);
		when(carnetService.save(any(Carnet.class))).thenReturn(carnetMock);
		when(paradasPeregrinoRepository.save(any(ParadasPeregrino.class))).thenReturn(new ParadasPeregrino());

		assertDoesNotThrow(
				() -> paradasPeregrinoService.registrarParadaYSellarCarnet(carnetMock, paradaMock, peregrinoMock));
	}

	@Test
	void testSelladoPeregrinoNoRegistrado() {
		when(peregrinoService.findByIdUsuario(1L)).thenReturn(null);

		assertThrows(
				RuntimeException.class, () -> paradasPeregrinoService
						.registrarParadaYSellarCarnet(new Carnet(new Parada()), new Parada(), null),
				"Debe lanzar una excepción si el peregrino no existe.");
	}

	@Test
	void testSelladoParadaInexistente() {
		Peregrino peregrinoMock = new Peregrino();
		peregrinoMock.setId(1L);

		when(peregrinoService.findByIdUsuario(1L)).thenReturn(peregrinoMock);

		assertThrows(RuntimeException.class,
				() -> paradasPeregrinoService.registrarParadaYSellarCarnet(new Carnet(null), null, peregrinoMock),
				"Debe lanzar una excepción si la parada no existe.");
	}

	@Test
	void testRegistroParadaExitoso() {
		Usuario usuarioMock = new Usuario("responsable", "resp@gmail.com", "password", null);
		Parada paradaMock = new Parada("Santiago", 'N', usuarioMock);

		when(paradaRepository.save(any(Parada.class))).thenReturn(paradaMock);

		assertDoesNotThrow(() -> paradaService.save(paradaMock),
				"El registro de la parada debería ser exitoso sin lanzar excepciones.");
	}

	@Test
	void testRegistroParadaDuplicadaMismaRegion() {
		Usuario usuarioMock = new Usuario("responsable", "resp@gmail.com", "password", null);
		Parada paradaMock = new Parada("Santiago", 'N', usuarioMock);

		when(paradaRepository.findByNombre("Santiago")).thenReturn(paradaMock);

		boolean existe = paradaService.existeParada("Santiago", 'N');

		assertTrue(existe, "Debe indicar que la parada ya existe en la misma región.");
	}

}
