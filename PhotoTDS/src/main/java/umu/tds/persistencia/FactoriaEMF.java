package umu.tds.persistencia;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class FactoriaEMF {

	private static EntityManagerFactory emf;

	public static EntityManagerFactory getEntityManagerFactory() {
		if (emf == null) {
			emf = Persistence.createEntityManagerFactory("PhotoTDS");
		}
		return emf;
	}

}
