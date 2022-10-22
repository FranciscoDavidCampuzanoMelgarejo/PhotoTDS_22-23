package umu.tds.modelo.pojos;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "PERFIL_USUARIO")
public class PerfilUsuario implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;

	@Column(name = "presentacion", nullable = true)
	private String presentacion;

	/*
	 * @Column(name = "foto_perfil") private String foto;
	 */

	public PerfilUsuario() {

	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getPresentacion() {
		return presentacion;
	}

	public void setPresentacion(String presentacion) {
		this.presentacion = presentacion;
	}

	@Override
	public String toString() {
		return "PerfilUsuario [id=" + id + ", presentacion=" + presentacion + "]";
	}

}
