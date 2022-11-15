package umu.tds.vistas;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.JComponent;
import javax.swing.text.JTextComponent;

public class Utils {

	public Utils() {
		// TODO Auto-generated constructor stub
	}

	public static void campoVacio(String texto) {
		if (texto.isEmpty()) {
			throw new CampoVacioException("Excepcion: Campo Vacio");
		}
	}

	public static void formatoErroneo(String texto, String patron) {
		Pattern p = Pattern.compile(patron);
		Matcher matcher = p.matcher(texto);
		if (!matcher.matches()) {
			throw new FormatoNoAdecuadoException("Excepcion: No cumple el formato");
		}

	}

}
