package umu.tds.persistencia;

import java.util.List;

import umu.tds.modelo.pojos.Usuario;

public interface IUsuarioDAO {

	// Añadir un usuario a la base de datos
	public void save(Usuario usuario);

	// Recuperar un usuario de la base de datos
	public Usuario findBy(Integer id);

	// Actualizar un usuario de la base de datos
	public void update(Usuario usuario);

	// Eliminar un usuario de la base de datos
	public void delete(Usuario usuario);

	// Recuperar todos los usuarios de la base de datos
	public List<Usuario> findAll();
}
