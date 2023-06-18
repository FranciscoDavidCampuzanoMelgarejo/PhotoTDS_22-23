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

/** @title JPanel para la lista de publicaciones
 *  @author diego
 * */
public class PubliListPanel extends JPanel{
	private Publicacion publi;
	private BufferedImage img, userpic;
	private JLabel usericon, imgicon, username;
	
	/* Constructor */
	public PubliListPanel(Publicacion publi) {
		super();
		this.publi = publi;
		
		try {
			Foto foto = (Foto)publi;
			img = ImageIO.read(new File(foto.getRuta()));
			String ruta = publi.getUsuario().getPerfil().getFoto();
			//System.err.println("ruta = " + ruta);
			userpic = (ruta==null) ? ImageIO.read(getClass().getResource("/imagenes/noprofilepic.png")) : ImageIO.read(new File(ruta));
		} catch (IOException ioe) { System.err.print("PHOTOTDS !> Error cargando publi '" + publi.toString() + "'\n"); }
		
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{300, 0, 0};
		gridBagLayout.rowHeights = new int[]{0, 0};
		gridBagLayout.columnWeights = new double[]{0.0, 1.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{1.0, Double.MIN_VALUE};
		setLayout(gridBagLayout);
		
		JPanel panelIzq = new JPanel();
		GridBagConstraints gbc_panelIzq = new GridBagConstraints();
		gbc_panelIzq.insets = new Insets(0, 0, 0, 5);
		gbc_panelIzq.fill = GridBagConstraints.BOTH;
		gbc_panelIzq.gridx = 0;
		gbc_panelIzq.gridy = 0;
		add(panelIzq, gbc_panelIzq);
		panelIzq.setLayout(new BorderLayout(0, 0));
		
		imgicon = new JLabel();
			imgicon.setIcon(new ImageIcon(img.getScaledInstance(192, 192, Image.SCALE_SMOOTH)));
		panelIzq.add(imgicon, BorderLayout.CENTER);
		
		JPanel panelDrc = new JPanel();
		GridBagConstraints gbc_panelDrc = new GridBagConstraints();
		gbc_panelDrc.fill = GridBagConstraints.BOTH;
		gbc_panelDrc.gridx = 1;
		gbc_panelDrc.gridy = 0;
		add(panelDrc, gbc_panelDrc);
	}
	
}
