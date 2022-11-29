package umu.tds.controlador;

import static org.junit.Assert.*;

import java.time.LocalDate;
import java.util.List;

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
		// Loguearse
		assertTrue(controlador.loginUsuario("gema@um.es", "1234"));
		
		//Publicar foto
		controlador.publicarFoto("ruta/inventada/gema3", "Foto inicial de Gema3", "Foto del atardecer en Murcia", null);
		
		Usuario usuario = usuarioDAO.findBy(2);
		
		System.out.println("Numero de publicaciones: " + usuario.getPublicaciones().size());
		usuario.getPublicaciones().stream()
			.forEach((Publicacion p ) -> System.out.println(p));
		
		Publicacion p = publicacionDAO.findBy(12);
		System.out.println(p.getUsuario());
		
	}

}
