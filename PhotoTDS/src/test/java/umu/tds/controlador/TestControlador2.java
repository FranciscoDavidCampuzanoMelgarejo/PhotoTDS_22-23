package umu.tds.controlador;

import static org.junit.Assert.*;

import java.time.LocalDate;

import org.junit.BeforeClass;
import org.junit.Test;

import umu.tds.modelo.pojos.Publicacion;
import umu.tds.modelo.pojos.Usuario;
import umu.tds.persistencia.IPublicacionDAO;
import umu.tds.persistencia.IUsuarioDAO;
import umu.tds.persistencia.PublicacionDAO;
import umu.tds.persistencia.UsuarioDAO;

public class TestControlador2 {
	
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
	public void testGeneral() {
		
		//Registrarse
		//assertTrue(controlador.registrarUsuario("Gema Ruiz Perez", "gema@um.es", "gemita-99", "1234", LocalDate.now(), "Hola me llamo Gema.", "ruta/to/Gema"));
		
		// Loguearse
		assertTrue(controlador.loginUsuario("gema@um.es", "1234"));
		
		//Publicar foto
		//controlador.publicarFoto("ruta/inventada", "Foto inicial", "Foto del atardecer en Murcia", null);
		
		
		Usuario usuario = usuarioDAO.findBy(2);
		
		System.out.println("Numero de publicaciones: " + usuario.getPublicaciones().size());
		usuario.getPublicaciones().stream()
			.forEach((Publicacion p ) -> System.out.println(p));
		
		Publicacion p = publicacionDAO.findBy(2);
		System.out.println(p.getUsuario());
		
		//Seguir a un usuario
		//controlador.seguir("gemita-99");
		
		/*
		Usuario usuario = usuarioDAO.findBy(2);
		
		System.out.println("Numero de seguidores: " + usuario.getSeguidores().size());
		usuario.getSeguidores().stream()
			.forEach((Usuario u) -> System.out.println(u));
		
		//Dejar de seguir
		controlador.dejarSeguir("gemita-99");
		
		usuario = usuarioDAO.findBy(2);
		
		System.out.println("Numero de seguidores: " + usuario.getSeguidores().size());
		usuario.getSeguidores().stream()
			.forEach((Usuario u) -> System.out.println(u));
		*/
		
		
	}

}
