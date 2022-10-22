package umu.tds.persistencia;

import java.util.List;

import umu.tds.modelo.pojos.Publicacion;

public interface IPublicacionDAO {

	// Guardar una publicacion en la base de datos
	public void save(Publicacion publicacion);

	// Recuperar una publicacion de la base de datos
	public Publicacion findBy(Integer id);

	// Actualizar una publicacion de la base de datos
	public void update(Publicacion publicacion);

	// Eliminar una publicacion de la base de datos
	public void delete(Publicacion publicacion);

	// Recuperar todas las publicaciones de la base de datos
	public List<Publicacion> findAll();

}
