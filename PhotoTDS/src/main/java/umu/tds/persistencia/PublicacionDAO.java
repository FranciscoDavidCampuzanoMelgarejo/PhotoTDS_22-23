package umu.tds.persistencia;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;

import umu.tds.modelo.pojos.Publicacion;

public class PublicacionDAO implements IPublicacionDAO {

	private static PublicacionDAO publicacionDAO;
	private EntityManagerFactory emf;

	private PublicacionDAO() {
		this.emf = FactoriaEMF.getEntityManagerFactory();
	}

	public static PublicacionDAO getPublicacionDAO() {
		if (publicacionDAO == null) {
			publicacionDAO = new PublicacionDAO();
		}
		return publicacionDAO;
	}

	@Override
	public void save(Publicacion publicacion) {
		EntityManager em = emf.createEntityManager();
		try {
			em.getTransaction().begin();
			em.persist(publicacion);
			em.getTransaction().commit();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (em.getTransaction().isActive()) {
				em.getTransaction().rollback();
			}
			em.close();
		}
	}

	@Override
	public Publicacion findBy(Integer id) {
		EntityManager em = emf.createEntityManager();
		Publicacion p = null;
		try {
			em.getTransaction().begin();
			p = em.find(Publicacion.class, id);
			em.getTransaction().commit();

			// Si la publicaicon es nula, entonces lanzar la Excepcion EntidadNoEncontrada
		} catch (Exception e) {
			e.printStackTrace();

		} finally {
			if (em.getTransaction().isActive()) {
				em.getTransaction().rollback();
			}
			em.close();
		}

		return p;
	}

	@Override
	public void update(Publicacion publicacion) {
		EntityManager em = emf.createEntityManager();

		try {
			em.getTransaction().begin();
			em.merge(publicacion);
			em.getTransaction().commit();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (em.getTransaction().isActive()) {
				em.getTransaction().rollback();
			}
			em.close();
		}

	}

	@Override
	public void delete(Publicacion publicacion) {
		EntityManager em = emf.createEntityManager();
		try {
			em.getTransaction().begin();
			em.remove(publicacion);
			em.getTransaction().commit();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (em.getTransaction().isActive()) {
				em.getTransaction().rollback();
			}
			em.close();
		}

	}

	@Override
	public List<Publicacion> findAll() {
		EntityManager em = emf.createEntityManager();
		List<Publicacion> publicaciones = null;
		String query = "SELECT p FROM PUBLICACION p";
		try {
			TypedQuery<Publicacion> q = em.createQuery(query, Publicacion.class);
			publicaciones = q.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (em.getTransaction().isActive()) {
				em.getTransaction().rollback();
			}
			em.close();
		}

		return publicaciones;
	}

}
