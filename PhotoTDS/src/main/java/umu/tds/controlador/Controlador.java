package umu.tds.controlador;

import java.time.LocalDate;
import java.util.function.Predicate;

import umu.tds.modelo.catalogos.CatalogoPublicaciones;
import umu.tds.modelo.catalogos.CatalogoUsuarios;
import umu.tds.modelo.pojos.PerfilUsuario;
import umu.tds.modelo.pojos.Usuario;
import umu.tds.persistencia.FactoriaDAO;
import umu.tds.persistencia.IComentarioDAO;
import umu.tds.persistencia.IPublicacionDAO;
import umu.tds.persistencia.IUsuarioDAO;

public class Controlador {

	private static Controlador controlador;

	// Catalogos del sistema
	private CatalogoUsuarios catalogoUsuarios;
	private CatalogoPublicaciones catalogoPublicaciones;

	// DAO de las clases del sistema
	private IUsuarioDAO usuarioDAO;
	private IPublicacionDAO publicacionDAO;
	private IComentarioDAO comentarioDAO;

	private Controlador() {
		this.catalogoUsuarios = CatalogoUsuarios.getCatalogoUsuarios();
		this.catalogoPublicaciones = CatalogoPublicaciones.getCatalogoPublicaciones();

		FactoriaDAO factoria = FactoriaDAO.getFactoriaDAO(); // Obtenemos la factoria que crea DAO que usan JPA para la
																// persistencia
		this.usuarioDAO = factoria.getUsuarioDAO();
		this.publicacionDAO = factoria.getPublicacionDAO();
		this.comentarioDAO = factoria.getComentarioDAO();
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
			catalogoUsuarios.add(usuario);
			usuarioDAO.save(usuario);
			return true;
		}
		return false;
	}

	public boolean loginUsuario(String usuario, String password) {
		return catalogoUsuarios
				.existeUsuario((Usuario u) -> (u.getEmail().equals(usuario) || u.getUsuario().equals(usuario))
						&& u.getPassword().equals(password));
	}

}
