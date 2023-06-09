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
	public void testGeneral1() {
		
		//Registrarse
		//assertTrue(controlador.registrarUsuario("Gema Ruiz Perez", "gema@um.es", "gemita-99", "1234", LocalDate.now(), "Hola me llamo Gema.", "ruta/to/Gema"));
		
		// Loguearse
		assertTrue(controlador.loginUsuario("Usuario_26", "aA1-"));
		
		//Publicar foto
		//controlador.publicarFoto("ruta/inventada", "Foto inicial", "Foto del atardecer en Murcia", null);
		
		
		Usuario usuario = controlador.getUsuarioLogueado();
		
		/*
		System.out.println("Numero de publicaciones: " + usuario.getPublicaciones().size());
		usuario.getPublicaciones().stream()
			.forEach((Publicacion p ) -> System.out.println(p));
		*/
		
		
		//Seguir a un usuario
		//controlador.seguir("jose99");
		//controlador.seguir("MelguiSama");
		
		//Loguearme con otro usuario
		assertTrue(controlador.loginUsuario("Anonimo", "aA1-"));
		usuario = controlador.getUsuarioLogueado();
		System.out.println(usuario.getUsuario());
		
		controlador.seguir("Usuario_26");
		
		
		
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
	
	@Test
	public void testGeneralPublicacion() {
		assertTrue(controlador.loginUsuario("Usuario_26", "cC1-"));
		
		Usuario usuario = controlador.getUsuarioLogueado();
		
		//controlador.publicarFoto("C:\\Users\\franc\\OneDrive\\Imágenes\\pepoG.jpg", "Tomando apuntes", "Emoticono de la rana Pepe tomando apunte", null, null);
		//controlador.publicarFoto("C:\\Users\\franc\\OneDrive\\Imágenes\\Algo.png", "Impresionado", "Emoticono de la rana Pepe impresionado", null, null);
		//controlador.publicarFoto("C:\\Users\\franc\\OneDrive\\Imágenes\\based.jpg", "BASED", "Basado", null, null);
		controlador.publicarFoto("C:\\Users\\franc\\OneDrive\\Imágenes\\booba.jpg", "BOOBA", "Ojo saltones", null, null);
		controlador.publicarFoto("C:\\Users\\franc\\OneDrive\\Imágenes\\copium.jpg", "COPIUM", "Anestesiado", null, null);
		controlador.publicarFoto("C:\\Users\\franc\\OneDrive\\Imágenes\\peepoHey.png", "SALUDO", "Emote de saludo", null, null);
		controlador.publicarFoto("C:\\Users\\franc\\OneDrive\\Imágenes\\sadge.png", "SAD", "Emote triste", null, null);
		controlador.publicarFoto("C:\\Users\\franc\\OneDrive\\Imágenes\\monkas.jpg", "MONKAS", "Sudando",null, null);
		controlador.publicarFoto("C:\\Users\\franc\\OneDrive\\Imágenes\\pepoG.jpg", "PepoG", "Tomando notas", null, null);
		
		// Otras 6 fotos
		controlador.publicarFoto("C:\\Users\\franc\\OneDrive\\Imágenes\\booba.jpg", "BOOBA", "Ojo saltones", null, null);
		controlador.publicarFoto("C:\\Users\\franc\\OneDrive\\Imágenes\\copium.jpg", "COPIUM", "Anestesiado", null, null);
		controlador.publicarFoto("C:\\Users\\franc\\OneDrive\\Imágenes\\peepoHey.png", "SALUDO", "Emote de saludo", null, null);
		controlador.publicarFoto("C:\\Users\\franc\\OneDrive\\Imágenes\\sadge.png", "SAD", "Emote triste", null, null);
		controlador.publicarFoto("C:\\Users\\franc\\OneDrive\\Imágenes\\monkas.jpg", "MONKAS", "Sudando",null, null);
		controlador.publicarFoto("C:\\Users\\franc\\OneDrive\\Imágenes\\pepoG.jpg", "PepoG", "Tomando notas", null, null);
		
		// Otras 6 fotos
		controlador.publicarFoto("C:\\Users\\franc\\OneDrive\\Imágenes\\booba.jpg", "BOOBA", "Ojo saltones", null, null);
		controlador.publicarFoto("C:\\Users\\franc\\OneDrive\\Imágenes\\copium.jpg", "COPIUM", "Anestesiado", null, null);
		controlador.publicarFoto("C:\\Users\\franc\\OneDrive\\Imágenes\\peepoHey.png", "SALUDO", "Emote de saludo", null, null);
		controlador.publicarFoto("C:\\Users\\franc\\OneDrive\\Imágenes\\sadge.png", "SAD", "Emote triste", null, null);
		controlador.publicarFoto("C:\\Users\\franc\\OneDrive\\Imágenes\\monkas.jpg", "MONKAS", "Sudando",null, null);
		controlador.publicarFoto("C:\\Users\\franc\\OneDrive\\Imágenes\\pepoG.jpg", "PepoG", "Tomando notas", null, null);
		
		// Otras 6 fotos
		controlador.publicarFoto("C:\\Users\\franc\\OneDrive\\Imágenes\\booba.jpg", "BOOBA", "Ojo saltones", null, null);
		controlador.publicarFoto("C:\\Users\\franc\\OneDrive\\Imágenes\\copium.jpg", "COPIUM", "Anestesiado", null, null);
		controlador.publicarFoto("C:\\Users\\franc\\OneDrive\\Imágenes\\peepoHey.png", "SALUDO", "Emote de saludo", null, null);
		controlador.publicarFoto("C:\\Users\\franc\\OneDrive\\Imágenes\\sadge.png", "SAD", "Emote triste", null, null);
		controlador.publicarFoto("C:\\Users\\franc\\OneDrive\\Imágenes\\monkas.jpg", "MONKAS", "Sudando",null, null);
		controlador.publicarFoto("C:\\Users\\franc\\OneDrive\\Imágenes\\pepoG.jpg", "PepoG", "Tomando notas", null, null);
		
	}

}
