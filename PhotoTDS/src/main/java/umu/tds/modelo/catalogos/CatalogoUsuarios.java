package umu.tds.modelo.catalogos;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import umu.tds.modelo.pojos.Usuario;
import umu.tds.persistencia.FactoriaDAO;
import umu.tds.persistencia.IUsuarioDAO;

public class CatalogoUsuarios {

	private Map<Integer, Usuario> usuarios;
	private static CatalogoUsuarios catalogo;

	private FactoriaDAO factoria;
	private IUsuarioDAO usuarioDAO;

	private CatalogoUsuarios() {
		this.factoria = FactoriaDAO.getFactoriaDAO(); // Obtengo la factoria de JPA
		this.usuarioDAO = factoria.getUsuarioDAO();
		this.usuarios = new HashMap<Integer, Usuario>();
		this.cargarCatalogo();
	}

	private void cargarCatalogo() {
		usuarios = usuarioDAO.findAll().stream().collect(Collectors.toMap(u -> u.getId(), u -> u));
	}

	public static CatalogoUsuarios getCatalogoUsuarios() {
		if (catalogo == null) {
			catalogo = new CatalogoUsuarios();
		}
		return catalogo;
	}

	// Añadir un usuario al catalogo
	public void add(Usuario usuario) {
		usuarios.put(usuario.getId(), usuario);
	}

	// Obtener un usuario del catalogo
	public Usuario get(Integer id) {
		return usuarios.get(id);
	}

	// Eliminar un usuario del catalogo
	public void remove(Usuario usuario) {
		usuarios.remove(usuario.getId());
	}
	
	// Obtener todos los usuarios del catalogo
	public List<Usuario> getAll(){
		return usuarios.values().stream().collect(Collectors.toList());
	}

}
