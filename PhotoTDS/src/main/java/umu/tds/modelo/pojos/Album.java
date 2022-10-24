package umu.tds.modelo.pojos;

import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "ALBUM")
public class Album extends Publicacion {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	// Al eliminar un album se eliminan las fotos (hay que ver lo que dice el PDF)
	@OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.REMOVE)
	@JoinColumn(name = "album_id")
	private List<Foto> fotos = new LinkedList<Foto>();

	public Album() {
		super();
	}

	public Album(String titulo, String descripcion, Integer likes, LocalDateTime fecha) {
		super(titulo, descripcion, likes, fecha);
	}

	public List<Foto> getFotos() {
		return fotos;
	}

	public void setFotos(List<Foto> fotos) {
		this.fotos = fotos;
	}

}
