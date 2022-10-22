package umu.tds.modelo.pojos;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

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

	public Foto(String titulo, String descripcion, Integer likes, LocalDateTime fecha, String ruta) {
		super(titulo, descripcion, likes, fecha);
		this.ruta = ruta;
	}

	public String getRuta() {
		return ruta;
	}

	public void setRuta(String ruta) {
		this.ruta = ruta;
	}

}
