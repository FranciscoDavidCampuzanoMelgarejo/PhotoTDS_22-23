package umu.tds.persistencia.pojos;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "ALBUM")
public class Album extends Publicacion {
	
	

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/*
	 * TODO -> Establecer la relacion OneToMany con la clase Foto
	 */

	public Album() {
		super();
	}
	
	public Album(String titulo, String descripcion, Integer likes, LocalDateTime fecha) {
		super(titulo, descripcion, likes, fecha);
	}

}
