package umu.tds.persistencia.pojos;

import java.time.LocalDate;
import java.time.Month;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

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
		em.getTransaction().commit();

		System.out.println(u);
		
		Integer id = u.getId();
		Usuario nuevo = em.find(Usuario.class, id);
		System.out.println(nuevo);

	}

}
