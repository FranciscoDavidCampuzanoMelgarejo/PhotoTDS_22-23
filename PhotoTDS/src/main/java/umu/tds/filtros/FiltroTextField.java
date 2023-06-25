package umu.tds.filtros;

import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;

import umu.tds.vistas.FormatoNoAdecuadoException;
import umu.tds.vistas.Utils;

public class FiltroTextField extends DocumentFilter {

	private static final String MENSAJE = " Formato incorrecto ";
	private Boolean estado; // Variable para saber si se debe notificar el cambio o no
	private String patron;
	private DocumentFilterListener filterListener;

	public FiltroTextField(String patron, DocumentFilterListener filterListener) {
		super();
		this.patron = patron;
		this.filterListener = filterListener;
		this.estado = null;
	}

	@Override
	public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs)
			throws BadLocationException {
		if ((text != null) && (!text.equals(""))) {
			String textoCompleto = (fb.getDocument().getText(0, fb.getDocument().getLength() - length) + text);
			try {
				Utils.formatoErroneo(textoCompleto, patron);
				if (estado == null || (!estado)) {
					estado = true;
					filterListener.notificarFormatoCorrecto();
				}
			} catch (FormatoNoAdecuadoException e) {
				if (estado == null || estado) {
					estado = false;
					filterListener.notificarFormatoErroneo(MENSAJE);
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
				if (!estado) {
					estado = true;
					filterListener.notificarFormatoCorrecto();
				}
			} catch (FormatoNoAdecuadoException e) {
				if (estado) {
					estado = false;
					filterListener.notificarFormatoErroneo(MENSAJE);
				}
			}
		} else {
			estado = null;
			filterListener.notificarCampoVacio();
		}
		super.remove(fb, offset, length);
	}

}
