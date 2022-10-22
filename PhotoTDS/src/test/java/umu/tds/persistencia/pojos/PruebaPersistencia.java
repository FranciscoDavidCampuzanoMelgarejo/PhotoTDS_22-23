package umu.tds.persistencia.pojos;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.Arrays;
import java.util.Collections;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import umu.tds.modelo.pojos.Album;
import umu.tds.modelo.pojos.Foto;
import umu.tds.modelo.pojos.PerfilUsuario;
import umu.tds.modelo.pojos.Publicacion;
import umu.tds.modelo.pojos.Usuario;

public class PruebaPersistencia {

	private static EntityManagerFactory emf = Persistence.createEntityManagerFactory("PhotoTDS");

	public static void main(String[] args) {
		EntityManager em = emf.createEntityManager();

		em.getTransaction().begin();
		Usuario u = new Usuario();
		u.setEmail("correo@um.es");
		u.setNombre("Jose Antonio Lopez Alvarez");
		u.setUsuario("user88LOL");
		u.setPassword("1234");
		u.setPremium(false);
		u.setFechaNacimiento(LocalDate.of(1999, Month.MAY, 26));

		PerfilUsuario perfil = new PerfilUsuario();
		perfil.setPresentacion("Hola a todos. Soy nuevo por aqui");
		u.setPerfil(perfil);
		em.persist(u);
		
		System.out.println(u);

		Publicacion p1 = new Foto("De chill", "foto en la playa con los amigos", 0, LocalDateTime.now(),
				"ruta/to/invent");
		p1.setHashtags(Arrays.asList("#chill", "#playa", "#chiringuito"));
		Publicacion p2 = new Foto("Paisaje", "foto del valle de Ricote", 6, LocalDateTime.now(), "ruta/to/invent/2");
		Publicacion p3 = new Album("Album playa", "Album para guardar las fotos de la playa", 0, LocalDateTime.now());
		((Album) p3).getFotos().add((Foto) p1);
		((Album) p3).getFotos().add((Foto) p2);
		p1.setUsuario(u);
		p2.setUsuario(u);
		u.getPublicaciones().add(p1);
		u.getPublicaciones().add(p2);
		u.getPublicaciones().add(p3);
		
		em.persist(p1);
		em.persist(p2);
		em.persist(p3);

		em.getTransaction().commit();
		em.close();
		emf.close();

	}

}
