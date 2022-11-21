package umu.tds.controlador;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.function.Predicate;

import umu.tds.modelo.catalogos.CatalogoPublicaciones;
import umu.tds.modelo.catalogos.CatalogoUsuarios;
import umu.tds.modelo.pojos.Foto;
import umu.tds.modelo.pojos.PerfilUsuario;
import umu.tds.modelo.pojos.Usuario;
import umu.tds.persistencia.FactoriaDAO;
import umu.tds.persistencia.IComentarioDAO;
import umu.tds.persistencia.IPublicacionDAO;
import umu.tds.persistencia.IUsuarioDAO;
import umu.tds.persistencia.PublicacionDAO;

public class Controlador {

	private static Controlador controlador;

	// Catalogos del sistema
	private CatalogoUsuarios catalogoUsuarios;
	private CatalogoPublicaciones catalogoPublicaciones;

	// DAO de las clases del sistema
	private IUsuarioDAO usuarioDAO;
	private IPublicacionDAO publicacionDAO;
	private IComentarioDAO comentarioDAO;

	// Usuario logueado
	private Usuario usuario;

	private Controlador() {
		this.catalogoUsuarios = CatalogoUsuarios.getCatalogoUsuarios();
		this.catalogoPublicaciones = CatalogoPublicaciones.getCatalogoPublicaciones();

		FactoriaDAO factoria = FactoriaDAO.getFactoriaDAO(); // Obtenemos la factoria que crea DAO que usan JPA para la
																// persistencia
		this.usuarioDAO = factoria.getUsuarioDAO();
		this.publicacionDAO = factoria.getPublicacionDAO();
		this.comentarioDAO = factoria.getComentarioDAO();

		this.usuario = null;
	}

	public static Controlador getControlador() {
		if (controlador == null) {
			controlador = new Controlador();
		}
		return controlador;
	}

	public boolean registrarUsuario(String nombre, String email, String nombreUsuario, String password,
			LocalDate fechaNacimiento, String presentacion, String foto) {

		if (!catalogoUsuarios
				.existeUsuario((Usuario u) -> u.getEmail().equals(email) || u.getUsuario().equals(nombreUsuario))) {

			PerfilUsuario perfil = new PerfilUsuario(presentacion, foto);
			Usuario usuario = new Usuario(nombre, email, nombreUsuario, password, fechaNacimiento, perfil);

			/*
			 * IMPORTANTE: Guardar primero el usuario en la base de datos. De esta forma, se
			 * genera el id de la entidad automaticamente y podemos guardarlo en el
			 * catalogo.
			 */
			usuarioDAO.save(usuario);
			catalogoUsuarios.add(usuario);
			return true;
		}
		return false;
	}

	public boolean loginUsuario(String nombreUsuario, String password) {
		Predicate<Usuario> predicado = ((
				Usuario u) -> (u.getEmail().equals(nombreUsuario) || u.getUsuario().equals(nombreUsuario))
						&& (u.getPassword().equals(password)));

		Usuario usuarioLogueado = catalogoUsuarios.get(predicado);
		if (usuarioLogueado != null) {
			this.usuario = usuarioLogueado;
			return true;
		}
		return false;
	}

	public void editarPerfil(String foto, String presentacion, String password) {
		PerfilUsuario perfil = usuario.getPerfil();
		perfil.setFoto(foto);

		perfil.setPresentacion(presentacion);

		if (password != null) {
			usuario.setPassword(password);
		}

		usuarioDAO.update(usuario);

	}

	public void seguir(String nombreUsuario) {

		// Obtener el usuario al que se quiere seguir
		Usuario usuarioSeguido = catalogoUsuarios.get(nombreUsuario);

		// Nos añadimos a la lista de seguidores del usuario seguido
		usuarioSeguido.addSeguidor(this.usuario);
	}

	public void dejarSeguir(String nombreUsuario) {

		// Obtener el usuario al que se quiere dejar de seguir
		Usuario usuarioSeguido = catalogoUsuarios.get(nombreUsuario);

		// Nos elmininamos de la lista de seguidores del usuario seguido
		usuarioSeguido.removeSeguidor(this.usuario);
	}

	public void publicarFoto(String ruta, String titulo, String descripcion, List<String> hashtags) {
		Foto foto = new Foto(titulo, descripcion, 0, LocalDateTime.now(), ruta);
		foto.setHashtags(hashtags);

		this.usuario.addPublicacion(foto);
		publicacionDAO.save(foto);
		catalogoPublicaciones.add(foto);
	}

}
