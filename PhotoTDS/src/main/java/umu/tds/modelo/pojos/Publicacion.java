package umu.tds.modelo.pojos;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
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
	protected Integer id;

	@Column(name = "titulo")
	protected String titulo;

	@Column(name = "descripcion")
	protected String descripcion;

	@Column(name = "likes")
	protected Integer likes;

	@Column(name = "fecha_publicacion", columnDefinition = "TIMESTAMP")
	protected LocalDateTime fecha;

	@ElementCollection(fetch = FetchType.EAGER)
	@CollectionTable(name = "HASHTAGS", joinColumns = @JoinColumn(name = "publicacion_id"))
	@Column(name = "texto")
	protected List<String> hashtags = new ArrayList<String>(4);

	@ManyToOne(fetch = FetchType.EAGER) // Al recuperar una publicacion, tambien recuperamos al usuario
	@JoinColumn(name = "usuario")
	protected Usuario usuario;

	// Al eliminar una publicacion se eliminan los comentarios
	// DEBE DE SER UN SET. SI NO ES UN SET NO FUNCIONA
	@OneToMany(cascade = {CascadeType.REMOVE, CascadeType.PERSIST}, fetch = FetchType.EAGER)
	@JoinColumn(name = "publicacion_id")
	protected Set<Comentario> comentarios = new HashSet<Comentario>();

	protected Publicacion() {
		this.likes = 0;
		this.fecha = LocalDateTime.now();
	}

	// Hacer una publicacion escribiendo hashtags en un comentario
	protected Publicacion(String titulo, String descripcion, Comentario comentario, List<String> hashtags) {
		this();
		this.titulo = titulo;
		this.descripcion = descripcion;

		addComentario(comentario);
		addHashTags(hashtags);
	}
	
	// METODOS ABSTRACTOS
	public abstract void darLike();

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
		addHashTags(hashtags);
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
		addComentarios(comentarios);
	}

	@Override
	public String toString() {
		return "Publicacion [id=" + id + ", titulo=" + titulo + ", descripcion=" + descripcion + ", likes=" + likes
				+ ", fecha=" + fecha.toString() + ", hashtags=" + hashtags + ", usuario=" + usuario.getUsuario() + "comentarios=" + comentarios.size() + "]";
	}

	// METODOS AUXILIARES
	public void addComentario(Comentario comentario) {
		if (comentario != null)
			this.comentarios.add(comentario);
	}

	// METODOS PRIVADOS
	private void addHashTags(List<String> hashtags) {
		if (hashtags != null) {
			hashtags.stream().forEach((String hashtag) -> this.hashtags.add(hashtag));
		}

	}

	private void addComentarios(Set<Comentario> comentarios) {
		if (comentarios != null) {
			comentarios.stream().forEach((Comentario c) -> this.comentarios.add(c));
		}
	}

}
