package umu.tds.persistencia;

public abstract class FactoriaDAO {

	private static FactoriaDAO factoriaDAO;

	// Esta factoria nos devuelve los DAO que utilizan JPA
	public static final String DAO_JPA = "umu.tds.persistencia.JPAFactoriaDAO";

	// Esta factoria nos devuelve los DAO que utilizan el servicio de persistencia
	// dado por el profesor
	public static final String DAO_TDS = "umu.tds.persistencia.TDSFactoriaDAO";

	protected FactoriaDAO() {

	}

	public static FactoriaDAO getFactoriaDAO(String tipo) {
		if (factoriaDAO == null) {
			try {
				factoriaDAO = (FactoriaDAO) Class.forName(tipo).getDeclaredConstructor().newInstance();

			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return factoriaDAO;
	}

	public static FactoriaDAO getFactoriaDAO() {
		return getFactoriaDAO(DAO_JPA);
	}
	
	public abstract IUsuarioDAO getUsuarioDAO();
	public abstract IPublicacionDAO getPublicacionDAO();
	public abstract IComentarioDAO getComentarioDAO();

}
