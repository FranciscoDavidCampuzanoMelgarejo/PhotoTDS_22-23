package umu.tds.modelo.catalogos;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import umu.tds.modelo.pojos.Publicacion;
import umu.tds.persistencia.FactoriaDAO;
import umu.tds.persistencia.IPublicacionDAO;

public class CatalogoPublicaciones {

	private Map<Integer, Publicacion> publicaciones;
	private static CatalogoPublicaciones catalogo;

	private FactoriaDAO factoria;
	private IPublicacionDAO publicacionDAO;

	private CatalogoPublicaciones() {
		this.factoria = FactoriaDAO.getFactoriaDAO();
		this.publicacionDAO = factoria.getPublicacionDAO();
		this.publicaciones = new HashMap<Integer, Publicacion>();
		this.cargarCatalogo();
	}

	private void cargarCatalogo() {
		publicaciones = publicacionDAO.findAll().stream().collect(Collectors.toMap(p -> p.getId(), p -> p));
	}

	public static CatalogoPublicaciones getCatalogoPublicaciones() {
		if (catalogo == null) {
			catalogo = new CatalogoPublicaciones();
		}
		return catalogo;
	}

	// Añadir una publicacion al catalogo
	public void add(Publicacion publicacion) {
		publicaciones.put(publicacion.getId(), publicacion);
	}

	// Recuperar una publicacion del catalogo
	public Publicacion get(Integer id) {
		return publicaciones.get(id);
	}

	// Eliminar una publicacion del catalogo
	public void remove(Publicacion publicacion) {
		publicaciones.remove(publicacion.getId());
	}

	// Recuperar todas las publicaciones del catalogo
	public List<Publicacion> getAll() {
		return publicaciones.values().stream().collect(Collectors.toList());
	}

}
