package umu.tds.vistas.lists;
import umu.tds.modelo.pojos.*;
import umu.tds.vistas.publicaciones.DialogoPublicacion;

import java.awt.Color;
import java.awt.Component;
import javax.swing.JList;
import javax.swing.ListCellRenderer;

/**
 * @title Rendering for publication list
 * @author diego
 * */
public class PubliListRenderer implements ListCellRenderer<Publicacion>{
	private int alto;
	
	public PubliListRenderer(int alto) {
		super();
		this.alto = alto;
	}
	
	@Override
	public Component getListCellRendererComponent(JList<? extends Publicacion> list, Publicacion publi, int index, boolean isSelected, boolean cellHasFocus) {
		PubliListPanel panel = new PubliListPanel(publi, alto);
		
		if(isSelected) {
			panel.setBackground(new Color(0x3c-10, 0x3f-10, 0x41-10));
		}
		
		return panel;
	}
}
