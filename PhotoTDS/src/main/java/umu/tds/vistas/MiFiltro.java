package umu.tds.vistas;

import javax.swing.JLabel;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;
import javax.swing.text.JTextComponent;

public class MiFiltro extends DocumentFilter {

	private JTextComponent componente;
	private String patron;
	private JLabel etiquetaLateral;
	private JLabel etiquetaSuperior;
	private Integer min;
	private Integer max;
	private boolean campoCorrecto;

	public MiFiltro(JTextComponent componente) {
		this.campoCorrecto = true;
		this.componente = componente;
	}

	public MiFiltro(JTextComponent componente, String patron, JLabel etiquetaL, JLabel etiquetaS) {
		this(componente);
		this.patron = patron;
		this.etiquetaLateral = etiquetaL;
		this.etiquetaSuperior = etiquetaS;
	}

	public MiFiltro(JTextComponent componente, String patron, JLabel etiquetaL, JLabel etiquetaS, Integer min,
			Integer max) {
		this(componente, patron, etiquetaL, etiquetaS);
		this.min = min;
		this.max = max;
	}

	@Override
	public void insertString(FilterBypass fb, int offset, String string, AttributeSet attr)
			throws BadLocationException {
		try {

			Utils.formatoErroneo(componente, patron);
			// El formato del campo es correcto
			if (!campoCorrecto || !(etiquetaLateral.isVisible() && etiquetaSuperior.isVisible())) {

				if (min != null && max != null) {
					int longitudTexto = fb.getDocument().getLength() + string.length();
					if (longitudTexto <= min) {
						cambiarEtiquetas("D", " - Contraseña debil", true, true, true);
					} else if (longitudTexto > min && longitudTexto <= max) {
						cambiarEtiquetas("I", " - Contraseña intermedia", true, true, true);
					} else {
						cambiarEtiquetas("F", " - Contraseña fuerte", true, true, true);
					}
				} else {
					cambiarEtiquetas("V", " - Formato correcto", true, true, true);
				}
			}
			super.insertString(fb, offset, string, attr);
		} catch (FormatoNoAdecuadoException e) {
			e.printStackTrace();

			if (campoCorrecto) {
				cambiarEtiquetas("X", " - Formato erroneo", false, true, true);
			}
		}
	}

	@Override
	public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs)
			throws BadLocationException {
		try {

			Utils.formatoErroneo(componente, patron);
			if (!campoCorrecto || !(etiquetaLateral.isVisible() && etiquetaSuperior.isVisible())) {
				if (min != null && max != null) {
					int longitudTexto = (fb.getDocument().getLength() - length) + text.length();
					if (longitudTexto <= min) {
						cambiarEtiquetas("D", " - Contraseña debil", true, true, true);
					} else if (longitudTexto > min && longitudTexto <= max) {
						cambiarEtiquetas("I", " - Contraseña intermedia", true, true, true);
					} else {
						cambiarEtiquetas("F", " - Contraseña fuerte", true, true, true);
					}
				} else {
					cambiarEtiquetas("V", " - Formato correcto", true, true, true);
				}
			}
			super.replace(fb, offset, length, text, attrs);
		} catch (FormatoNoAdecuadoException e) {
			e.printStackTrace();

			if (campoCorrecto) {
				cambiarEtiquetas("X", " - Formato erroneo", false, true, true);
			}
		}
	}

	@Override
	public void remove(FilterBypass fb, int offset, int length) throws BadLocationException {
		if (offset > 0) {
			try {
				Utils.formatoErroneo(componente, patron);
				if (!campoCorrecto || !(etiquetaLateral.isVisible() && etiquetaSuperior.isVisible())) {
					if (min != null && max != null) {
						int longitudTexto = fb.getDocument().getLength() - length;
						if (longitudTexto <= min) {
							cambiarEtiquetas("D", " - Contraseña debil", true, true, true);
						} else if (longitudTexto > min && longitudTexto <= max) {
							cambiarEtiquetas("I", " - Contraseña intermedia", true, true, true);
						} else {
							cambiarEtiquetas("F", " - Contraseña fuerte", true, true, true);
						}
					} else {
						cambiarEtiquetas("V", " - Formato correcto", true, true, true);
					}
				}
				super.remove(fb, offset, length);
			} catch (FormatoNoAdecuadoException e) {
				e.printStackTrace();

				if (campoCorrecto) {
					cambiarEtiquetas("X", " - Formato erroneo", false, true, true);
				}
				super.remove(fb, offset, length);
			}
		} else {
			cambiarEtiquetas("", "", true, false, false);
		}
	}

	private void cambiarEtiquetas(String icono, String mensaje, boolean campoCorrecto, boolean visEtiquetaL,
			boolean visEtiquetaS) {
		etiquetaLateral.setText(icono);
		etiquetaLateral.setVisible(visEtiquetaL);
		etiquetaSuperior.setText(mensaje);
		etiquetaSuperior.setVisible(visEtiquetaS);
		this.campoCorrecto = campoCorrecto;
	}

}
