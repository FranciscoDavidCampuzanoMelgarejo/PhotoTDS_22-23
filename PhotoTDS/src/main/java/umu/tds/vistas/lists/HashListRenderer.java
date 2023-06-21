package umu.tds.vistas.lists;

import java.awt.Color;
import java.awt.Component;
import javax.swing.JList;
import javax.swing.ListCellRenderer;

/**
 * @title Rendering for hashtag list
 * @author diego
 * */
public class HashListRenderer implements ListCellRenderer<String>{
	@Override
	public Component getListCellRendererComponent(JList<? extends String> list, String hashtag, int index, boolean isSelected, boolean cellHasFocus) {
		HashListPanel panel = new HashListPanel(hashtag);
		
		if(isSelected) {
			panel.setBackground(new Color(0x3c-10, 0x3f-10, 0x41-10));
		}
		
		return panel;
	}
}
