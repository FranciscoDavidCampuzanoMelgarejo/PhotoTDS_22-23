package umu.tds.persistencia.pojos;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;

@Entity
@Table(name = "PUBLICACION")
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Publicacion implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	private Integer id;

	@Column(name = "titulo")
	private String titulo;

	@Column(name = "descripcion")
	private String descripcion;

	@Column(name = "likes")
	private Integer likes;

	@Column(name = "fecha_publicacion", columnDefinition = "TIMESTAMP")
	private LocalDateTime fecha;

	/*
	 * @Column(name = "hashtags") private List<String> hashtags;
	 */

	/*
	 * TODO -> Establecer la relacion ManyToOne con el Usuario Establecer la
	 * relacion OneToMany con la clase Comentario
	 */

	public Publicacion() {

	}

	public Publicacion(String titulo, String descripcion, Integer likes, LocalDateTime fecha) {
		this.titulo = titulo;
		this.descripcion = descripcion;
		this.likes = likes;
		this.fecha = fecha;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public Integer getLikes() {
		return likes;
	}

	public void setLikes(Integer likes) {
		this.likes = likes;
	}

	public LocalDateTime getFecha() {
		return fecha;
	}

	public void setFecha(LocalDateTime fecha) {
		this.fecha = fecha;
	}

	@Override
	public String toString() {
		return "Publicacion [id=" + id + ", titulo=" + titulo + ", descripcion=" + descripcion + ", likes=" + likes
				+ ", fecha=" + fecha + "]";
	}

}
