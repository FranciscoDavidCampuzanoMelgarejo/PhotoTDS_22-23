package umu.tds.persistencia;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;

import umu.tds.modelo.pojos.Usuario;

public class UsuarioDAO implements IUsuarioDAO {

	private static UsuarioDAO usuarioDAO;
	private EntityManagerFactory emf;

	private UsuarioDAO() {
		this.emf = FactoriaEMF.getEntityManagerFactory();
	}

	public static UsuarioDAO getUsuarioDAO() {
		if (usuarioDAO == null) {
			usuarioDAO = new UsuarioDAO();
		}
		return usuarioDAO;
	}

	@Override
	public void save(Usuario usuario) {
		EntityManager em = emf.createEntityManager();
		try {
			em.getTransaction().begin();
			em.persist(usuario);
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
	public Usuario findBy(Integer id) {
		EntityManager em = emf.createEntityManager();
		Usuario usuario = null;
		try {
			em.getTransaction().begin();
			usuario = em.find(Usuario.class, id);
			em.getTransaction().commit();
			// Si usuario en nulo -> lanzar excepcion EntidadNoEncontrada
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (em.getTransaction().isActive()) {
				em.getTransaction().rollback();
			}
			em.close();
		}
		return usuario;
	}

	@Override
	public void update(Usuario usuario) {
		EntityManager em = emf.createEntityManager();
		try {
			em.getTransaction().begin();
			em.merge(usuario);
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
	public void delete(Usuario usuario) {
		// TODO Auto-generated method stub

	}

	@Override
	public List<Usuario> findAll() {
		EntityManager em = emf.createEntityManager();
		List<Usuario> usuarios = null;
		String query = "Select u from Usuario u";
		try {
			TypedQuery<Usuario> q = em.createQuery(query, Usuario.class);
			usuarios = q.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (em.getTransaction().isActive()) {
				em.getTransaction().rollback();
			}
			em.close();
		}

		return usuarios;
	}

}
