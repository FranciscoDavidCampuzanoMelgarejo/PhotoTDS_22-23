package umu.tds.persistencia;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;

import umu.tds.modelo.pojos.Comentario;

public class ComentarioDAO implements IComentarioDAO {

	private static ComentarioDAO comentarioDAO;
	private EntityManagerFactory emf;

	private ComentarioDAO() {
		this.emf = FactoriaEMF.getEntityManagerFactory();
	}

	public static ComentarioDAO getComentarioDAO() {
		if (comentarioDAO == null) {
			comentarioDAO = new ComentarioDAO();
		}
		return comentarioDAO;
	}

	@Override
	public void save(Comentario comentario) {
		EntityManager em = emf.createEntityManager();
		try {
			em.getTransaction().begin();
			em.persist(comentario);
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
	public Comentario findBy(Integer id) {
		EntityManager em = emf.createEntityManager();
		Comentario comentario = null;

		try {
			em.getTransaction().begin();
			comentario = em.find(Comentario.class, id);
			em.getTransaction().commit();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (em.getTransaction().isActive()) {
				em.getTransaction().rollback();
			}
			em.close();
		}
		return comentario;
	}

	@Override
	public void update(Comentario comentario) {
		EntityManager em = emf.createEntityManager();
		try {
			em.getTransaction().begin();
			em.merge(comentario);
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
	public void delete(Comentario comentario) {
		EntityManager em = emf.createEntityManager();
		try {
			em.getTransaction().begin();
			em.remove(comentario);
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
	public List<Comentario> findAll() {
		EntityManager em = emf.createEntityManager();
		List<Comentario> comentarios = null;
		String query = "Select c from Comentario c";
		try {
			em.getTransaction().begin();
			TypedQuery<Comentario> q = em.createQuery(query, Comentario.class);
			comentarios = q.getResultList();
			em.getTransaction().commit();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (em.getTransaction().isActive()) {
				em.getTransaction().rollback();
			}
			em.close();
		}

		return comentarios;
	}

}
