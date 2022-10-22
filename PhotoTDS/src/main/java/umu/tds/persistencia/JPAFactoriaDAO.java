package umu.tds.persistencia;

public class JPAFactoriaDAO extends FactoriaDAO {

	public JPAFactoriaDAO() {

	}

	@Override
	public IUsuarioDAO getUsuarioDAO() {
		return UsuarioDAO.getUsuarioDAO();
	}

	@Override
	public IPublicacionDAO getPublicacionDAO() {
		return PublicacionDAO.getPublicacionDAO();
	}

	@Override
	public IComentarioDAO getComentarioDAO() {
		return ComentarioDAO.getComentarioDAO();
	}

}
