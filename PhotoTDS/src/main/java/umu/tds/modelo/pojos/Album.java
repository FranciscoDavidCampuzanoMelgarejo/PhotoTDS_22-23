package umu.tds.modelo.pojos;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
	private Set<Foto> fotos = new HashSet<Foto>(16);

	public Album() {
		super();
	}

	public Album(String titulo, String descripcion, Comentario comentario, List<String> hashtags, Set<Foto> fotos) {
		super(titulo, descripcion, comentario, hashtags);
		addFotos(fotos);
	}

	public Set<Foto> getFotos() {
		return fotos;
	}

	public void setFotos(Set<Foto> fotos) {
		if (fotos != null) {
			addFotos(fotos);
		}
	}
	
	@Override
	public void darLike() {
		this.likes++;
		fotos.stream()
			.forEach(f -> f.darLike());
		
	}

	// Metodos privados

	private void addFotos(Set<Foto> fotos) {
		fotos.stream().forEach((Foto f) -> this.fotos.add(f));
	}

}
