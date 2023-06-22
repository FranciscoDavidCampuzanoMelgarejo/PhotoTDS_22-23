package umu.tds.vistas.lists;
import umu.tds.modelo.pojos.*;

import java.awt.Color;
import java.awt.Component;
import javax.swing.JList;
import javax.swing.ListCellRenderer;

/**
 * @title Rendering for user list
 * @author diego
 * */
public class UserListRenderer implements ListCellRenderer<Usuario>{	
	@Override
	public Component getListCellRendererComponent(JList<? extends Usuario> list, Usuario usuario, int index, boolean isSelected, boolean cellHasFocus) {
		UserListPanel panel = new UserListPanel(usuario);
		
		if(isSelected) {
			panel.setBackground(new Color(0x3c-10, 0x3f-10, 0x41-10));
		}
		
		return panel;
	}
}
