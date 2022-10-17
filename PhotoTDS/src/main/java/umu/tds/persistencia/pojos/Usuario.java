package umu.tds.persistencia.pojos;

import java.io.Serializable;
import java.time.LocalDate;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "USUARIO")
public class Usuario implements Serializable {

	/**
	 * 
	 */
	// Valor por defecto al implementar la interfaz serializable
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;

	@Column(name = "nombre")
	private String nombre;

	@Column(name = "email")
	private String email;

	@Column(name = "nombre_usuario")
	private String usuario;

	@Column(name = "contraseña")
	private String password;

	@Column(name = "fecha_nacimiento", columnDefinition = "DATE")
	private LocalDate fechaNacimiento;

	@Column(name = "premium")
	private Boolean premium;

	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "perfil")
	private PerfilUsuario perfil;
	
	/*
	 * TODO -> Establecer la relacion relfexiva (ManyToMany). Es decir, la lista de seguidores y la lista de personas a las que sigue un usuario
	 * 		   Establcer la relacion OneToMany con publicacion
	 */

	// Constructor por defecto obligatorio para que la clase sea Serializable
	public Usuario() {

	}
	
	public Usuario(String nombre, String email, String usuario, String password, LocalDate fechaNacimiento, PerfilUsuario perfil) {
		this.nombre = nombre;
		this.email = email;
		this.usuario = usuario;
		this.password = password;
		this.fechaNacimiento = fechaNacimiento;
		this.perfil = perfil;
		this.premium = false;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public LocalDate getFechaNacimiento() {
		return fechaNacimiento;
	}

	public void setFechaNacimiento(LocalDate fechaNacimiento) {
		this.fechaNacimiento = fechaNacimiento;
	}

	public Boolean getPremium() {
		return premium;
	}

	public void setPremium(Boolean premium) {
		this.premium = premium;
	}

	public PerfilUsuario getPerfil() {
		return perfil;
	}

	public void setPerfil(PerfilUsuario perfil) {
		this.perfil = perfil;
	}

	@Override
	public String toString() {
		return "Usuario [id=" + id + ", nombre=" + nombre + ", email=" + email + ", usuario=" + usuario + ", password="
				+ password + ", fechaNacimiento=" + fechaNacimiento + ", premium=" + premium + ", perfil=" + perfil
				+ "]";
	}

}
