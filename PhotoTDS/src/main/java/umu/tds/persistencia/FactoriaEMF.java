package umu.tds.persistencia;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class FactoriaEMF {

	private static FactoriaEMF factoria;
	private EntityManagerFactory emf;
	
	private FactoriaEMF() {
		this.emf = Persistence.createEntityManagerFactory("PhotoTDS");
	}

	public static FactoriaEMF getFactoriaEMF() {
		if (factoria == null) {
			factoria = new FactoriaEMF();
		}
		return factoria;
	}
	
	public EntityManagerFactory getEntityManagerFactory() {
		return emf;
	}
	
	

}
