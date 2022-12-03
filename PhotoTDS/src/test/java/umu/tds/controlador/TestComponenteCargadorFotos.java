package umu.tds.controlador;

import static org.junit.Assert.*;

import org.junit.BeforeClass;
import org.junit.Test;

import umu.tds.componente.CargadorFotos;

public class TestComponenteCargadorFotos {
	
	private static Controlador controlador;
	
	@BeforeClass
	public static void setUp() {
		controlador = Controlador.getControlador();
		controlador.loginUsuario("jose@um.es", "1234");
	}

	@Test
	public void testComponente() {
		CargadorFotos componente = new CargadorFotos();
		componente.addFotosListener(controlador);
		componente.setFicheroFotos("xml/fotos.xml");
	}

}
