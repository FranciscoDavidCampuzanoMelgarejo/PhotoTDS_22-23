package umu.tds.filtros;

import java.awt.Color;

import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;

import umu.tds.vistas.FormatoNoAdecuadoException;
import umu.tds.vistas.Utils;

public class FiltroPassword extends DocumentFilter {

	private final static int LONGITUD_MINIMA = 4;
	private final static int LONGITUD_MAXIMA = 12;
	private final static String FORMATO_INCORRECTO = " Formato incorrecto ";
	private final static String PASSWORD_DEBIL = " Contraseña débil ";
	private final static String PASSWORD_MEDIA = " Contraseña intermedia ";
	private final static String PASSWORD_FUERTE = " Contraseña fuerte ";

	private Boolean estado; // Variable para saber si se debe notificar el cambio o no
	private boolean isDebil, isMedio, isFuerte;
	private String patron;
	private DocumentFilterListener filterListener;

	public FiltroPassword(String patron, DocumentFilterListener filterListener) {
		super();
		this.patron = patron;
		this.filterListener = filterListener;
		this.estado = null;
		this.isDebil = this.isMedio = this.isFuerte = false;
	}

	@Override
	public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs)
			throws BadLocationException {
		if ((text != null) && (!text.equals(""))) {
			String textoCompleto = (fb.getDocument().getText(0, fb.getDocument().getLength() - length) + text);
			try {
				Utils.formatoErroneo(textoCompleto, patron);
				estado = true;
				if (!isDebil && textoCompleto.length() <= LONGITUD_MINIMA) {
					isDebil = true;
					isMedio = isFuerte = false;
					filterListener.notificarRobustezPassword(PASSWORD_DEBIL, new Color(240, 45, 5));
				} else if (!isMedio
						&& (textoCompleto.length() > LONGITUD_MINIMA && textoCompleto.length() <= LONGITUD_MAXIMA)) {
					isMedio = true;
					isDebil = isFuerte = false;
					filterListener.notificarRobustezPassword(PASSWORD_MEDIA, new Color(245, 210, 5));
				} else if (!isFuerte && textoCompleto.length() > LONGITUD_MAXIMA) {
					isFuerte = true;
					isDebil = isMedio = false;
					filterListener.notificarRobustezPassword(PASSWORD_FUERTE, new Color(60, 240, 13));
				}
			} catch (FormatoNoAdecuadoException e) {
				if (estado == null || estado) {
					estado = false;
					filterListener.notificarFormatoErroneo(FORMATO_INCORRECTO);
				}
			}

		} else
			this.estado = null;

		super.replace(fb, offset, length, text, attrs);
	}

	@Override
	public void remove(FilterBypass fb, int offset, int length) throws BadLocationException {
		if (offset > 0) {
			String textoCompleto = fb.getDocument().getText(0, offset);
			try {
				Utils.formatoErroneo(textoCompleto, patron);
				estado = true;
				if (!isDebil && textoCompleto.length() <= LONGITUD_MINIMA) {
					isDebil = true;
					isMedio = isFuerte = false;
					filterListener.notificarRobustezPassword(PASSWORD_DEBIL, new Color(240, 45, 5));
				} else if (!isMedio
						&& (textoCompleto.length() > LONGITUD_MINIMA && textoCompleto.length() <= LONGITUD_MAXIMA)) {
					isMedio = true;
					isDebil = isFuerte = false;
					filterListener.notificarRobustezPassword(PASSWORD_MEDIA, new Color(245, 210, 5));
				} else if (!isFuerte && textoCompleto.length() > LONGITUD_MAXIMA) {
					isFuerte = true;
					isDebil = isMedio = false;
					filterListener.notificarRobustezPassword(PASSWORD_FUERTE, new Color(60, 240, 13));
				}
			} catch (FormatoNoAdecuadoException e) {
				if (estado) {
					estado = false;
					filterListener.notificarFormatoErroneo(FORMATO_INCORRECTO);
				}
			}
		} else {
			estado = null;
			filterListener.notificarCampoVacio();
		}
		super.remove(fb, offset, length);
	}

}
