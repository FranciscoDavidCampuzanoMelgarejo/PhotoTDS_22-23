package umu.tds.persistencia;

import java.util.List;

import umu.tds.modelo.pojos.Comentario;

public interface IComentarioDAO {

	// Guardar un comentario en la base de datos
	public void save(Comentario comentario);

	// Recuperar un comentario de la base de datos
	public Comentario findBy(Integer id);

	// Actualizar un comentario de la base de datos
	public void update(Comentario comentario);

	// Eliminar un comentario de la base de datos
	public void delete(Comentario comentario);

	// Recuperar todos los comentarios de la base de datos
	public List<Comentario> findAll();

}
