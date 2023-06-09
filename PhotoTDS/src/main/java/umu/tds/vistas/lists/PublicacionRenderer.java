package umu.tds.vistas.lists;

import java.awt.Component;
import java.io.Serializable;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.ListCellRenderer;
import umu.tds.modelo.pojos.Publicacion;
import java.awt.Color;
import java.awt.BorderLayout;

public class PublicacionRenderer extends JPanel implements ListCellRenderer<Publicacion>, Serializable{
	public PublicacionRenderer() {
		setBackground(Color.MAGENTA);
		setLayout(new BorderLayout(0, 0));
	}
	private static final long serialVersionUID = 1L;

	@Override
	public Component getListCellRendererComponent(JList<? extends Publicacion> lista, Publicacion objeto, int indice, boolean isSelected, boolean cellHasFocus) {
		Component c;
		if(isSelected) {
			setBackground(Color.cyan);
		} else setBackground(Color.magenta);
		return null;
	}

}
