package umu.tds.modelo.pojos;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
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

	@ElementCollection
	@CollectionTable(name = "HASHTAGS", joinColumns = @JoinColumn(name = "publicacion_id"))
	@Column(name = "texto")
	private List<String> hashtags = new LinkedList<String>();

	@ManyToOne(fetch = FetchType.EAGER) // Al recuperar una publicacion, tambien recuperamos al usuario
	@JoinColumn(name = "usuario")
	private Usuario usuario;

	// Al eliminar una publicacion se eliminan los comentarios
	@OneToMany(cascade = CascadeType.REMOVE, fetch = FetchType.EAGER)
	@JoinColumn(name = "publicacion_id")
	private Set<Comentario> comentarios = new HashSet<Comentario>();

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

	public List<String> getHashtags() {
		return hashtags;
	}

	public void setHashtags(List<String> hashtags) {
		this.hashtags = hashtags;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public Set<Comentario> getComentarios() {
		return comentarios;
	}

	public void setComentarios(Set<Comentario> comentarios) {
		this.comentarios = comentarios;
	}

	@Override
	public String toString() {
		return "Publicacion [id=" + id + ", titulo=" + titulo + ", descripcion=" + descripcion + ", likes=" + likes
				+ ", fecha=" + fecha + ", hashtags=" + hashtags + ", usuario=" + usuario.getUsuario() + ", comentarios="
				+ comentarios + "]";
	}

}
