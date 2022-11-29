package umu.tds.controlador;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.function.Predicate;

import javax.persistence.EntityManager;

import umu.tds.modelo.catalogos.CatalogoPublicaciones;
import umu.tds.modelo.catalogos.CatalogoUsuarios;
import umu.tds.modelo.pojos.Album;
import umu.tds.modelo.pojos.Comentario;
import umu.tds.modelo.pojos.Foto;
import umu.tds.modelo.pojos.PerfilUsuario;
import umu.tds.modelo.pojos.Publicacion;
import umu.tds.modelo.pojos.Usuario;
import umu.tds.persistencia.FactoriaDAO;
import umu.tds.persistencia.FactoriaEMF;
import umu.tds.persistencia.IComentarioDAO;
import umu.tds.persistencia.IPublicacionDAO;
import umu.tds.persistencia.IUsuarioDAO;
import umu.tds.persistencia.PublicacionDAO;
import umu.tds.persistencia.UsuarioDAO;

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

	// Lista de usuarios a los que sigo
	List<Usuario> usuariosSeguidos;

	private Controlador() {
		this.catalogoUsuarios = CatalogoUsuarios.getCatalogoUsuarios();
		this.catalogoPublicaciones = CatalogoPublicaciones.getCatalogoPublicaciones();

		FactoriaDAO factoria = FactoriaDAO.getFactoriaDAO(); // Obtenemos la factoria que crea DAO que usan JPA para la
																// persistencia
		this.usuarioDAO = factoria.getUsuarioDAO();
		this.publicacionDAO = factoria.getPublicacionDAO();
		this.comentarioDAO = factoria.getComentarioDAO();

		this.usuario = null;
		this.usuariosSeguidos = new LinkedList<Usuario>();
	}
	
	private void publicar(Publicacion publicacion) {
		publicacion.setUsuario(this.usuario);

		publicacionDAO.save(publicacion);
		catalogoPublicaciones.add(publicacion);
		this.usuario.addPublicacion(publicacion);
	}

	public static Controlador getControlador() {
		if (controlador == null) {
			controlador = new Controlador();
		}
		return controlador;
	}

	public String getUsername() {
		return usuario.getUsuario();
	}

	public String getUserNombre() {
		return usuario.getNombre();
	}

	public String getUserPresentacion() {
		return usuario.getPerfil().getPresentacion();
	}

	public String getUserPicture() {
		return usuario.getPerfil().getFoto();
	}

	public boolean registrarUsuario(String nombre, String email, String nombreUsuario, String password,
			LocalDate fechaNacimiento, String presentacion, String foto) {

		System.out.println(foto);

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
			// Necesito obtener el usuario de la base de datos para que este persistido
			// this.usuario = usuarioDAO.findBy(usuarioLogueado.getId());
			this.usuario = usuarioLogueado;

			// Obtener los usuarios a los que sigue el usuario logueado
			for (Usuario u : catalogoUsuarios.getAll()) {
				if (u.isSeguido(usuarioLogueado)) {
					usuariosSeguidos.add(u);
				}
			}
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
		usuariosSeguidos.add(usuarioSeguido);
		usuarioSeguido.addSeguidor(this.usuario);
		usuarioDAO.update(usuarioSeguido);
	}

	public void dejarSeguir(String nombreUsuario) {

		// Obtener el usuario al que se quiere dejar de seguir
		Usuario usuarioSeguido = catalogoUsuarios.get(nombreUsuario);

		// Nos elmininamos de la lista de seguidores del usuario seguido
		usuariosSeguidos.remove(usuarioSeguido);
		usuarioSeguido.removeSeguidor(this.usuario);
		usuarioDAO.update(usuarioSeguido);
	}

	public void publicarFoto(String ruta, String titulo, String descripcion, String comentario, List<String> hashtags) {

		Foto foto = new Foto(titulo, descripcion, ruta, new Comentario(comentario, usuario), hashtags);
		publicar(foto);
		
	}

	public void publicarAlbum(String titulo, String descripcion, String comentario, List<String> hashtags, Set<Foto> fotos) {
		
		Album album = new Album(titulo, descripcion, new Comentario(comentario, usuario), hashtags, fotos);
		publicar(album);
	}

	public void elminarPublicacion(Integer id) {
		Publicacion publicacion = catalogoPublicaciones.get(id);
		catalogoPublicaciones.remove(publicacion);
		publicacionDAO.delete(publicacion);
	}
	
	public void darLike(Integer id) {
		Publicacion publicacion = catalogoPublicaciones.get(id);
		publicacion.darLike();
		publicacionDAO.update(publicacion);
	}

	public int numeroSeguidores() {
		return usuario.numeroSeguidores();
	}

	public int numeroUsuariosSeguidos() {
		return usuariosSeguidos.size();
	}

	// Getters y Setters
	public Usuario getUsuarioLogueado() {
		return this.usuario;
	}

	public void setUsuarioLogueado(Usuario usuario) {
		this.usuario = usuario;
	}

}
