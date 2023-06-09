package umu.tds.controlador;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;

import java.util.EventObject;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;

import umu.tds.componente.CargadorFotos;
import umu.tds.componente.FotosEvent;
import umu.tds.componente.FotosListener;
import umu.tds.fotos.HashTag;
import umu.tds.modelo.catalogos.CatalogoPublicaciones;
import umu.tds.modelo.catalogos.CatalogoUsuarios;
import umu.tds.modelo.pojos.Album;
import umu.tds.modelo.pojos.Comentario;
import umu.tds.modelo.pojos.Descuento;
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

public class Controlador implements FotosListener {

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
	
	// Lista de descuentos
	List<Descuento> descuentos;
	
	// Componente Java Bean para cargar las fotos a partir de un fichero XML
	private CargadorFotos cargador;

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
		this.cargador = new CargadorFotos();
		this.cargador.addFotosListener(this);
		this.descuentos = new LinkedList<Descuento>();
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
	
	public LocalDate getFechaNacimiento() {
		return usuario.getFechaNacimiento();
	}

	public String getUserPresentacion() {
		return usuario.getPerfil().getPresentacion();
	}

	public String getUserPicture() {
		System.out.println(usuario.getPerfil());
		return usuario.getPerfil().getFoto();
	}
	
	/* Devuelve la ruta de la foto de perfil de un usuario */
	public String getUserPicture(String username) {
		Usuario u = catalogoUsuarios.get(username);
		return (u==null) ? null : u.getPerfil().getFoto();
	}
	
	public boolean isPremium() {
		return usuario.getPremium();
	}

	@Override
	public void enteradoCambio(EventObject e) {
		if (e instanceof FotosEvent) {
			FotosEvent evento = (FotosEvent) e;
			for (umu.tds.fotos.Foto foto : evento.getFotosNuevas().getFoto()) {

				// Si la foto no esta guardada en el catalogo
				if (!catalogoPublicaciones.checkPath(foto.getPath())) {
					List<String> hashtags = null;
					String textoComentario = null;
					if (!foto.getHashTags().isEmpty()) {
						hashtags = foto.getHashTags().stream().flatMap(h -> h.getHashTag().stream())
								.collect(Collectors.toList());

						StringBuilder sb = new StringBuilder();
						for (String h : hashtags) {
							sb.append("#" + h);
						}
						sb.append("\n");
						textoComentario = sb.toString();

						System.out.println("HASHTAGS");
						hashtags.stream().forEach(h -> System.out.println(h));

						System.out.println("COMENTARIO");
						System.out.println(textoComentario);
					}

					publicarFoto(foto.getPath(), foto.getTitulo(), foto.getDescripcion(), textoComentario, hashtags);

				}
			}
		}

	}
	
	public void cargarFotos(String ruta) {
		this.cargador.setFicheroFotos(ruta);
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

	public void editarPerfil(String foto, String presentacion) {
		usuario.cambiarPerfil(foto, presentacion);

		usuarioDAO.update(usuario);
	}
	
	public void cambiarPassword(String nuevaPassword) {
		usuario.setPassword(nuevaPassword);
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

	public void publicarAlbum(String titulo, String descripcion, String comentario, List<String> hashtags,
			Set<Foto> fotos) {

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
	
	// Devuelve el número de likes en total de todas las fotos del usuario
	public long numeroLikes() {
		return catalogoPublicaciones.getAll().stream() 
											 .filter(p -> p.getClass()==Foto.class)
											 .filter(p -> p.getUsuario().getId()==usuario.getId())
											 .map(p -> p.getLikes())
											 .count();
	}

	public int numeroUsuariosSeguidos() {
		return usuariosSeguidos.size();
	}

	// Devuelve las últimas 20 publicaciones de usuarios que seguimos, desde la fecha actual
	public List<Publicacion> getUltimasPublicaciones(){
		return catalogoPublicaciones.getAll().stream()
											 .sorted(Comparator.comparing(Publicacion::getFecha))
											 .limit(20)
											 .collect(Collectors.toList());
	}
	
	// Devuelve las últimas 20 fotos de todos los usuarios que seguimos y nosotros mismos
	public List<Publicacion> getUltimasFotos(){
		return catalogoPublicaciones.getAll().stream()
											 .filter(publi -> publi.getClass()==Foto.class)
											 .filter(publi -> usuariosSeguidos.contains(publi.getUsuario()) || publi.getUsuario().getId()==usuario.getId())
											 .sorted(Comparator.comparing(Publicacion::getFecha))
											 .limit(20)
											 .collect(Collectors.toList());
	}
	
	// PREMIUM: el controlador almacena los descuentos que hay hasta ahora implementados
	public void addDescuento(Descuento d) {
		descuentos.add(d);
	}
	
	public List<Descuento> getDescuentos(){
		return descuentos;
	}
	
	// PREMIUM: activa o desactiva el status de premium del usuario
	public void setPremium(boolean value) {
		usuario.setPremium(value);
		usuarioDAO.update(usuario);
	}
	
	// PREMIUM: devuelve las 10 fotos con más me gusta del usuario actual
	public List<Publicacion> getMasMeGusta(){
		return catalogoPublicaciones.getAll().stream()
											 .filter(publi -> publi.getUsuario().getId()==usuario.getId())
											 .filter(publi -> publi.getClass()==Foto.class)
										     .sorted(Comparator.comparing(Publicacion::getLikes))
										     .limit(10)
										     .collect(Collectors.toList());
	}
	
	// PREMIUM: genera un fichero EXCEL con la lista de seguidores (nombre, email, presentacion)
	public void generarExcelSeguidores() {
		
	}
	
	// PREMIM: genera un fichero PDF con la lista de seguidores (nombre, email, presentacion)
	public void generarPdfSeguidores() {
		
	}
	
	// Getters y Setters
	public Usuario getUsuarioLogueado() {
		return this.usuario;
	}

	public void setUsuarioLogueado(Usuario usuario) {
		this.usuario = usuario;
	}

}
