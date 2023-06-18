package umu.tds.vistas.lists;

import umu.tds.controlador.Controlador;
import umu.tds.modelo.pojos.*;

import java.awt.Component;
import java.awt.Graphics;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;

import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.ListCellRenderer;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import java.awt.BorderLayout;
import javax.swing.SwingConstants;

/**
 * @title Rendering for publication list
 * @author diego
 * */
public class PubliListRenderer implements ListCellRenderer<Publicacion>{	
	@Override
	public Component getListCellRendererComponent(JList<? extends Publicacion> list, Publicacion publi, int index, boolean isSelected, boolean cellHasFocus) {
		PubliListPanel panel = new PubliListPanel(publi);
		return panel;
	}
	
}
