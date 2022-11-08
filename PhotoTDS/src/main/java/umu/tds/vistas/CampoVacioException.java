package umu.tds.vistas;

public class CampoVacioException extends IllegalArgumentException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public CampoVacioException(String mensaje) {
		super(mensaje);
	}

	// La variable error es la causa de la excepcion
	public CampoVacioException(String mensaje, Throwable error) {
		super(mensaje, error);
	}

}
