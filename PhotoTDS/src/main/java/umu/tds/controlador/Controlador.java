package umu.tds.controlador;

public class Controlador {

	private static Controlador controlador;

	private Controlador() {

	}

	public static Controlador getControlador() {
		if (controlador == null) {
			controlador = new Controlador();
		}
		return controlador;
	}

}
