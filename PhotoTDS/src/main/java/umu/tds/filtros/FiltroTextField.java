package umu.tds.filtros;

import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;

import umu.tds.vistas.FormatoNoAdecuadoException;
import umu.tds.vistas.Utils;

public class FiltroTextField extends DocumentFilter {

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
		// System.out.println("REPLACE");
		if ((text != null) && (!text.equals(""))) {
			String textoCompleto = (fb.getDocument().getText(0, fb.getDocument().getLength() - length) + text);
			try {
				Utils.formatoErroneo(textoCompleto, patron);
				if (estado == null || (!estado)) {
					// System.out.println("Formato correcto: cambio de estado");
					estado = true;
					filterListener.notificarCambioFormatoErroneo(EstadosValidacion.VALIDO);
				}
			} catch (FormatoNoAdecuadoException e) {
				if (estado == null || estado) {
					// System.out.println("Formato incorreco: cambio de estado");
					estado = false;
					filterListener.notificarCambioFormatoErroneo(EstadosValidacion.INVALIDO);
				}
			}

		} else
			this.estado = null;

		super.replace(fb, offset, length, text, attrs);
	}

	@Override
	public void remove(FilterBypass fb, int offset, int length) throws BadLocationException {
		// System.out.println("REMOVE");
		if (offset > 0) {
			String textoCompleto = fb.getDocument().getText(0, offset);
			// System.out.println("OFFSET: " + offset);
			// System.out.println(textoCompleto);
			try {
				Utils.formatoErroneo(textoCompleto, patron);
				if (!estado) {
					// System.out.println("Formato correcto: cambio de estado");
					estado = true;
					filterListener.notificarCambioFormatoErroneo(EstadosValidacion.VALIDO);
				}
			} catch (FormatoNoAdecuadoException e) {
				if (estado) {
					// System.out.println("Formato incorrecto: cambio de estado");
					estado = false;
					filterListener.notificarCambioFormatoErroneo(EstadosValidacion.INVALIDO);
				}
			}
		} else {
			// System.out.println("Estado Inicial");
			estado = null;
			filterListener.notificarCambioFormatoErroneo(EstadosValidacion.INICIAL);
		}
		super.remove(fb, offset, length);
	}

}
