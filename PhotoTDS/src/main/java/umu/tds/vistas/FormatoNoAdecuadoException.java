package umu.tds.vistas;

public class FormatoNoAdecuadoException extends IllegalArgumentException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public FormatoNoAdecuadoException(String mensaje) {
		super(mensaje);
	}

	public FormatoNoAdecuadoException(String mensaje, Throwable error) {
		super(mensaje, error);
	}

}
