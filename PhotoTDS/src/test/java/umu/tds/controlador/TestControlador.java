package umu.tds.controlador;

import static org.junit.Assert.*;

import java.time.LocalDate;
import java.time.Month;

import org.junit.BeforeClass;
import org.junit.Test;

public class TestControlador {

	private static Controlador controlador;

	@BeforeClass
	public static void setUp() {
		controlador = Controlador.getControlador();
	}

	@Test
	public void testRegistrarUsuarios() {
		assertTrue(controlador.registrarUsuario("Pepe Lopez Ruiz", "pepe@um.es", "pepit0-99", "1234",
				LocalDate.of(1999, Month.MAY, 26), "Hola a todos.", null));

		assertFalse(controlador.registrarUsuario("Gema Mengual Alcolea", "pepe@um.es", "G3m1t4", "1234",
				LocalDate.now(), null, null));

		assertFalse(controlador.registrarUsuario("Gema Mengual Alcolea", "gema@um.es", "pepit0-99", "1234",
				LocalDate.now(), null, null));

		assertTrue(controlador.registrarUsuario("Gema Mengual Alcolea", "gema@um.es", "G3m1t4", "1234", LocalDate.now(),
				null, null));
	}

	@Test
	public void testLoginUsuarios() {
		assertTrue(controlador.loginUsuario("pepe@um.es", "1234"));
		assertTrue(controlador.loginUsuario("pepit0-99", "1234"));
		assertFalse(controlador.loginUsuario("pepe@um.es", "password"));
		assertFalse(controlador.loginUsuario("anonim0", "anonimus"));
		assertTrue(controlador.loginUsuario("gema@um.es", "1234"));
		assertFalse(controlador.loginUsuario("G3m1t4", "#GEMA#"));
	}

}
