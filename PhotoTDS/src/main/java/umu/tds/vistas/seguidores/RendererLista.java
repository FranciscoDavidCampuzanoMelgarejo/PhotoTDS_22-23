package umu.tds.vistas.seguidores;

import java.awt.Component;
import java.io.Serializable;

import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.ListCellRenderer;

import umu.tds.modelo.pojos.Usuario;

public class RendererLista extends JPanel implements ListCellRenderer<Usuario>, Serializable{
	
	private int hoverIndex = -1;

	public RendererLista() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public Component getListCellRendererComponent(JList<? extends Usuario> list, Usuario value, int index,
			boolean isSelected, boolean cellHasFocus) {
		
		PanelListaUsuarios panel = new PanelListaUsuarios(value);
		if(isSelected)
			panel.setColorClickado();
		else if(hoverIndex == index)
			panel.setColorEncima();
		
		return panel;
	}
	
	public int getHoverIndex() {
		return hoverIndex;
	}
	
	public void setHoverIndex(int index) {
		if(hoverIndex != index)
			hoverIndex = index;
	}

	

}
