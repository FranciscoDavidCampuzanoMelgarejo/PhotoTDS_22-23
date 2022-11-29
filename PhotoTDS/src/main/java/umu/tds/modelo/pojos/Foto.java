package umu.tds.modelo.pojos;

import java.util.LinkedList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import java.util.regex.*;

@Entity
@Table(name = "FOTO")
public class Foto extends Publicacion {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Column(name = "ruta")
	private String ruta;

	public Foto() {
		super();
	}

	public Foto(String titulo, String descripcion, String ruta, Comentario comentario, List<String> hashtags) {
		super(titulo, descripcion, comentario, hashtags);
		this.ruta = ruta;
	}

	public String getRuta() {
		return ruta;
	}

	public void setRuta(String ruta) {
		this.ruta = ruta;
	}
	
	@Override
	public void darLike() {
		this.likes++;
	}

	public List<String> getEtiquetas() {
		LinkedList<String> l = new LinkedList<String>();
		Pattern patronEtiqueta = Pattern.compile("#[^ ]*");
		Matcher m = patronEtiqueta.matcher(this.getDescripcion());
		while (m.find()) {
			l.add(m.group());
		}
		return l;
	}

}
