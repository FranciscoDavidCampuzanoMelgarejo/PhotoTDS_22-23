package umu.tds.controlador;

import static org.junit.Assert.*;

import java.time.LocalDate;
import java.time.Month;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.BeforeClass;
import org.junit.Test;

import umu.tds.modelo.pojos.Album;
import umu.tds.modelo.pojos.Comentario;
import umu.tds.modelo.pojos.Foto;
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
	public void testRegistrar() {
		assertTrue(controlador.registrarUsuario("Jose Lopez Luan", "jose@um.es", "jose99", "1234", LocalDate.now(),
				"Hola me llamo Jose.", null));
	}

	@Test
	public void testRegistrarUsuarioExistente() {
		assertFalse(controlador.registrarUsuario("Juan Perez Saez", "jose@um.es", "juanit0", "1234", LocalDate.now(),
				"Hola a todos.", null));
		assertFalse(controlador.registrarUsuario("Juan Perez Saez", "juan@um,es", "jose99", "1234", LocalDate.now(),
				"Hola a todos.", null));
	}

	@Test
	public void testLogin() {
		assertTrue(controlador.loginUsuario("jose@um.es", "1234"));
		assertTrue(controlador.loginUsuario("jose99", "1234"));
	}

	@Test
	public void testLoginPasswordErronea() {
		assertFalse(controlador.loginUsuario("jose@um.es", "password"));
	}

	@Test
	public void testLoginUsuarioNoExistente() {
		assertFalse(controlador.loginUsuario("invent", "1234"));
	}

	@Test
	public void testEditarPerfil() {
		Usuario usuarioAntiguo = usuarioDAO.findBy(1);
		controlador.setUsuarioLogueado(usuarioAntiguo);
		controlador.editarPerfil("/ruta/hacia/foto", "He cambiado el perfil", null);
		Usuario usuarioNuevo = usuarioDAO.findBy(1);
		assertNotEquals(usuarioAntiguo, usuarioNuevo);
	}

	@Test
	public void testPublicarFoto() {
		Usuario usuario = usuarioDAO.findBy(1);
		controlador.setUsuarioLogueado(usuario);
		controlador.publicarFoto("ruta/foto", "Foto inicial", "Foto de un atardecer de verano",
				"Este es mi primer #comentario", Arrays.asList("comentario"));

		Publicacion publicacion = publicacionDAO.findAll().get(0);
		assertNotNull(publicacion);
		System.out.println(publicacion);
	}

	@Test
	public void testPublicarAlbum() {
		Usuario usuario = usuarioDAO.findBy(1);
		controlador.setUsuarioLogueado(usuario);
		Set<Foto> fotos = new HashSet<Foto>();
		Foto foto = (Foto) publicacionDAO.findBy(1);
		fotos.add(foto);
		controlador.publicarAlbum("Nuevo album", "fotos de animales", "Primer #comentario", Arrays.asList("comentario"),
				fotos);

		Album publicacion = (Album) publicacionDAO.findBy(2);
		assertNotNull(publicacion);
		//assertTrue(publicacion.getFotos().contains(foto));
		System.out.println(publicacion);
		publicacion.getFotos().stream().forEach(p -> System.out.println(p));

	}

}
