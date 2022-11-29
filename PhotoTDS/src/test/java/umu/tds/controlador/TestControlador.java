package umu.tds.controlador;

import static org.junit.Assert.*;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;

import org.junit.BeforeClass;
import org.junit.Test;

import umu.tds.modelo.pojos.Publicacion;
import umu.tds.modelo.pojos.Usuario;
import umu.tds.persistencia.IPublicacionDAO;
import umu.tds.persistencia.IUsuarioDAO;
import umu.tds.persistencia.PublicacionDAO;
import umu.tds.persistencia.UsuarioDAO;

public class TestControlador {

	private static Controlador controlador;
	private static IUsuarioDAO usuarioDAO;
	private static IPublicacionDAO publicacionDAO;

	@BeforeClass
	public static void setUp() {
		controlador = Controlador.getControlador();
		usuarioDAO = UsuarioDAO.getUsuarioDAO();
		publicacionDAO = PublicacionDAO.getPublicacionDAO();
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

	@Test
	public void testEditarPerfil() {
		controlador.editarPerfil(null, "Editar presentacion", "4321");
	}

	@Test
	public void testPublicarFoto() {
		List<Publicacion> publicaciones;
		Usuario usuarioPepe = usuarioDAO.findBy(1);
		publicaciones = usuarioPepe.getPublicaciones();
		publicaciones.stream()
			.forEach((Publicacion p) -> System.out.println(p.getTitulo()));
		controlador.setUsuario(usuarioPepe);
		controlador.publicarFoto("/ruta/a/foto", "Foto1", "Esto es mi primera foto", null);
		
		// Despues de publicar la foto
		System.out.println(usuarioPepe.getPublicaciones());
		
		Usuario nuevoPepe = usuarioDAO.findBy(1);
		publicaciones = nuevoPepe.getPublicaciones();
		publicaciones.stream()
			.forEach((Publicacion p) -> System.out.println(p.getTitulo()));
		 

	}

}
