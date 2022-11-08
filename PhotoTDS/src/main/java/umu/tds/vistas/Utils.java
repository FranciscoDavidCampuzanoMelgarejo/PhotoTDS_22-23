package umu.tds.vistas;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.JComponent;
import javax.swing.text.JTextComponent;

public class Utils {

	public Utils() {
		// TODO Auto-generated constructor stub
	}

	public static void campoVacio(JTextComponent componente) {
		String campo = componente.getText().trim();
		if (campo.isEmpty()) {
			throw new CampoVacioException("El componente" + componente.getName() + " esta vacio");
		}
	}

	public static void formatoErroneo(JTextComponent componente, String patron) {
		String campo = componente.getText().trim();
		Pattern p = Pattern.compile(patron);
		Matcher matcher = p.matcher(campo);
		if (!matcher.matches()) {
			throw new FormatoNoAdecuadoException(
					"El componente " + componente.getName() + "no tiene el formato adecuado");
		}

	}

}
