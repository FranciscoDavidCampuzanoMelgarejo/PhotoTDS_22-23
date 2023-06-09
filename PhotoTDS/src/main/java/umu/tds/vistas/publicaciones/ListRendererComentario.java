package umu.tds.vistas.publicaciones;

import java.awt.Component;

import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.ListCellRenderer;

import umu.tds.modelo.pojos.Comentario;
import umu.tds.modelo.pojos.Usuario;

public class ListRendererComentario implements ListCellRenderer<Comentario> {
	
	@Override
	public Component getListCellRendererComponent(JList<? extends Comentario> list, Comentario value, int index,
			boolean isSelected, boolean cellHasFocus) {
		
		Usuario u = value.getUsuario();
		JPanel panelRender = new PanelCeldaComentario(u.getPerfil().getFoto(), u.getUsuario(), value.getTexto());
		return panelRender;
	}

}
